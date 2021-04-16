package log.sink.logback.appender;

import log.LogEvent;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.google.gson.Gson;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

/**
 * @Description LogstashAppender
 * @Author yuanhang.liu@tcl.com
 * @Date 2019-09-06
 **/
public class LogstashAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    @Setter
    protected String application = "";

    @Setter
    protected String profile = "";

    @Setter
    protected Integer port = 8001;

    @Setter
    protected String host = "";

    @Setter
    protected String endpoint = "";

    @Setter
    protected Integer connectTimeout = 10000;

    @Setter
    protected Integer socketTimeout = 10000;

    @Setter
    protected Integer connectRequestTimeout = 10000;

    @Setter
    protected Integer responseTimeout = 10000;

    @Setter
    protected Integer retryNum = 1;

    @Setter
    protected Integer interval = 100;

    @Setter
    protected Integer connMaxTotal = 1000;

    @Setter
    protected Integer connMaxPerRoute = 1000;

    private static final String SUPPRESSED_CAPTION = "Suppressed: ";
    private static final String CAUSE_CAPTION = "Caused by: ";
    private static final String NEXT_LINE = "\r\n";

    private List<String> requestQueue;

    private String ip;

    private LogEventSender logEventSender;

    private Gson gson;

    private CloseableHttpAsyncClient asyncClient;

    private String url;

    private SimpleDateFormat sdf;

    @Override
    public void start() {
        super.start();
        gson = new Gson();
        requestQueue = new LinkedList<>();
        ip = getIpAddress();
        url = host+ ":" + port + "/" + endpoint ;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectRequestTimeout)
                .build();

        //配置io线程
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .setSoKeepAlive(true)
                .build();

        //设置连接池大小
        ConnectingIOReactor ioReactor=null;
        try {
            ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor);
        connManager.setMaxTotal(connMaxTotal);
        connManager.setDefaultMaxPerRoute(connMaxPerRoute);

        asyncClient = HttpAsyncClients.custom().
                setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        asyncClient.start();

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
            asyncClient.close();
        } catch (Exception e) {
            e.printStackTrace();
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
        String jsonStr = gson.toJson(logEvent);
        try {
            sendLogEvent(jsonStr);
        } catch (Exception e) {
           e.printStackTrace();
        }

    }

    private void sendLogEvent(String record) {
        synchronized (requestQueue){
            requestQueue.add(record);
        }
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
        private CallBack callBack = new CallBack();

        @Override
        public void run() {

            List<String> requestEntryList = new ArrayList<>();

            for (;;){

                synchronized (requestQueue){
                    requestEntryList.addAll(requestQueue);
                    requestQueue.clear();
                }

                if (requestEntryList.size() > 0){
                    try {
                        sendLogEventToLogstash(requestEntryList, retryNum);
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


        private void sendLogEventToLogstash(List<String> recordList, Integer retry){

            final List<String> failRequest = new ArrayList<>();
            final List<Future<HttpResponse>> futureList = new LinkedList<>();
            for (String record : recordList){
                HttpPost post = new HttpPost(url);
                HttpEntity httpEntity = new StringEntity(record, APPLICATION_JSON);
                post.setEntity(httpEntity);
                futureList.add(asyncClient.execute(post, callBack));
            }

            for (int idx = 0; idx < futureList.size(); idx++){
                try {
                    HttpResponse response = futureList.get(idx).get(responseTimeout, TimeUnit.MILLISECONDS);
                    if (200 != response.getStatusLine().getStatusCode()){
                        failRequest.add(recordList.get(idx));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    failRequest.add(recordList.get(idx));
                }

            }

            if (failRequest.size() > 0){
                sendLogEventToLogstash(failRequest, --retry);
            }

        }

    }

    private final class CallBack implements FutureCallback<HttpResponse>{

        @Override
        public void completed(HttpResponse httpResponse) {

        }

        @Override
        public void failed(Exception e) {

        }

        @Override
        public void cancelled() {

        }
    }

}
