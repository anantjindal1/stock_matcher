package com.stockmarket.logger;

public enum LogLevel {
    DEBUG,INFO,ERROR,NONE;

    /**
     * 1 - debug, 2 - info, 3 - error, 4 - none
     * @param num
     * @return
     */
    public static LogLevel fromInteger(int num){
        LogLevel level;
        switch (num){
            case 1:
                level = DEBUG;
                break;
            case 2:
                level = INFO;
                break;
            case 3:
                level = ERROR;
                break;
            default:
                level = NONE;
                break;
        }
        return level;
    }
}
