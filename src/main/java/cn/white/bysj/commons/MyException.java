package cn.white.bysj.commons;

/**
 * Create by @author white
 *
 * @date 2017-12-28 20:41
 */
public class MyException extends Exception {

    private int status;

    public MyException(int status, String message){
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
