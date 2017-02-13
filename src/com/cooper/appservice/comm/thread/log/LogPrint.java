package com.cooper.appservice.comm.thread.log;

import com.cooper.appservice.comm.thread.constant.ThreadConfig;
import src.com.cooper.appservice.comm.thread.RunnableObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-23
 * Time: 下午2:18
 * To change this template use File | Settings | File Templates.
 */
public class LogPrint {

    private final static String rootPath = getRootPath();

    private static String getRootPath() {
        String path = LogPrint.class.getResource("/").toString();
        if(path.startsWith("file:/")) {
            path = path.substring("file:/".length());
        }
        return path;
    }

    private final static String outputDirPath = rootPath + "threadLog";

    private final static String outputWarningFilePath = rootPath + "threadLog/warning.log";
    /***/
    private final static String outputRunFilePath = rootPath + "threadLog/run.log";

    /**
     * 执行警告内容的打印操作
     * @param runnableObject
     */
    public static void warn(RunnableObject runnableObject, String takenTimeStr) {//TODO
        if(!ThreadConfig.showStackFlg) {
            return;
        }
//        System.out.println("outputWarningFilePath: " + outputWarningFilePath);
        runnableObject.updateWarnCount();
        String s = "warn: " + runnableObject + " run " + takenTimeStr + "\r\n";
        FileOutputStream fos = null;
        try {
            checkThreadLogDirPath();
            fos = new FileOutputStream(outputWarningFilePath, true);
            fos.write(formatString(s).getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            if(fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
        }
    }
    private static String formatString(String s) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("<yyyy-MM-dd HH:mm:ss>");
        return sdf.format(date) + s;
    }

    public static void checkThreadLogDirPath() {
        File dir = new File(outputDirPath);
//        System.out.println("dir: " + outputDirPath);
        if(!dir.exists() || dir.isFile()) {
            dir.mkdirs();
        }
    }

    public static void printLog(String s) {
        if(ThreadConfig.showLogFlg) {
            printLogAndStack(s);
        }
    }
    public static void printStack(String s) {
        if(ThreadConfig.showStackFlg) {
            printLogAndStack(s);
        }
    }
    /**
     * 打印输出，为了避免多线程混合，采用序列化方式调用
     * @param s
     */
    private synchronized static void printLogAndStack(String s) {
        FileOutputStream fos = null;
        try {
            checkThreadLogDirPath();
            fos = new FileOutputStream(outputRunFilePath, true);
            fos.write(formatString(s).getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            if(fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
        }
    }
}
