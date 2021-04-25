package main.model.exception;

/**
 * @author Tptogiar
 * @Descripiton: 找不到保存在本地的图片
 * @creat 2021/04/12-20:45
 */


public class NoSuchImageInData extends Exception {
    static final long serialVersionUID = -3387519924229948L;
    public NoSuchImageInData() {
    }

    public NoSuchImageInData(String msg){
        super(msg);
    }


}
