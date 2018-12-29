package com.tts.starsky.apperceive.exception;

/**
 * 数据库异常
 */
public class DBException extends Exception {

    public final static String TAG="数据库";

    public final static String UNINITEXCEPTION="数据库未初始化异常";
    public final static String UNINITDAOSESSIONEXCEPTION="数据库Session空指针异常";
    public final static String INSERTEXCEPTION="数据库插入异常";

    public DBException(String message) {
        super(message);
    }
}
