package com.bigshark.smartlight;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Looper;

import com.bigshark.smartlight.utils.Contact;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by ch on 2017/5/10.
 *
 * @email 869360026@qq.com
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = CrashHandler.class.getSimpleName();
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;


    private CrashHandler() {
    }


    public static CrashHandler getInstance() {
        return INSTANCE;
    }


    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        System.out.println("uncaughtException");
        File file = new File(Contact.BASE_FILE_URL+"/smartLight/");
        if (!file.exists()){
            file.mkdirs();
        }
        file = new File(Contact.BASE_FILE_URL+"/smartLight/log.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
                FileWriter stringWriter = new FileWriter(file);
                stringWriter.append(ex.getMessage());
                stringWriter.flush();
                stringWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }
        // new Handler(Looper.getMainLooper()).post(new Runnable() {
        // @Override
        // public void run() {
        // new AlertDialog.Builder(mContext).setTitle("提示")
        // .setMessage("程序崩溃了...").setNeutralButton("我知道了", null)
        // .create().show();
        // }
        // });


        return true;
    }

}
