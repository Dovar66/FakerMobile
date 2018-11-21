package com.dovar.fakermobile.util;

import android.content.ContentResolver;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XCallback;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

public class Phone {
    public Phone(XC_LoadPackage.LoadPackageParam sharePkgParam) {
//        getType(sharePkgParam);
//        Bluetooth(sharePkgParam);
        Wifi(sharePkgParam);
        Telephony(sharePkgParam);
    }


    // 联网方式
    public void getType(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        /**
         * getType
         * {@link ConnectivityManager#TYPE_MOBILE},
         * {@link ConnectivityManager#TYPE_WIFI},
         * {@link ConnectivityManager#TYPE_WIMAX},
         * {@link ConnectivityManager#TYPE_ETHERNET},
         * {@link ConnectivityManager#TYPE_BLUETOOTH},
         * or other types defined by {@link ConnectivityManager}
         */
        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", loadPackageParam.classLoader, "getType", new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                param.setResult(SharedPref.getintXValue("getType"));
                XposedBridge.log("NetworkInfo_getType");
            }
        });

        // FIXME: 2018/3/20
        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", loadPackageParam.classLoader, "getTypeName", new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                //return String
//                param.setResult("WIFI");
                XposedBridge.log("NetworkInfo_getTypeName");
            }
        });
        XposedHelpers.findAndHookMethod("android.net.ConnectivityManager", loadPackageParam.classLoader, "getNetworkInfo", int.class, new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                //return NetworkInfo
//                param.setResult(SharedPref.getintXValue("getType"));
                XposedBridge.log("ConnectivityManager_getNetworkInfo");
            }
        });


    }

    // ------- MAC 蓝牙-----------------------------------------------------------
    public void Bluetooth(XC_LoadPackage.LoadPackageParam loadPkgParam) {
        try {
            // 双层 MAC
            XposedHelpers.findAndHookMethod(
                    "android.bluetooth.BluetoothAdapter",
                    loadPkgParam.classLoader, "getAddress",
                    new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param)
                                throws Throwable {
                            super.afterHookedMethod(param);
                            param.setResult(SharedPref.getXValue("LYMAC"));
                            XposedBridge.log("BluetoothAdapter");
                        }

                    });
            // 双层MAC
            XposedHelpers.findAndHookMethod(
                    "android.bluetooth.BluetoothDevice",
                    loadPkgParam.classLoader, "getAddress",
                    new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param)
                                throws Throwable {
                            // super.afterHookedMethod(param);
                            param.setResult(SharedPref.getXValue("LYMAC"));
                            XposedBridge.log("BluetoothDevice");
                        }

                    });
        } catch (Exception e) {
            XposedBridge.log("phone MAC HOOK 失败 " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------------------

    // WIF MAC
    public void Wifi(XC_LoadPackage.LoadPackageParam loadPkgParam) {
        try {
            XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo",
                    loadPkgParam.classLoader, "getMacAddress",
                    new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param)
                                throws Throwable {
                            super.afterHookedMethod(param);
                            param.setResult(PoseHelper008.valueMap.getString("WifiMAC"));
                            XposedBridge.log("WifiInfo_getMacAddress");
                        }

                    });

           /* // 内网IP
            XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo",
                    loadPkgParam.classLoader, "getIpAddress",
                    new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param)
                                throws Throwable {
                            super.afterHookedMethod(param);
                            param.setResult(SharedPref.getintXValue("getIP"));
                            // param.setResult(tryParseInt(SharedPref.getXValue("getIP")));
                            XposedBridge.log("WifiInfo_getIpAddress");
                        }

                    });

            XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo",
                    loadPkgParam.classLoader, "getSSID", new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param)
                                throws Throwable {
                            super.afterHookedMethod(param);
                            param.setResult(SharedPref.getXValue("WifiName"));
                            XposedBridge.log("WifiInfo_getSSID");
                        }

                    });*/


        } catch (Exception e) {

        }

       /* // ------------------------基站信息


        // 基站的信号强度
        XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", loadPkgParam.classLoader, "getBSSID", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param)
                    throws Throwable {
                // TODO Auto-generated method stub
                super.afterHookedMethod(param);
                param.setResult(SharedPref.getXValue("BSSID"));
                XposedBridge.log("WifiInfo_BSSID");
            }

        });


        // FIXME: 2018/3/20
        XposedHelpers.findAndHookMethod("android.net.wifi.WifiManager", loadPkgParam.classLoader, "getScanResults", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
//                List<ScanResult>
//                param.setResult();
                XposedBridge.log("WifiManager_getScanResults");
            }

        });*/
    }

    public void Telephony(XC_LoadPackage.LoadPackageParam loadPkgParam) {
//        try {
//            XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), loadPkgParam.classLoader, "getDeviceId", Integer.TYPE.getName(), new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    XposedBridge.log("before getDeviceId:0");
//                    super.beforeHookedMethod(param);
//                }
//
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    XposedBridge.log("getDeviceId:0");
//
//                    param.setResult(SharedPref.getXValue("IMEI"));
//                    super.afterHookedMethod(param);
//                }
//            });
//        } catch (Exception ex) {
//            XposedBridge.log(" IMEI 错误: " + ex.getMessage());
//        }

        try {
            XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), loadPkgParam.classLoader, "getDeviceId", new XC_MethodReplacement(XCallback.PRIORITY_LOWEST) {
                @Override
                protected Object replaceHookedMethod(MethodHookParam mMethodHookParam) throws Throwable {
                    PoseHelper008.initPoseHelper();
                    String imei = PoseHelper008.valueMap.getString("IMEI");//MainActivity.getFromSp("IMEI");// SharedPref.getXValue("IMEI");//SimulateDataTemp.getRandomProp("IMEI");
                    XposedBridge.log(" TelephonyManager.IMEI_1" + imei);

                    return imei;
                }
            });

            XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), loadPkgParam.classLoader, "getSubscriberId", new XC_MethodReplacement(XCallback.PRIORITY_LOWEST) {
                @Override
                protected Object replaceHookedMethod(MethodHookParam mMethodHookParam) throws Throwable {
                    XposedBridge.log("getSubscriberId");
                    return null;
                }
            });

