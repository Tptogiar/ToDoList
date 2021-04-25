package main.model.exception;/**
 * @author Tptogiar
 * @creat 2021-03-28-19:17
 */

/**
 * @author:Tptogiar
 * @Description: items中已经存在标题，描述相同的item
 * @date: 2021/3/28 19:17
 *
 */
public class IsAlreadyExistInItems extends Exception{

    static final long serialVersionUID = -338751993124229948L;
    public IsAlreadyExistInItems() {
    }

    public IsAlreadyExistInItems(String msg){
        super(msg);
    }
}
