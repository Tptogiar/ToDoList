package main.model.serve;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDateTime;
import javafx.scene.image.Image;
import main.model.exception.NoSuchImageInData;

/**
 * @author Tptogiar
 * @Descripiton: 复制插入的图片到软件resource目录下并简单加密一下图片
 * @creat 2021/04/11-23:18
 */

public class CopyAndLoadImage {

    public static File saveImage(File outerImageFile){
        File imageSaveDir=new File("src\\UserData\\images\\");
        File imageFile = new File("src\\UserData\\images\\"+ FormatServe.formatLoaclDateTimeForFileName(LocalDateTime.now()));
        FileInputStream fileInputStream=null;
        FileOutputStream fileOutputStream=null;

        try {

            if(!imageSaveDir.exists()){
                imageSaveDir.mkdirs();
            }

            if(!imageFile.exists()){
                imageFile.createNewFile();
            }


            fileInputStream = new FileInputStream(outerImageFile);
            fileOutputStream=new FileOutputStream(imageFile);

            byte[] data=new byte[1024];
            int len;
            while((len=fileInputStream.read(data))!=-1){
                fileOutputStream.write(data,0,len);
                fileOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imageFile;
    }

    public static Image loadImage(File imageFile) throws MalformedURLException {


        FileInputStream fileInputStream=null;


        try {

            fileInputStream = new FileInputStream(imageFile);
            if (!imageFile.exists()){
                throw new NoSuchImageInData("找不到保存在本地的图片");
            }

            byte[] data=new byte[1024];
            int len;
            while((len=fileInputStream.read(data))!=-1){
            }
        } catch (NoSuchImageInData | IOException e) {
            e.printStackTrace();
        }finally {
            if (fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Image(imageFile.toURI().toURL().toString());

    }




}
