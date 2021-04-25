package main.model.exception;/**
 * @author Tptogiar
 * @creat 2021-03-24-14:42
 */

/**
 * @author:Tptogiar
 * @Description: 没有找到给item
 * @date: 2021/3/24 14:42
 *
 */
public class NoSuchItemException extends Exception{
    static final long serialVersionUID = -3387516993124229948L;
    public NoSuchItemException() {
    }

    public NoSuchItemException(String msg){
        super(msg);
    }
}
