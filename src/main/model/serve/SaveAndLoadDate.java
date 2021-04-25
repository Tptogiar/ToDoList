package main.model.serve;/**
 * @author Tptogiar
 * @creat 2021-03-28-15:16
 */

import main.model.Item;
import main.model.exception.NoToDoDateException;
import main.model.manager.ItemsMgr;


import java.io.*;
import java.util.ArrayList;


/**
 * @author:Tptogiar
 * @Description: 保存数据到本地
 * @date: 2021/3/28 15:16
 *
 */
public class SaveAndLoadDate<E> {

    /**
     * @Author: Tptogiar
     * @Description:整个集合保存
     * @Date: 2021/3/30-13:29
     */
    public static void saveObject(Object saveObject, File saveFile){


        File file =null;
        FileOutputStream fileOutputStream=null;
        ObjectOutputStream objectOutputStream=null;

        try {


            file=saveFile;

            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }



            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(saveObject);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {


            if (fileOutputStream!= null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (objectOutputStream!=null){

                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    /**
     * @Author: Tptogiar
     * @Description: 加载整个集合
     * @Date: 2021/3/30-13:30
     */
    public static Object loadObject(File loadFile) throws NoToDoDateException {

        Object readObject=null;
        if (!ItemsMgr.fileDir.exists()){
            throw new NoToDoDateException("数据丢失：找不到"+loadFile);
        }

        FileInputStream fileInputStream=null;
        ObjectInputStream objectInputStream=null;


        try {

            fileInputStream = new FileInputStream(loadFile);

            objectInputStream = new ObjectInputStream(fileInputStream);

//            ArrayList<Item>items= (ArrayList<Item>)objectInputStream.readObject();
//            ItemsMgr.setItems(items);
            readObject = objectInputStream.readObject();

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            if (fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (objectInputStream!=null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return readObject;
    }




//    /**
//     * @Author: Tptogiar
//     * @Description: 将存储ToDo项的ArrayList列表存储到本地硬盘
//     * @Date: 2021/3/28-16:23
//     */
//
//    /**
//     * @Author: Tptogiar
//     * @Description: 下面这个方法没有用到了，刚开始是调用这个方法一个一个存的，
//     * 但是发现要修改里面某一条数据的时候很比较麻烦（
//     * 要实现修改某一条Item的时候，整体读入，修改后再整体写出这样肯定示比较好操作的，但是数据量大的时候效率是很低的，
//     * 但是要精确的修改某一条数据需要把操作文件的指针定位到某一条Item上，可能得用RandomAccessFile来实现，但是现在没时间了）
//     * 所以就先换成下面saveTiems，整体读入写出吧，后面有时间在来改，所以这个函数就先保留着吧
//     * @Date: 2021/3/30-13:35
//     */
//    @Deprecated
//    public static void saveItem(Item item,boolean isAppend) {
//
//        File file =null;
//        FileOutputStream fileOutputStream=null;
//        ObjectOutputStream objectOutputStream=null;
//
//        try {
//
//            file = ItemsMgr.fileDir;
//
//
//
//
//
//            if(!file.exists()){
//                file.createNewFile();
//            }
//
//            fileOutputStream = new FileOutputStream(file,isAppend);
//
//
//            //判读是否是第一次写入，如果不是第一次写入，则不需要加入文件头，用重写的AppendObjectOutputStream来写入，
//            // 避免重复写入文件头导致读取失败
//            if (file.length()<1){
//                objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            }else{
//                objectOutputStream=new AppendObjectOutputStream(fileOutputStream);
//            }
//
//
//            objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(item);
//            objectOutputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//
//
//            if (fileOutputStream!= null) {
//                try {
//                    fileOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (objectOutputStream!=null){
//
//                try {
//                    objectOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//



//    /**
//     * @Author: Tptogiar
//     * @Description: 将保存在ToDo.data中的数据读取到内存中的
//     * @Date: 2021/3/28-16:35
//     */
//
//    /**
//     * @Author: Tptogiar
//     * @Description: 同SaveDate中的saveItem一样，这个方法已经不使用了
//     * @Date: 2021/3/30-13:44
//     */
//    @Deprecated
//    public static void loadItem() throws  ClassNotFoundException, NoToDoDateException {
//
//
//        if (!ItemsMgr.fileDir.exists()){
//            throw new NoToDoDateException("数据丢失：找不到储存ToDo数据的文件!");
//        }
//
//
//        FileInputStream fileInputStream=null;
//        ObjectInputStream objectInputStream=null;
//
//
//        try {
//
//            fileInputStream = new FileInputStream(ItemsMgr.fileDir);
//            objectInputStream = new ObjectInputStream(fileInputStream);
//
//
//            while (true){
//
//                try {
//
//                    Item item= (Item)objectInputStream.readObject();
//                    ItemsMgr.addItem(item);
//
//                } catch (IOException e) {
//
//                    //判读是否到达文件结尾
//                    e.printStackTrace();
//                    break;
//                }
//            }
//
//
//
//        }catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//
//            if (fileInputStream!=null){
//                try {
//                    fileInputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//            if (objectInputStream!=null){
//                try {
//                    objectInputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//        }
//
//
//    }

}
