//package main.model.otherthread;/**
// * @author Tptogiar
// * @creat 2021-04-07-23:39
// */
//
//import main.controller.EventList;
//import main.model.Item;
//import main.model.manager.ItemsMgr;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//
///**
// * @Author: Tptogiar
// * @Description: 本来想把排序，搜索等操作分配到这个线程的，但是想想数据量挺小的，不是很必要
// * 后面需要再弄吧，此线程先暂时保留
// * @Date: 2021/4/9-20:44
// */
///**
// * @author:Tptogiar
// * @Description: ServeThread的Runnalbe
// * @date: 2021/4/7 23:39
// *
// */
//public class ServeThread extends Thread {
//
//
//
//    public static final String SEARCH_ACTION="search_action";
//
//    private String actionType=null;
//
//    private String searchkey;
//
//    private boolean isStop=false;
//
//    private static ServeThread serveThread=null;
//    private ServeThread(){
//    }
//
//    public static ServeThread getServeThread(){
//        if (serveThread==null){
//            serveThread=new ServeThread();
//        }
//        return serveThread;
//    }
//
//
//    public void setActionType(String actionType,String searchkey) {
//        this.actionType = actionType;
//        this.searchkey=searchkey;
//    }
//
//
//    public void stopThread(boolean isStop) {
//        this.isStop = isStop;
//    }
//
//    @Override
//    public void run() {
//        while (!isStop){
//            if(SEARCH_ACTION.equals(actionType)){
//                ArrayList<Item> searchResult = ItemsMgr.searchByStr(searchkey);
//                try {
//                    EventList.controlOfMainWindow.upContent(searchResult);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//
//
//
//
//
//    }
//}
