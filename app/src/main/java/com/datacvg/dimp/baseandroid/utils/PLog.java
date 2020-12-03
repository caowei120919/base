package com.datacvg.dimp.baseandroid.utils;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description : 日志打印工具类
 */
public class PLog {
    public static boolean isDebug = true ; //是否是debug模式
    public static String path ; //路径，日志文件存放的路径
    public static final String PLOG_FILE_NAME = "log.txt";

    //是否写入日志文件
    public static final boolean WRITE_LOG_TO_FILE = false ;

    /**
     * 错误信息
     * @param tag
     * @param message
     */
    public static void e(String tag, String message){
        if(isDebug){    //是否处于debug模式
            Log.e(tag,log(message));
        }
        if(WRITE_LOG_TO_FILE){  //是否需要生成日志文件
            writeLogToFile("e",tag,message);
        }
    }

    /**
     * 警告信息
     * @param tag
     * @param message
     */
    public static void w(String tag, String message){
        if(isDebug){    //是否处于debug模式
            Log.w(tag,log(message));
        }
        if(WRITE_LOG_TO_FILE){  //是否需要生成日志文件
            writeLogToFile("w",tag,message);
        }
    }

    /**
     * 调试信息
     * @param tag
     * @param message
     */
    public static void d(String tag, String message){
        if(isDebug){    //是否处于debug模式
            Log.d(tag,log(message));
        }
        if(WRITE_LOG_TO_FILE){  //是否需要生成日志文件
            writeLogToFile("d",tag,message);
        }
    }

    /**
     * 提示信息
     * @param tag
     * @param message
     */
    public static void i(String tag, String message){
        if(isDebug){    //是否处于debug模式
            Log.i(tag,log(message));
        }
        if(WRITE_LOG_TO_FILE){  //是否需要生成日志文件
            writeLogToFile("i",tag,message);
        }
    }

    public static void e(String message){
        e(getClassName(),message);
    }

    public static void w(String message){
        w(getClassName(),message);
    }

    public static void d(String message){
        d(getClassName(),message);
    }

    public static void i(String message){
        i(getClassName(),message);
    }

    /**
     * 生成日志文件
     * @param e
     * @param tag
     * @param message
     */
    private static void writeLogToFile(String e, String tag, String message) {
        isExist(path);
        String needWriteMessage = "\r\n" +
                TimeUtils.getCurDateStr(TimeUtils.FORMAT_YMDHMS_CN) +
                "\r\n" +
                e + "     " + tag + "\r\n" + message;
        File file = new File(path,PLOG_FILE_NAME);
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(needWriteMessage);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 根据路径判断文件是否存在,如果文件不存在，则创建文件
     * @param path
     */
    private static void isExist(String path) {
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 打印信息
     * @param message
     * @return
     */
    private static String log(String message) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement stackTrace = stackTraceElements[5];
        String className = stackTrace.getClassName() ;  //获取当前类名
        //重新处理打印出来的类名信息，使其以".java"结尾
        className = className.substring(className.lastIndexOf(".") + 1) + ".java" ;
        int lineNum = stackTrace.getLineNumber() ;  //获取对应的行数
        if(lineNum < 0){
            lineNum = 0 ;
        }
        //重新构造自己希望展现出来的message信息，此处包含了文件名，所在行数，以及打印出来的信息
        return "(" + className + ":" + lineNum + ")" + message;
    }

    /**
     * 获取当前类名
     * @return
     */
    private static String getClassName() {
        String result ;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[2];
        result = stackTraceElement.getClassName() ;
        result = result.substring(result.lastIndexOf(".") + 1);
        int index = result.indexOf("$");    //用于提出匿名内部类
        return index == -1 ? result : result.substring(0,index) ;
    }
}
