package utils;

/**
 * Created by crised on 7/9/14.
 */
public class AppException extends  Exception{

    public AppException() { super(); }
    public AppException(String message) { super(message); }
    public AppException(String message, Throwable cause) { super(message, cause); }
    public AppException(Throwable cause) { super(cause); }
}
