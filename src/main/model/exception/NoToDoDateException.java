package main.model.exception;/**
 * @author Tptogiar
 * @creat 2021-03-28-16:52
 */

/**
 * @author:Tptogiar
 * @Description: 找不到储存Item数据的文件
 * @date: 2021/3/28 16:52
 *
 */
public class NoToDoDateException extends Exception{

    static final long serialVersionUID = -3387516993124948L;
    public NoToDoDateException() {
    }

    public NoToDoDateException(String msg){

        super(msg);
    }
}
