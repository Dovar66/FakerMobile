package com.dovar.fakermobile.util;

import android.os.Build;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

public class Phone {
    public Phone(XC_LoadPackage.LoadPackageParam sharePkgParam) {
        getType(sharePkgParam);
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
                XposedBridge.log("NetworkInfo");
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
                            param.setResult(SharedPref.getXValue("WifiMAC"));
                            XposedBridge.log("WifiInfo_getMacAddress");
                        }

                    });

            // 内网IP
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

                    });


        } catch (Exception e) {

        }

        // ------------------------基站信息


        // 基站的信号强度
        XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo",
                loadPkgParam.classLoader, "getBSSID", new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                        // TODO Auto-generated method stub
                        super.afterHookedMethod(param);
                        param.setResult(SharedPref.getXValue("BSSID"));
                    }

                });


    }

    public void Telephony(XC_LoadPackage.LoadPackageParam loadPkgParam) {
        String TelePhone = "android.telephony.TelephonyManager";
        try {
            XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPkgParam.classLoader, "getDeviceId", new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam mMethodHookParam) throws Throwable {
                    XposedBridge.log(" TelephonyManager.IMEI_1");

                    return SharedPref.getXValue("IMEI");
                }
            });
            XposedHelpers.findAndHookMethod("com.android.internal.telephony.PhoneSubInfo", loadPkgParam.classLoader, "getDeviceId", new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam mMethodHookParam) throws Throwable {
                    XposedBridge.log(" TelephonyManager.IMEI_2");

                    return SharedPref.getXValue("IMEI");
                }
            });

            if (Build.VERSION.SDK_INT < 22) {
                XposedHelpers.findAndHookMethod("com.android.internal.telephony.gsm.GSMPhone", loadPkgParam.classLoader, "getDeviceId", new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam mMethodHookParam) throws Throwable {
                        XposedBridge.log(" TelephonyManager.IMEI_3");

                        return SharedPref.getXValue("IMEI");
                    }
                });
                XposedHelpers.findAndHookMethod("com.android.internal.telephony.PhoneProxy", loadPkgParam.classLoader, "getDeviceId", new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam mMethodHookParam) throws Throwable {
                        XposedBridge.log(" TelephonyManager.IMEI_4");

                        return SharedPref.getXValue("IMEI");
                    }
                });
            }
        } catch (Exception ex) {
            XposedBridge.log(" IMEI 错误: " + ex.getMessage());
        }

        HookTelephony(TelePhone, loadPkgParam, "getLine1Number", SharedPref.getXValue("PhoneNumber"));
        HookTelephony(TelePhone, loadPkgParam, "getSimSerialNumber", SharedPref.getXValue("SimSerial"));
        HookTelephony(TelePhone, loadPkgParam, "getNetworkOperator", SharedPref.getXValue("networktor")); // 网络运营商类型
        HookTelephony(TelePhone, loadPkgParam, "getNetworkOperatorName", SharedPref.getXValue("Carrier")); // 网络类型名
        HookTelephony(TelePhone, loadPkgParam, "getSimOperator", SharedPref.getXValue("CarrierCode")); // 运营商  (mobile country code + mobile network code)(5 or 6 decimal digits)
        HookTelephony(TelePhone, loadPkgParam, "getSimOperatorName", SharedPref.getXValue("simopename")); // 运营商名字 中国联通

//        HookTelephony(TelePhone, loadPkgParam, "getDeviceSoftwareVersion", SharedPref.getXValue("deviceversion"));// 返系统版本
//        HookTelephony(TelePhone, loadPkgParam, "getSubscriberId", SharedPref.getXValue("IMSI"));
//        HookTelephony(TelePhone, loadPkgParam, "getNetworkCountryIso", SharedPref.getXValue("gjISO")); // 国家iso代码
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
        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPkgParam.classLoader, "getNetworkType", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                //      网络类型
                param.setResult(SharedPref.getintXValue("networkType"));
            }
        });


//        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPkgParam.classLoader, "getPhoneType", new XC_MethodHook() {
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//
//                param.setResult(SharedPref.getintXValue("phonetype"));
//            }
//        });

//        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPkgParam.classLoader, "getSimState", new XC_MethodHook() {
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//
//                param.setResult(SharedPref.getintXValue("SimState"));
//            }
//        });


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

}