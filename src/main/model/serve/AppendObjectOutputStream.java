//package main.model.serve;/**
// * @author Tptogiar
// * @creat 2021-03-28-17:40
// */
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.OutputStream;
//
///**
// * @Author: Tptogiar
// * @Description: 之前是单个item写入的，现在没有用了
// * @Date: 2021/4/24-15:25
// */
//
//
//
//
///**
// * @author:Tptogiar
// * @Description: 重写ObjectOutputStream，实现对象追加写入
// * @date: 2021/3/28 17:40
// *
// */
//public class AppendObjectOutputStream extends ObjectOutputStream {
//    public AppendObjectOutputStream(OutputStream out) throws IOException {
//        super(out);
//    }
//
//    protected AppendObjectOutputStream() throws IOException, SecurityException {
//        super();
//    }
//
//    @Override
//    protected void writeStreamHeader() throws IOException {
//        return;
//    }
//}