//            XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPkgParam.classLoader, "getDeviceId", new XC_MethodHook(XCallback.PRIORITY_LOWEST) {
//
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                    XposedBridge.log(" TelephonyManager.IMEI_1_after");
//                    param.setResult(SharedPref.getXValue("IMEI"));
//                }
//            });

            // TODO: 2018/11/21 刚注销
           /* XposedHelpers.findAndHookMethod("com.android.internal.telephony.PhoneSubInfo", loadPkgParam.classLoader, "getDeviceId", new XC_MethodReplacement(XCallback.PRIORITY_LOWEST) {
                @Override
                protected Object replaceHookedMethod(MethodHookParam mMethodHookParam) throws Throwable {
                    XposedBridge.log(" TelephonyManager.IMEI_2");

                    return SharedPref.getXValue("IMEI");
                }
            });

            if (Build.VERSION.SDK_INT < 22) {
                XposedHelpers.findAndHookMethod("com.android.internal.telephony.gsm.GSMPhone", loadPkgParam.classLoader, "getDeviceId", new XC_MethodReplacement(XCallback.PRIORITY_LOWEST) {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam mMethodHookParam) throws Throwable {
                        XposedBridge.log(" TelephonyManager.IMEI_3");

                        return SharedPref.getXValue("IMEI");
                    }
                });
                XposedHelpers.findAndHookMethod("com.android.internal.telephony.PhoneProxy", loadPkgParam.classLoader, "getDeviceId", new XC_MethodReplacement(XCallback.PRIORITY_LOWEST) {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam mMethodHookParam) throws Throwable {
                        XposedBridge.log(" TelephonyManager.IMEI_4");

                        return SharedPref.getXValue("IMEI");
                    }
                });
            }*/
        } catch (Exception ex) {
            XposedBridge.log(" IMEI 错误: " + ex.getMessage());
        }

//        HookTelephony(TelePhone, loadPkgParam, "getLine1Number", SharedPref.getXValue("PhoneNumber"));
//        HookTelephony(TelePhone, loadPkgParam, "getSimSerialNumber", SharedPref.getXValue("SimSerial"));
//        HookTelephony(TelePhone, loadPkgParam, "getNetworkOperator", SharedPref.getXValue("networktor")); // 网络运营商类型
//        HookTelephony(TelePhone, loadPkgParam, "getNetworkOperatorName", SharedPref.getXValue("Carrier")); // 网络类型名

//        HookTelephony(TelePhone, loadPkgParam, "getSubscriberId", SharedPref.getXValue("IMSI"));
//
//        // FIXME: 2018/3/20
////        HookTelephony(TelePhone, loadPkgParam, "getDeviceSoftwareVersion", SharedPref.getXValue("deviceversion"));// 返系统版本
//        HookTelephony(TelePhone, loadPkgParam, "getNetworkCountryIso", SharedPref.getXValue("gjISO")); // 国家iso代码

