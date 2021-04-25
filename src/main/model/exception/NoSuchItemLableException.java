package main.model.exception;/**
 * @author Tptogiar
 * @creat 2021-03-24-21:39
 */

/**
 * @author:Tptogiar
 * @Description: 没有找到该标签
 * @date: 2021/3/24 21:39
 *
 */
public class NoSuchItemLableException extends Exception{
    static final long serialVersionUID = -33875169931249948L;
    public NoSuchItemLableException() {
    }

    public NoSuchItemLableException(String msg){
        super(msg);
    }
}
