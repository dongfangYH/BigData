package log;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author yuanhang.liu@tcl.com
 * @Date 2019-10-19
 **/
@Data
public class LogEvent implements Serializable {

    private String application;
    private String profile;
    private String ip;
    private String level;
    private String loggerName;
    private String date;
    private String timezone;
    private String threadName;
    private String message;
    private String stackTrace;
}
