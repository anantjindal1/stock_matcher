package com.stockmarket.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    String loggerName;
    SimpleDateFormat formatter;
    public static boolean isDebug, isInfo, isError;

    private Logger(String name) {
        loggerName = name;
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    /**
     * 1 - debug, 2 - info, 3 - error, 4 - none
     *
     * @param level
     */
    public static void setLogLevel(LogLevel level) {
        isDebug = false;
        isInfo = false;
        isError = false;
        switch (level) {
            case DEBUG:
                isDebug = true;
                break;
            case INFO:
                isInfo = true;
                break;
            case ERROR:
                isError = true;
                break;
            case NONE:
            default:
                break;
        }

    }

    public static Logger getLogger(String name) {
        return new Logger(name);
    }

    public void error(String message) {
        print(isError, "ERROR", message);
    }

    public void info(String message) {
        print(isInfo || isError, "INFO", message);
    }

    public void debug(String message) {
        print(isDebug || isError || isInfo, "DEBUG", message);
    }

    private void print(boolean isOn, String level, String message) {
        if (isOn) {
            System.out.println(formatter.format(new Date(System.currentTimeMillis())) +
                    " : " + level.toUpperCase() + " : " + loggerName + " : " + message);
        }
    }
}
