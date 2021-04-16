package log.sink.logback.appender;

import log.LogEvent;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesisAsyncClient;
import com.amazonaws.services.kinesis.AmazonKinesisAsyncClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import com.amazonaws.services.kinesis.model.PutRecordsResultEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Description KinesisAppender
 * @Author yuanhang.liu@tcl.com
 * @Date 2019-09-06
 **/
public class KinesisAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    public static final String KINESIS_ENDPOINT_PREFIX = "https://kinesis.";
    public static final String KINESIS_ENDPOINT_SUFFIX = ".amazonaws.com";

    @Setter
    protected String application = "";

    @Setter
    protected String profile = "";

    @Setter
    protected String region = "";

    @Setter
    protected String streamName = "";

    @Setter
    protected String partitionKey = null;

    @Setter
    protected Integer retryNum = 1;

    @Setter
    protected Integer interval = 100;

    private static final String SUPPRESSED_CAPTION = "Suppressed: ";
    private static final String CAUSE_CAPTION = "Caused by: ";
    private static final String NEXT_LINE = "\r\n";

    private Regions regions;

    private AmazonKinesisAsyncClient kinesisAsyncClient;

    private List<PutRecordsRequestEntry> requestQueue;

    private String ip;

    private ObjectMapper objectMapper;

    private LogEventSender logEventSender;

    private SimpleDateFormat sdf;


    private String getKinesisEndpoint(Regions regions){
        return KINESIS_ENDPOINT_PREFIX + regions.getName() + KINESIS_ENDPOINT_SUFFIX;
    }



    @Override
    public void start() {
        super.start();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        regions = Regions.fromName(region);
        objectMapper = new ObjectMapper();
        AWSCredentialsProvider credentialsProvider = DefaultAWSCredentialsProviderChain.getInstance();
        AmazonKinesisAsyncClientBuilder clientBuilder = AmazonKinesisAsyncClientBuilder.standard();
        clientBuilder.withCredentials(credentialsProvider);
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(getKinesisEndpoint(regions),regions.getName());
        clientBuilder.withEndpointConfiguration(endpointConfiguration);
        kinesisAsyncClient = (AmazonKinesisAsyncClient) clientBuilder.build();
        requestQueue = new LinkedList<>();
        ip = getIpAddress();
        logEventSender = new LogEventSender();
        logEventSender.setName("T-logEventSender");
        logEventSender.start();

    }

    @Override
    public void stop() {
        super.stop();
        logEventSender.shutdown();
        try {
            logEventSender.join(2 * interval);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != kinesisAsyncClient){
            kinesisAsyncClient.shutdown();
        }
    }

    @Override
    protected void append(ILoggingEvent event) {
        ThrowableProxy throwableProxy = (ThrowableProxy) event.getThrowableProxy();
        Throwable throwable = null;

        if (null != throwableProxy){
           throwable = throwableProxy.getThrowable();
        }

        String loggerName = event.getLoggerName();
        String level = event.getLevel().levelStr;
        String message = event.getMessage();
        Long timestamp = event.getTimeStamp();
        String threadName = event.getThreadName();
        String stackTrace = getStackTrace(throwable);

        LogEvent logEvent = new LogEvent();
        logEvent.setApplication(application);
        logEvent.setLevel(level);
        logEvent.setLoggerName(loggerName);
        logEvent.setDate(sdf.format(new Date(timestamp)));
        logEvent.setProfile(profile);
        logEvent.setThreadName(threadName);
        logEvent.setStackTrace(stackTrace);
        logEvent.setMessage(message);
        logEvent.setIp(ip);
        logEvent.setTimezone(TimeZone.getDefault().getID());

        try {
            PutRecordsRequestEntry putRecordsRequestEntry = new PutRecordsRequestEntry();
            putRecordsRequestEntry.setData(ByteBuffer.wrap(objectMapper.writeValueAsString(logEvent).getBytes(Charset.forName("UTF-8"))));
            putRecordsRequestEntry.setPartitionKey(getPartitonKey());
            sendLogEvent(putRecordsRequestEntry);
        } catch (Exception e) {
           e.printStackTrace();
        }

    }

    private void sendLogEvent(PutRecordsRequestEntry record) {
        synchronized (requestQueue){
            requestQueue.add(record);
        }
    }


    /**
     * 获取分区键
     * @return
     */
    private String getPartitonKey() {
        if (null == partitionKey){
            return UUID.randomUUID().toString();
        }
        return partitionKey;

    }

    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("fail to obtain ip address." + e.toString());
        }
        return "";
    }

    private String getStackTrace(Throwable throwable) {

        if (null == throwable){
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(throwable).append(NEXT_LINE);
        Set<Throwable> dejaVu = Collections.newSetFromMap(new IdentityHashMap<>());
        dejaVu.add(throwable);
        StackTraceElement[] trace = throwable.getStackTrace();
        for (StackTraceElement traceElement : trace){
            builder.append("\tat " + traceElement).append(NEXT_LINE);
        }

        // Print suppressed exceptions, if any
        for (Throwable se : throwable.getSuppressed()){
            getEnclosedStackTrace(se, builder, trace, SUPPRESSED_CAPTION, "\t", dejaVu);
        }

        // Print cause, if any
        Throwable ourCause = throwable.getCause();
        if (ourCause != null){
            getEnclosedStackTrace(ourCause, builder, trace, CAUSE_CAPTION, "", dejaVu);
        }

        return builder.toString();
    }


    private void getEnclosedStackTrace(Throwable throwable, StringBuilder builder,
                                         StackTraceElement[] enclosingTrace,
                                         String caption,
                                         String prefix,
                                         Set<Throwable> dejaVu) {
        if (dejaVu.contains(throwable)) {
            builder.append("\t[CIRCULAR REFERENCE:" + this + "]").append(NEXT_LINE);;
        } else {
            dejaVu.add(throwable);
            // Compute number of frames in common between this and enclosing trace
            StackTraceElement[] trace = throwable.getStackTrace();
            int m = trace.length - 1;
            int n = enclosingTrace.length - 1;
            while (m >= 0 && n >=0 && trace[m].equals(enclosingTrace[n])) {
                m--; n--;
            }
            int framesInCommon = trace.length - 1 - m;

            // Print our stack trace
            builder.append(prefix + caption + throwable);
            for (int i = 0; i <= m; i++){
                builder.append(prefix + "\tat " + trace[i]).append(NEXT_LINE);;
            }
            if (framesInCommon != 0){
                builder.append(prefix + "\t... " + framesInCommon + " more").append(NEXT_LINE);;
            }

            // Print suppressed exceptions, if any
            for (Throwable se : throwable.getSuppressed()){
                getEnclosedStackTrace(se, builder, trace, SUPPRESSED_CAPTION,
                        prefix +"\t", dejaVu);
            }

            // Print cause, if any
            Throwable ourCause = throwable.getCause();
            if (ourCause != null){
                getEnclosedStackTrace(ourCause, builder, trace, CAUSE_CAPTION, prefix, dejaVu);
            }
        }
    }

    private final class LogEventSender extends Thread{

        private volatile boolean shutdownFlag = false;

        @Override
        public void run() {

            List<PutRecordsRequestEntry> requestEntryList = new ArrayList<>();

            for (;;){

                synchronized (requestQueue){
                    requestEntryList.addAll(requestQueue);
                    requestQueue.clear();
                }

                if (requestEntryList.size() > 0){
                    try {
                        sendLogEventToKinesis(requestEntryList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                requestEntryList.clear();

                if (shutdownFlag){
                    break;
                }

                try {
                    if (Thread.interrupted()) {
                        break;
                    }
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void shutdown(){
            shutdownFlag = true;
        }


        private void sendLogEventToKinesis(List<PutRecordsRequestEntry> requestEntryList) throws ExecutionException, InterruptedException, TimeoutException {

            PutRecordsRequest recordsRequest = new PutRecordsRequest();
            recordsRequest.setStreamName(streamName);
            recordsRequest.setRecords(requestEntryList);
            final List<PutRecordsRequestEntry> failRequest = new LinkedList<>();
            Future<PutRecordsResult> resultFuture = kinesisAsyncClient.putRecordsAsync(recordsRequest,
                    new AsyncHandler<PutRecordsRequest, PutRecordsResult>(){
                        @Override
                        public void onError(Exception exception) {
                            exception.printStackTrace();
                        }
                        @Override
                        public void onSuccess(PutRecordsRequest request, PutRecordsResult putRecordsResult) {
                            Integer failCount = putRecordsResult.getFailedRecordCount();
                            if (failCount > 0 ){
                                for (int i = 0; i < putRecordsResult.getRecords().size(); i++){
                                    PutRecordsResultEntry resultEntry = putRecordsResult.getRecords().get(i);
                                    if (resultEntry.getErrorCode() != null){
                                        PutRecordsRequestEntry requestEntry = request.getRecords().get(i);
                                        failRequest.add(requestEntry);
                                    }
                                }
                            }
                        }
                    });

            resultFuture.get(10, TimeUnit.SECONDS);
            if (failRequest.size() > 0){
                retryPolicy(failRequest, retryNum);
            }

        }

        private Integer retryPolicy(List<PutRecordsRequestEntry> failRequest, Integer num) {

            // end condition
            if (num-- <= 0){
                return failRequest.size();
            }

            PutRecordsRequest recordsRequest = new PutRecordsRequest();
            recordsRequest.setStreamName(streamName);
            recordsRequest.setRecords(failRequest);
            PutRecordsResult recordsResult = kinesisAsyncClient.putRecords(recordsRequest);

            Integer failCount = recordsResult.getFailedRecordCount();
            if (failCount > 0){
                List<PutRecordsRequestEntry> requestEntryList = new LinkedList<>();
                for (int i = 0; i < failRequest.size(); i++){
                    PutRecordsResultEntry resultEntry = recordsResult.getRecords().get(i);
                    if (resultEntry.getErrorCode() != null){
                        requestEntryList.add(failRequest.get(i));
                    }
                }

                return retryPolicy(requestEntryList, num);
            }

            return failCount;

        }

    }

}