//        HookTelephony(TelePhone, loadPkgParam, "getSimOperator", SharedPref.getXValue("CarrierCode")); // 运营商  (mobile country code + mobile network code)(5 or 6 decimal digits)
//        HookTelephony(TelePhone, loadPkgParam, "getSimOperatorName", SharedPref.getXValue("simopename")); // 运营商名字 中国联通
//        HookTelephony(TelePhone, loadPkgParam, "getSimCountryIso", SharedPref.getXValue("CountryCode")); // 手机卡国家


        /**
         * GPRS    2G(2.5) General Packet Radia Service 114kbps
         * EDGE    2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
         * UMTS    3G WCDMA 联通3G Universal MOBILE Telecommunication System 完整的3G移动通信技术标准
         * CDMA    2G 电信 Code Division Multiple Access 码分多址
         * EVDO_0  3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
         * EVDO_A  3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
         * 1xRTT   2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
         * HSDPA   3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
         * HSUPA   3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
         * HSPA    3G (分HSDPA,HSUPA) High Speed Packet Access
         * IDEN    2G Integrated Dispatch Enhanced Networks 集成数字增强型网络 （属于2G，来自维基百科）
         * EVDO_B  3G EV-DO Rev.B 14.7Mbps 下行 3.5G
         * LTE     4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G
         * EHRPD   3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
         * HSPAP   3G HSPAP 比 HSDPA 快些
         *
         */
//        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPkgParam.classLoader, "getNetworkType", new XC_MethodHook() {
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                //      网络类型
//                param.setResult(SharedPref.getintXValue("networkType"));
//                XposedBridge.log("getNetworkType");
//            }
//        });
//
//
//        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPkgParam.classLoader, "getPhoneType", new XC_MethodHook() {
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//
//                param.setResult(SharedPref.getintXValue("phonetype"));
//                XposedBridge.log("getPhoneType");
//            }
//        });
//
//        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPkgParam.classLoader, "getSimState", new XC_MethodHook() {
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//
//                param.setResult(SharedPref.getintXValue("SimState"));
//                XposedBridge.log("getSimState");
//            }
//        });
//        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPkgParam.classLoader, "hasIccCard", new XC_MethodHook() {
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                param.setResult(true);
//                XposedBridge.log("hasIccCard");
//            }
//        });

        XposedHelpers.findAndHookMethod(Settings.Secure.class.getName(), loadPkgParam.classLoader, "getString", ContentResolver.class.getName(), String.class.getName(), hookID);

//        XposedHelpers.findAndHookMethod(Settings.System.class.getName(), loadPkgParam.classLoader, "getString", ContentResolver.class.getName(), String.class.getName(), hookID_2);
    }

    private void HookTelephony(String hookClass, XC_LoadPackage.LoadPackageParam loadPkgParam, final String funcName, final String value) {
        try {
            XposedHelpers.findAndHookMethod(hookClass, loadPkgParam.classLoader, funcName, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    param.setResult(value);
                    XposedBridge.log(funcName);
                }

            });
        } catch (Exception e) {

        }
    }

    private XC_MethodHook hookID = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            String androidID = PoseHelper008.valueMap.getString("AndroidID");//MainActivity.getFromSp("AndroidID");//SharedPref.getXValue("AndroidID");//SimulateDataTemp.getRandomProp("ANDROID_ID");
            if (param.args.length >= 2 && param.args[1].equals(Settings.Secure.ANDROID_ID)) {
                XposedBridge.log("Settings.Secure.class hook_androidId:" + androidID);
                param.setResult(androidID);
            }
        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
//            if (param.args.length >= 2 && param.args[1].equals(Settings.Secure.ANDROID_ID)) {
//                param.setResult(SharedPref.getXValue("AndroidID"));
//            }
        }
    };

    private XC_MethodHook hookID_2 = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            String androidID = PoseHelper008.valueMap.getString("AndroidID");//MainActivity.getFromSp("AndroidID");// SharedPref.getXValue("AndroidID");//SimulateDataTemp.getRandomProp("ANDROID_ID");
            if (param.args.length >= 2 && param.args[1].equals(Settings.Secure.ANDROID_ID)) {
                XposedBridge.log("Settings.System.class hook_androidId:" + androidID);
                param.setResult(androidID);
            }
        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
