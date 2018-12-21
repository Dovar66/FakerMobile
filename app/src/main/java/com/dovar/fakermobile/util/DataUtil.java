package com.dovar.fakermobile.util;


import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.dovar.fakermobile.data.DataBean;
import com.dovar.fakermobile.data.SimulateData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by heweizong on 2018/3/21.
 */
public class DataUtil {
    public static DataBean data;
    private static final String fileName = "fmsDevice.txt";
    private static final String finalFolder = "xx";

    public static void init() {
        data = JSON.parseObject(getDataFromFile()).toJavaObject(DataBean.class);
        data.setNetworkInfo(SimulateData.genNetworkInfo());
        XposedBridge.log(JSON.toJSONString(data.getNetworkInfo()));
    }

    public static DataBean getData() {
        if (data == null) {
            init();
        }
        return data;
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

    private static String getDataFromFile() {
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
        return content.trim();
    }
}
