package com.dovar.fakermobile;

import android.app.ActivityManager;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dovar.fakermobile.data.DataBean;
import com.dovar.fakermobile.data.SimulateData;
import com.dovar.fakermobile.util.DataUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText et_imei;//用于手动修改IMEI和androidId
    private EditText et_android_id;

    private String url = "http://";//刷留存时需要使用的上传与下载数据的接口地址，POST用于上传，GET用于下载，请自行提供

    public static String targetPackage = "com.dovar.testxp";//目标应用的包名，只会hook该应用


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_imei = (EditText) findViewById(R.id.et_imei);
        et_android_id = (EditText) findViewById(R.id.et_android_id);
        final RadioGroup radioGroup = findViewById(R.id.rg_container);
        radioGroup.check(getModeFromSP() == 0 ? R.id.rb_new : R.id.rb_old);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_new) {
                    saveModeToSP(0);
                } else {
                    saveModeToSP(1);
                }
            }
        });
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_new) {
                    DataBean data = generateDatas();
                    update(data);
                    uploadToServer(data);
                    Toast.makeText(MainActivity.this, "数据已生成", Toast.LENGTH_SHORT).show();
                } else {
                    downloadFromServer(new HttpCallback() {
                        @Override
                        public void success(final DataBean data) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (data == null) {
                                        Toast.makeText(MainActivity.this, "请求成功未返回数据", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    update(data);
                                    Toast.makeText(MainActivity.this, "数据已生成", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void fail() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
        });

        SimulateData.init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (requestTask != null) {
            requestTask.cancel(false);
        }
        if (postTask != null) {
            postTask.cancel(false);
        }
    }

    //启用新数据
    private void update(DataBean data) {
        if (data == null) return;
        //补齐数据
        data.setWifiMAC(SimulateData.getRandomProp("MAC"));
//        result.setBoard("msm8916");
//        result.setManufacture(data.get("brand"));
//        result.setId("KTU84P");
//        result.setDevice("hwG750-T01");
//        result.setSerial("aee5060e");
        //保存
        DataUtil.saveDataToFile(JSON.toJSONString(data));
        //界面展示
        ((TextView) findViewById(R.id.tv)).setText(JSON.toJSONString(data));

        //杀进程并清除应用数据
        killProcess(targetPackage);
        execCommand(targetPackage);
    }

    //本地生成部分数据，要与后台请求的数据相对应
    private DataBean generateDatas() {
        DataBean result = new DataBean();

        //从数据表中随机读取数据
        int index = new Random().nextInt(SimulateData.imeiStore.size());
        HashMap<String, String> data = SimulateData.imeiStore.get(index);
        result.setBrand(data.get("brand"));//品牌
        result.setTerm(data.get("model"));//型号
        String display = data.get("display");//分辨率
        if (!TextUtils.isEmpty(display)) {
            result.setDisplay(display);
        }

        String imei = SimulateData.genIMEI(data.get("imei"));
        String imei_et = et_imei.getText().toString();
        if (!TextUtils.isEmpty(imei_et)) {
            imei = imei_et;
        }
        String androidId = SimulateData.getRandomProp("ANDROID_ID");
        String android_id_et = et_android_id.getText().toString();
        if (!TextUtils.isEmpty(android_id_et)) {
            androidId = android_id_et;
        }

        result.setDevice_id(imei);
        result.setAndroid_id(androidId);
        String api = SimulateData.randomSdkLevel();
        result.setApi(api);
        result.setOs_version(SimulateData.getSdkVersion(api));
        return result;
    }

    interface HttpCallback {
        void success(DataBean data);

        void fail();
    }


    AsyncTask<String, String, String> postTask;
    AsyncTask<String, String, Integer> requestTask;

    //从服务器获取数据刷留存
    private void downloadFromServer(final HttpCallback callback) {
        if (requestTask != null && requestTask.getStatus() == AsyncTask.Status.RUNNING) {
            Toast.makeText(MainActivity.this, "当前GET请求尚未完成", Toast.LENGTH_SHORT).show();
            return;
        }
        final String path = this.url;
        requestTask = new AsyncTask<String, String, Integer>() {
            @Override
            protected Integer doInBackground(String... strings) {
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(10000);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();
                    int code = connection.getResponseCode();
                    if (code >= 200 && code < 300) {
                        InputStream inputStream = connection.getInputStream();
                        byte[] data = new byte[1024];
                        StringBuffer sb = new StringBuffer();
                        while (inputStream.read(data) != -1) {
                            String s = new String(data, Charset.forName("utf-8"));
                            sb.append(s);
                        }
                        inputStream.close();
                        callback.success(JSON.parseObject(sb.toString()).getJSONObject("data").toJavaObject(DataBean.class));
                    } else {
                        callback.fail();
                    }
                    connection.disconnect();
                    return code;
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.fail();
                }
                return 0;
            }
        }.execute();
    }


    //数据上传
    private void uploadToServer(final DataBean data) {
        if (data == null) return;
        final String path = this.url;
        if (postTask != null && postTask.getStatus() == AsyncTask.Status.RUNNING) {
            Toast.makeText(MainActivity.this, "当前POST请求尚未完成", Toast.LENGTH_SHORT).show();
            return;
        }

        postTask = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String result = "上传异常";
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(10000);
                    connection.setRequestMethod("POST");
                    connection.addRequestProperty("Content-Type", "application/json");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.connect();
                    OutputStream outputStream = connection.getOutputStream();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("device_id", data.getDevice_id());
                    params.put("android_id", data.getAndroid_id());
                    params.put("term", data.getTerm());
                    params.put("brand", data.getBrand());
                    params.put("os_version", data.getOs_version());
                    params.put("api", data.getApi());
                    params.put("display", data.getDisplay());
                    outputStream.write(params.toString().getBytes());
                    outputStream.flush();
                    outputStream.close();
                    int code = connection.getResponseCode();
                    if (code >= 200 && code < 300) {
                        InputStream inputStream = connection.getInputStream();
                        byte[] data = new byte[1024];
                        StringBuffer sb = new StringBuffer();
                        while (inputStream.read(data) != -1) {
                            String s = new String(data, Charset.forName("utf-8"));
                            sb.append(s);
                        }
                        inputStream.close();
                        result = "数据上传成功";
                    } else {
                        result = "数据上传失败";
                    }
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }


    //清除应用缓存的用户数据
    //友盟会保存数据到应用本地，其中有用于识别用户唯一性的凭据
    public static void execCommand(String packageName) {
        String cmd = "pm clear " + packageName;
        try {
            Process proc = Runtime.getRuntime().exec("su");
            DataOutputStream osl = new DataOutputStream(proc.getOutputStream());
            osl.writeBytes(cmd + "\n");
            osl.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //强杀目标应用进程
    public void killProcess(String pkn) {
        ActivityManager am = ((ActivityManager) getSystemService(ACTIVITY_SERVICE));
        if (am != null) {
            am.killBackgroundProcesses(pkn);
        }
    }

    private String spName = "com.dovar.fms";
    private String mode_key = "mode";//0.刷新增 1.刷留存

    void saveModeToSP(int value) {
        SharedPreferences sp = getSharedPreferences(spName, MODE_PRIVATE);
        sp.edit().putInt(mode_key, value).apply();
    }

    int getModeFromSP() {
        SharedPreferences sp = getSharedPreferences(spName, MODE_PRIVATE);
        return sp.getInt(mode_key, 0);
    }

/*    private void Save() {
        mySP.setSharedPref("brand", "Huawei"); //设备品牌
        mySP.setSharedPref("model", "HUAWEI G750-T01"); //手机的型号 设备名称
        mySP.setSharedPref("AndroidVer", "5.1"); //系统版本
        mySP.setSharedPref("API", "22"); //系统的API级别 SDK
        mySP.setSharedPref("AndroidID", "fc4ad25f66d554a8"); //  android id

        mySP.setSharedPref("serial", "aee5060e"); // 串口序列号
        mySP.setSharedPref("getBaseband", "SCL23KDU1BNG3"); // get 参数
        mySP.setSharedPref("BaseBand", "REL"); // 固件版本
        mySP.setSharedPref("board", "msm8916"); //主板
        mySP.setSharedPref("ABI", "armeabi-v7a"); //  设备指令集名称 1
        mySP.setSharedPref("ABI2", "armeabi"); //   设备指令集名称 2
        mySP.setSharedPref("device", "hwG750-T01"); //设备驱动名称
        mySP.setSharedPref("display", "R7c_11_151207"); //设备显示的版本包 固件版本
//          指纹 设备的唯一标识。由设备的多个信息拼接合成。
        mySP.setSharedPref("fingerprint", "Huawei/G750-T01/hwG750-T01:4.2.2/HuaweiG750-T01/C00B152:user/ota-rel-keys,release-keys");
        mySP.setSharedPref("NAME", "mt6592"); //设备硬件名称
        mySP.setSharedPref("ID", "KTU84P"); //设备版本号
        mySP.setSharedPref("Manufacture", "HUAWEI"); //设备制造商
        mySP.setSharedPref("product", "hwG750-T01"); //设备驱动名称
        mySP.setSharedPref("booltloader", "unknown"); //设备引导程序版本号
        mySP.setSharedPref("host", "ubuntu-121-114"); //设备主机地址
        mySP.setSharedPref("build_tags", "release-keys"); //设备标签
        mySP.setSharedPref("shenbei_type", "user"); //设备版本类型
        mySP.setSharedPref("incrementalincremental", "eng.root.20151207"); //源码控制版本号

        mySP.setintSharedPref("time", 123456789);// 固件时间
        mySP.setSharedPref("DESCRIPTION", "jfltexx-user 4.3 JSS15J I9505XXUEML1 release-keys"); //用户的KEY


        mySP.setSharedPref("IMEI", "506066104722640"); // 序列号IMEI
        mySP.setSharedPref("PhoneNumber", "13117511178"); // 手机号码

        mySP.setSharedPref("SimSerial", "89860179328595969501"); // 手机卡序列号
        mySP.setSharedPref("networktor", "46001"); // 网络运营商类型
        mySP.setSharedPref("CarrierCode", "46001"); // 运营商
        mySP.setSharedPref("Carrier", "中国联通");// 网络类型名
        mySP.setSharedPref("simopename", "中国联通");// 运营商名字

        mySP.setintSharedPref("getType", 1); // 联网方式 1为WIFI 2为流量
        mySP.setintSharedPref("networkType", 6);//      网络类型

        mySP.setintSharedPref("SimState", 5); // 手机卡状态 SIM_STATE_READY

        mySP.setintSharedPref("phonetype", 5); // 手机类型
        mySP.setSharedPref("LYMAC", "BC:1A:EA:D9:8D:98");//蓝牙 MAC
        mySP.setSharedPref("WifiMAC", "a8:a6:68:a3:d9:ef"); // WIF mac地址
        mySP.setSharedPref("WifiName", "免费WIFI"); // 无线路由器名
        mySP.setSharedPref("BSSID", "ce:ea:8c:1a:5c:b2"); // 无线路由器地址
        mySP.setSharedPref("IMSI", "460017932859596");
        mySP.setSharedPref("gjISO", "cn");// 国家iso代码
        mySP.setSharedPref("CountryCode", "cn");// 手机卡国家
        mySP.setSharedPref("deviceversion", "100"); // 返回系统版本
        mySP.setintSharedPref("getIP", -123456789); // 内网ip(wifl可用)


        mySP.setintSharedPref("width", 480); // 宽
        mySP.setintSharedPref("height", 720); // 高
        mySP.setintSharedPref("DPI", 320); // dpi
        mySP.setfloatharedPref("density", (float) 2.0); // density
        mySP.setfloatharedPref("xdpi", (float) 200.123);
        mySP.setfloatharedPref("ydpi", (float) 211.123);
        mySP.setfloatharedPref("scaledDensity", (float) 2.0); // 字体缩放比例



 *//*
    显卡信息
     *//*
        mySP.setSharedPref("GLRenderer", "Adreno (TM) 111"); // GPU
        mySP.setSharedPref("GLVendor", "UFU");// GPU厂商


            *//*
            位置信息
        30.2425140000,120.1404220000 杭州
     *//*

        mySP.setfloatharedPref("lat", (float) 30.2425140000); // 纬度
        mySP.setfloatharedPref("log", (float) 120.1404220000); // 经度
    }*/
}
