package com.dovar.fakermobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dovar.fakermobile.util.SharedPref;

import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        String text = "";
        int index = new Random().nextInt(SimulateDataTemp.imeiStore.size());
        HashMap<String, String> data = SimulateDataTemp.imeiStore.get(index);
        SharedPref mySP = new SharedPref(getApplicationContext());
        mySP.setSharedPref("brand", data.get("brand"));
        mySP.setSharedPref("model", data.get("model"));
        mySP.setSharedPref("IMEI", data.get("imei"));
        String display = data.get("display");
        if (!TextUtils.isEmpty(display)) {
            String[] size = display.split("\\*");
            try {
                mySP.setintSharedPref("width", Integer.parseInt(size[0]));
                if (size.length > 1) {
                    mySP.setintSharedPref("height", Integer.parseInt(size[1]));
                }
            } catch (Exception me) {
                me.printStackTrace();
            }
        }

        text += data.get("brand")+"\n";
        text += data.get("model")+"\n";
        text += data.get("imei")+"\n";
        text += data.get("display")+"\n";

        mySP.setSharedPref("API", SimulateDataTemp.getSdkLevel()); //系统的API级别 SDK

        ((TextView) findViewById(R.id.tv)).setText(text);
    }

    private void Save() {
        SharedPref mySP = new SharedPref(getApplicationContext());

    /*
      build 系列
     */
        mySP.setSharedPref("brand", "Huawei"); //设备品牌
        mySP.setSharedPref("model", "HUAWEI G750-T01"); //手机的型号 设备名称
//        mySP.setSharedPref("serial", "aee5060e"); // 串口序列号
//        mySP.setSharedPref("getBaseband", "SCL23KDU1BNG3"); // get 参数
//        mySP.setSharedPref("BaseBand", "REL"); // 固件版本
//        mySP.setSharedPref("board", "msm8916"); //主板
//        mySP.setSharedPref("ABI", "armeabi-v7a"); //  设备指令集名称 1
//        mySP.setSharedPref("ABI2", "armeabi"); //   设备指令集名称 2
//        mySP.setSharedPref("device", "hwG750-T01"); //设备驱动名称
//        mySP.setSharedPref("display", "R7c_11_151207"); //设备显示的版本包 固件版本
        //  指纹 设备的唯一标识。由设备的多个信息拼接合成。
//        mySP.setSharedPref("fingerprint", "Huawei/G750-T01/hwG750-T01:4.2.2/HuaweiG750-T01/C00B152:user/ota-rel-keys,release-keys");
//        mySP.setSharedPref("NAME", "mt6592"); //设备硬件名称
//        mySP.setSharedPref("ID", "KTU84P"); //设备版本号
//        mySP.setSharedPref("Manufacture", "HUAWEI"); //设备制造商
//        mySP.setSharedPref("product", "hwG750-T01"); //设备驱动名称
//        mySP.setSharedPref("booltloader", "unknown"); //设备引导程序版本号
//        mySP.setSharedPref("host", "ubuntu-121-114"); //设备主机地址
//        mySP.setSharedPref("build_tags", "release-keys"); //设备标签
//        mySP.setSharedPref("shenbei_type", "user"); //设备版本类型
//        mySP.setSharedPref("incrementalincremental", "eng.root.20151207"); //源码控制版本号
        mySP.setSharedPref("AndroidVer", "5.1"); //系统版本
        mySP.setSharedPref("API", "21"); //系统的API级别 SDK

//        mySP.setintSharedPref("time", 123456789);// 固件时间
//        mySP.setSharedPref("AndroidID", "fc4ad25f66d554a8"); //  android id
//        mySP.setSharedPref("DESCRIPTION", "jfltexx-user 4.3 JSS15J I9505XXUEML1 release-keys"); //用户的KEY




 /*
     TelephonyManager相关
     */
        mySP.setSharedPref("IMEI", "506066104722640"); // 序列号IMEI
//        mySP.setSharedPref("LYMAC", "BC:1A:EA:D9:8D:98");//蓝牙 MAC
//        mySP.setSharedPref("WifiMAC", "a8:a6:68:a3:d9:ef"); // WIF mac地址
//        mySP.setSharedPref("WifiName", "免费WIFI"); // 无线路由器名
//        mySP.setSharedPref("BSSID", "ce:ea:8c:1a:5c:b2"); // 无线路由器地址
//        mySP.setSharedPref("IMSI", "460017932859596");
        mySP.setSharedPref("PhoneNumber", "13117511178"); // 手机号码
        mySP.setSharedPref("SimSerial", "89860179328595969501"); // 手机卡序列号
        mySP.setSharedPref("networktor", "46001"); // 网络运营商类型
        mySP.setSharedPref("Carrier", "中国联通");// 网络类型名
        mySP.setSharedPref("CarrierCode", "46001"); // 运营商
        mySP.setSharedPref("simopename", "中国联通");// 运营商名字
//        mySP.setSharedPref("gjISO", "cn");// 国家iso代码
//        mySP.setSharedPref("CountryCode", "cn");// 手机卡国家
//        mySP.setSharedPref("deviceversion", "100"); // 返回系统版本

        mySP.setintSharedPref("getType", 1); // 联网方式 1为WIFI 2为流量
        mySP.setintSharedPref("networkType", 6);//      网络类型
        mySP.setintSharedPref("phonetype", 5); // 手机类型
//        mySP.setintSharedPref("SimState", 10); // 手机卡状态
        mySP.setintSharedPref("width", 720); // 宽
        mySP.setintSharedPref("height", 1920); // 高
//        mySP.setintSharedPref("getIP", -123456789); // 内网ip(wifl可用)
    /*
     屏幕相关
     */
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
