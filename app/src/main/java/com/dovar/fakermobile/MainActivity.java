package com.dovar.fakermobile;

import android.os.Bundle;
import android.support.annotation.Size;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dovar.fakermobile.util.SharedPref;

import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText et_imei;
    EditText et_android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_imei = (EditText) findViewById(R.id.et_imei);
        et_android_id = (EditText) findViewById(R.id.et_android_id);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                updateDatas();
                Toast.makeText(MainActivity.this, "数据已生成", Toast.LENGTH_SHORT).show();
            }
        });

        SimulateDataTemp.init(this);
        Save();
    }

    private void updateDatas() {
        String imei_et = et_imei.getText().toString();
        String android_id_et = et_android_id.getText().toString();

        String text = "";
        int index = new Random().nextInt(SimulateDataTemp.imeiStore.size());
        HashMap<String, String> data = SimulateDataTemp.imeiStore.get(index);
        SharedPref mySP = new SharedPref(getApplicationContext());
        mySP.setSharedPref("brand", data.get("brand"));
        mySP.setSharedPref("model", data.get("model"));
        String imei = SimulateDataTemp.generateImei(data.get("imei"));
        if (!TextUtils.isEmpty(imei_et)) {
            imei = imei_et;
        }
        mySP.setSharedPref("IMEI", imei);
        String display = data.get("display");
        if (!TextUtils.isEmpty(display)) {
            String[] size = display.split("\\*");
            try {
                mySP.setintSharedPref("width", 1440);
                if (size.length > 1) {
                    mySP.setintSharedPref("height", 2560);
                }
            } catch (Exception me) {
                me.printStackTrace();
            }
        }

        text += data.get("brand") + "\n";
        text += data.get("model") + "\n";
        text += imei + "\n";
        text += data.get("display") + "\n";

        String api = SimulateDataTemp.randomSdkLevel();
        mySP.setSharedPref("API", api); //系统的API级别 SDK
        mySP.setSharedPref("AndroidVer", SimulateDataTemp.getSdkVersion(api));

        text += api + "\n";
        text += SimulateDataTemp.getSdkVersion(api) + "\n";

        @Size(3) String[] simData = SimulateDataTemp.getSimData();
        mySP.setSharedPref("Carrier", simData[0]);// 网络类型名
        mySP.setSharedPref("simopename", simData[0]);// 运营商名字
        mySP.setSharedPref("SimSerial", simData[1]);//手机卡序列号
        mySP.setSharedPref("networktor", simData[2]); // 网络运营商类型
        mySP.setSharedPref("CarrierCode", simData[2]); // 运营商

        text += simData[0] + "\n";
        text += simData[1] + "\n";
        text += simData[2] + "\n";

        String phoneNum = SimulateDataTemp.getPhoneNumber(simData[2]);
        mySP.setSharedPref("PhoneNumber", phoneNum); // 手机号码
        text += phoneNum + "\n";

        @Size(2) int[] networkType = SimulateDataTemp.getNetworkType(simData[0]);
        mySP.setintSharedPref("getType", networkType[0]); // 联网方式
        mySP.setintSharedPref("networkType", networkType[1]);//网络类型

        text += networkType[0] + "\n";
        text += networkType[1] + "\n";
//        TelephonyManager tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
//        text += tm.getSimOperatorName() + "\n";//CMCC
//        text += tm.getSimOperator() + "\n";//46007
//        text += tm.getLine1Number() + "\n";//手机号码 需要权限android.Manifest.permission#READ_PHONE_STATE
//        text += tm.getNetworkOperator() + "\n";//46007
//        text += tm.getNetworkOperatorName() + "\n";//CHINA MOBILE
//        text += tm.getSimSerialNumber() + "\n";
//        text += tm.getNetworkType();
        String androidId = SimulateDataTemp.getRandData(16);
        if (!TextUtils.isEmpty(android_id_et)) {
            androidId = android_id_et;
        }
        mySP.setSharedPref("AndroidID", androidId); //  android id

        text += androidId;
        ((TextView) findViewById(R.id.tv)).setText(text);
    }

    private void Save() {
        SharedPref mySP = new SharedPref(getApplicationContext());

    /*
      build 系列
     */
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




 /*
     TelephonyManager相关
     */
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


    /*
     屏幕相关
     */
        mySP.setintSharedPref("width", 480); // 宽
        mySP.setintSharedPref("height", 720); // 高
//        mySP.setintSharedPref("DPI", 320); // dpi
//        mySP.setfloatharedPref("density", (float) 2.0); // density
//        mySP.setfloatharedPref("xdpi", (float) 200.123);
//        mySP.setfloatharedPref("ydpi", (float) 211.123);
//        mySP.setfloatharedPref("scaledDensity", (float) 2.0); // 字体缩放比例



 /*
    显卡信息
     */
//        mySP.setSharedPref("GLRenderer", "Adreno (TM) 111"); // GPU
//        mySP.setSharedPref("GLVendor", "UFU");// GPU厂商


            /*
            位置信息
        30.2425140000,120.1404220000 杭州
     */

//        mySP.setfloatharedPref("lat", (float) 30.2425140000); // 纬度
//        mySP.setfloatharedPref("log", (float) 120.1404220000); // 经度


        Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
    }
}