//            if (param.args.length >= 2 && param.args[1].equals(Settings.Secure.ANDROID_ID)) {
//                param.setResult(SharedPref.getXValue("AndroidID"));
//            }
        }
    };


    // TODO: 2018/11/21 友盟获取mac的方法
  /*  //获取mac
    public static String q(Context var0) {
        String var1 = "";
        if (Build.VERSION.SDK_INT < 23) {
            var1 = C(var0);
        } else if (Build.VERSION.SDK_INT == 23) {
            var1 = b();
            if (TextUtils.isEmpty(var1)) {
                //d默认为true
                if (com.umeng.analytics.a.d) {
                    var1 = c();
                } else {
                    var1 = C(var0);
                }
            }
        } else {
            var1 = b();
            if (TextUtils.isEmpty(var1)) {
                var1 = C(var0);
            }
        }

        return var1;
    }

    private static String C(Context var0) {
        try {
            WifiManager var1 = (WifiManager)var0.getSystemService("wifi");
            if (a(var0, "android.permission.ACCESS_WIFI_STATE")) {
                WifiInfo var2 = var1.getConnectionInfo();
                return var2.getMacAddress();
            } else {
                return "";
            }
        } catch (Throwable var3) {
            return "";
        }
    }

    private static String b() {
        try {
            Enumeration var0 = NetworkInterface.getNetworkInterfaces();

            NetworkInterface var1;
            do {
                if (!var0.hasMoreElements()) {
                    return null;
                }

                var1 = (NetworkInterface)var0.nextElement();
            } while(!"wlan0".equals(var1.getName()) && !"eth0".equals(var1.getName()));

            byte[] var2 = var1.getHardwareAddress();
            if (var2 != null && var2.length != 0) {
                StringBuilder var3 = new StringBuilder();
                byte[] var4 = var2;
                int var5 = var2.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    byte var7 = var4[var6];
                    var3.append(String.format("%02X:", var7));
                }

                if (var3.length() > 0) {
                    var3.deleteCharAt(var3.length() - 1);
                }

                return var3.toString().toLowerCase(Locale.getDefault());
            } else {
                return null;
            }
        } catch (Throwable var8) {
            return null;
        }
    }

    private static String c() {
        try {
            String[] var0 = new String[]{"/sys/class/net/wlan0/address", "/sys/class/net/eth0/address", "/sys/devices/virtual/net/wlan0/address"};

            for(int var2 = 0; var2 < var0.length; ++var2) {
                try {
                    String var1 = a(var0[var2]);
                    if (var1 != null) {
                        return var1;
                    }
                } catch (Throwable var4) {
                    ;
                }
            }
        } catch (Throwable var5) {

        }

        return null;
    }*/


    // TODO: 2018/11/21  友盟获取android_id的方法
   /* private static String D(Context var0) {
        String var1 = "";
        if (Build.VERSION.SDK_INT < 23) {
            var1 = F(var0);
            if (TextUtils.isEmpty(var1)) {
                var1 = C(var0);
                if (TextUtils.isEmpty(var1)) {
                    var1 = Settings.Secure.getString(var0.getContentResolver(), "android_id");
                    if (TextUtils.isEmpty(var1)) {
                        var1 = d();
                    }
                }
            }
        } else if (Build.VERSION.SDK_INT == 23) {
            var1 = F(var0);
            if (TextUtils.isEmpty(var1)) {
                var1 = b();
                if (TextUtils.isEmpty(var1)) {
                    if (com.umeng.analytics.a.d) {
                        var1 = c();
                    } else {
                        var1 = C(var0);
                    }
                }

                if (TextUtils.isEmpty(var1)) {
                    var1 = Settings.Secure.getString(var0.getContentResolver(), "android_id");
                    if (TextUtils.isEmpty(var1)) {
                        var1 = d();
                    }
                }
            }
        } else {
            var1 = F(var0);
            if (TextUtils.isEmpty(var1)) {
                var1 = d();
                if (TextUtils.isEmpty(var1)) {
                    var1 = Settings.Secure.getString(var0.getContentResolver(), "android_id");
                    if (TextUtils.isEmpty(var1)) {
                        var1 = b();
                        if (TextUtils.isEmpty(var1)) {
                            var1 = C(var0);
                        }
                    }
                }
            }
        }

        return var1;
    }

    private static String E(Context var0) {
        String var1 = "";
        if (Build.VERSION.SDK_INT < 23) {
            var1 = Settings.Secure.getString(var0.getContentResolver(), "android_id");
            if (TextUtils.isEmpty(var1)) {
                var1 = C(var0);
                if (TextUtils.isEmpty(var1)) {
                    var1 = d();
                    if (TextUtils.isEmpty(var1)) {
                        var1 = F(var0);
                    }
                }
            }
        } else if (Build.VERSION.SDK_INT == 23) {
            var1 = Settings.Secure.getString(var0.getContentResolver(), "android_id");
            if (TextUtils.isEmpty(var1)) {
                var1 = b();
                if (TextUtils.isEmpty(var1)) {
                    if (com.umeng.analytics.a.d) {
                        var1 = c();
                    } else {
                        var1 = C(var0);
                    }
                }

                if (TextUtils.isEmpty(var1)) {
                    var1 = d();
                    if (TextUtils.isEmpty(var1)) {
                        var1 = F(var0);
                    }
                }
            }
        } else {
            var1 = Settings.Secure.getString(var0.getContentResolver(), "android_id");
            if (TextUtils.isEmpty(var1)) {
                var1 = d();
                if (TextUtils.isEmpty(var1)) {
                    var1 = F(var0);
                    if (TextUtils.isEmpty(var1)) {
                        var1 = b();
                        if (TextUtils.isEmpty(var1)) {
                            var1 = C(var0);
                        }
                    }
                }
            }
        }

        return var1;
    }*/

    // TODO: 2018/11/21 友盟获取IMEI的方法
   /* private static String F(Context var0) {
        String var1 = "";
        TelephonyManager var2 = (TelephonyManager)var0.getSystemService("phone");
        if (var2 != null) {
            try {
                if (a(var0, "android.permission.READ_PHONE_STATE")) {
                    if (Build.VERSION.SDK_INT > 26) {
                        Class var3 = Class.forName("android.telephony.TelephonyManager");
                        Method var4 = var3.getMethod("getImei", Integer.class);
                        var1 = (String)var4.invoke(var2, var4, 0);
                        if (TextUtils.isEmpty(var1)) {
                            var4 = var3.getMethod("getMeid", Integer.class);
                            var1 = (String)var4.invoke(var2, var4, 0);
                            if (TextUtils.isEmpty(var1)) {
                                var1 = var2.getDeviceId();
                            }
                        }
                    } else {
                        var1 = var2.getDeviceId();
                    }
                }
            } catch (Throwable var5) {
                var1 = "";
            }
        }

        return var1;
    }*/

    // TODO: 2018/11/21 友盟获取网络状态的方法
   /* public static String[] j(Context var0) {
        String[] var1 = new String[]{"", ""};

        try {
            //检查权限
            if (!a(var0, "android.permission.ACCESS_NETWORK_STATE")) {
                var1[0] = "";
                return var1;
            }

            ConnectivityManager var2 = (ConnectivityManager)var0.getSystemService("connectivity");
            if (var2 == null) {
                var1[0] = "";
                return var1;
            }

            NetworkInfo var3 = var2.getNetworkInfo(1);
            if (var3 != null && var3.getState() == NetworkInfo.State.CONNECTED) {
                var1[0] = "Wi-Fi";
                return var1;
            }

            NetworkInfo var4 = var2.getNetworkInfo(0);
            if (var4 != null && var4.getState() == NetworkInfo.State.CONNECTED) {
                var1[0] = "2G/3G";
                var1[1] = var4.getSubtypeName();
                return var1;
            }
        } catch (Throwable var5) {

        }

        return var1;
    }*/

    // TODO: 2018/11/21 友盟获取电话号码的方法
   /* public static String e(Context var0) {
        if (f(var0) == null) {
            return null;
        } else {
            int var1 = var0.getResources().getConfiguration().mcc;
            int var2 = var0.getResources().getConfiguration().mnc;
            if (var1 != 0) {
                String var3 = String.valueOf(var2);
                if (var2 < 10) {
                    var3 = String.format("%02d", var2);
                }

                return var1 + var3;
            } else {
                return null;
            }
        }
    }

    public static String f(Context var0) {
        TelephonyManager var1 = (TelephonyManager)var0.getSystemService("phone");
        String var2 = null;
        //检查权限
        if (a(var0, "android.permission.READ_PHONE_STATE")) {
            var2 = var1.getSubscriberId();
        }

        return var2;
    }*/
}