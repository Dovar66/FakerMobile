package com.dovar.fakermobile.util;


import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by heweizong on 2018/3/21.
 */

public class PoseHelper008 {
    public static JSONObject valueMap = new JSONObject();
    public static String finalFolder = "xx";
    public static final String fileName = "xposeDevice.txt";

    static {
        valueMap.put("connect_mode", "1");
    }

    public static void initPoseHelper() {
        valueMap = JSON.parseObject(getDataFromFile());
    }

    public static void saveDataToFile(String content) {
        File mFile = new File(Environment.getExternalStorageDirectory(), finalFolder);
        if (!mFile.exists()) {
            mFile.mkdir();
        }
        File mFile1 = new File(Environment.getExternalStorageDirectory(), finalFolder + File.separator + fileName);
        try {
            if (!mFile1.exists()) {
                mFile1.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(mFile1);
            fos.write(content.getBytes("UTF-8"));
            fos.close();
        } catch (IOException mE) {
            mE.printStackTrace();
        }
    }

    public static String getDataFromFile() {
        File mFile = new File(Environment.getExternalStorageDirectory(), finalFolder);
        if (!mFile.exists()) {
            mFile.mkdir();
        }
        File mFile1 = new File(Environment.getExternalStorageDirectory(), finalFolder + File.separator + fileName);
        String content = "";
        if (mFile1.exists()) {
            try {
                BufferedReader bfr = new BufferedReader(new FileReader(mFile1));
                String str = "";
                do {
                    str = bfr.readLine();
                    if (str != null) {
                        content = content + str + "\n";
                    }
                } while (str != null);
                bfr.close();
            } catch (IOException mE) {
                mE.printStackTrace();
            }
        }

        XposedBridge.log("getDataFromFile: " + content);
        return content.trim();
    }
}
