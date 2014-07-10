package utils;

/**
 * Created by crised on 7/9/14.
 */
public class ModException extends  Exception{

    public ModException() { super(); }
    public ModException(String message) { super(message); }
    public ModException(String message, Throwable cause) { super(message, cause); }
    public ModException(Throwable cause) { super(cause); }
}
