package com.dovar.fakermobile.fms;

import android.os.Build;

import com.dovar.fakermobile.util.DataUtil;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Administrator on 2017/4/17 0017.
 * Build相关
 */

public class XBuild {

    public static void hook(XC_LoadPackage.LoadPackageParam loadPkgParam) {
        BuildProp(loadPkgParam);
//        AndroidSerial(loadPkgParam);
//        BaseBand(loadPkgParam);
    }


/*    static void AndroidSerial(XC_LoadPackage.LoadPackageParam loadPkgParam) {
        try {
            Class<?> classBuild = XposedHelpers.findClass("android.os.Build", loadPkgParam.classLoader);
            XposedHelpers.setStaticObjectField(classBuild, "SERIAL", SharedPref.getXValue("serial")); // 串口序列号

            Class<?> classSysProp = Class.forName("android.os.SystemProperties");
            XposedHelpers.findAndHookMethod(classSysProp, "get", String.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    String serialno = (String) param.args[0];

                    if (serialno.equals("gsm.version.baseband")
                            || serialno.equals("no message")
                            ) {
                        param.setResult(SharedPref.getXValue("getBaseband"));
                    }
                }

            });

            XposedHelpers.findAndHookMethod(classSysProp, "get", String.class, String.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);

                    String serialno = (String) param.args[0];
                    if (serialno.equals("gsm.version.baseband")
                            || serialno.equals("no message")
                            ) {
                        param.setResult(SharedPref.getXValue("getBaseband"));
                    }
                }

            });
        } catch (Exception ex) {
            XposedBridge.log(" AndroidSerial 错误: " + ex.getMessage());
        }
    }

    static void BaseBand(XC_LoadPackage.LoadPackageParam loadPkgParam) {
        try {

            XposedHelpers.findAndHookMethod("android.os.Build", loadPkgParam.classLoader, "getRadioVersion", new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param)
                        throws Throwable {
                    //固件版本
                    param.setResult(SharedPref.getXValue("BaseBand"));
                    XposedBridge.log("getRadioVersion");
                }

            });

        } catch (Exception e) {
            XposedBridge.log(" BaseBand 错误: " + e.getMessage());
        }
    }*/


    private static void BuildProp(XC_LoadPackage.LoadPackageParam loadPkgParam) {
        //systemProperties hook
        XposedHelpers.findAndHookMethod("android.os.SystemProperties", loadPkgParam.classLoader, "get", String.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String methodName = param.method.getName();
                if (methodName.startsWith("get")) {
                    setBuildData();
                    super.beforeHookedMethod(param);
                }
            }
        });

        XposedHelpers.findAndHookMethod("android.os.SystemProperties", loadPkgParam.classLoader, "get", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String methodName = param.method.getName();
                if (methodName.startsWith("get")) {
                    setBuildData();
                    super.beforeHookedMethod(param);
                }
            }
        });
    }

    private static void setBuildData() {
        XposedBridge.log("getDeviceInfo_string");
        try {
            XposedHelpers.setStaticObjectField(Build.class, "MODEL", DataUtil.getData().getTerm());
            XposedHelpers.findField(Build.class, "BOARD").set(null, DataUtil.getData().getBoard());
            XposedHelpers.setStaticObjectField(Build.class, "BRAND", DataUtil.getData().getBrand());
            XposedHelpers.findField(Build.class, "MANUFACTURER").set(null, DataUtil.getData().getManufacture());
            XposedHelpers.findField(Build.class, "ID").set(null, DataUtil.getData().getId());
            XposedHelpers.findField(Build.class, "DEVICE").set(null, DataUtil.getData().getDevice());
            XposedHelpers.setStaticObjectField(Build.class, "SERIAL", DataUtil.getData().getSerial()); // 串口序列号
            XposedHelpers.setStaticObjectField(android.os.Build.VERSION.class, "SDK", DataUtil.getData().getApi());
            XposedHelpers.setStaticObjectField(android.os.Build.VERSION.class, "RELEASE", DataUtil.getData().getOs_version());
//                        XposedHelpers.findField(Build.class, "TIME").set(null, SharedPref.getintXValue("time"));  // 固件时间build
//                        XposedHelpers.findField(Build.VERSION.class, "SDK_INT").set(null, pre.getInt("sdkint", 6));

//                        XposedHelpers.findField(Build.class, "CPU_ABI").set(null, SharedPref.getXValue("ABI"));
//                        XposedHelpers.findField(Build.class, "CPU_ABI2").set(null, SharedPref.getXValue("ABI2"));
//                        XposedHelpers.findField(Build.class, "DISPLAY").set(null, SharedPref.getXValue("display"));
//                        XposedHelpers.findField(Build.class, "FINGERPRINT").set(null, SharedPref.getXValue("fingerprint"));
//                        XposedHelpers.findField(Build.class, "HARDWARE").set(null, SharedPref.getXValue("NAME"));
//                        XposedHelpers.findField(Build.class, "PRODUCT").set(null, SharedPref.getXValue("product"));
//                        XposedHelpers.findField(Build.class, "BOOTLOADER").set(null, SharedPref.getXValue("booltloader")); //主板引导程序
//                        XposedHelpers.findField(Build.class, "HOST").set(null, SharedPref.getXValue("host"));  // 设备主机地址
//                        XposedHelpers.findField(Build.class, "TAGS").set(null, SharedPref.getXValue("build_tags"));  //描述build的标签
//                        XposedHelpers.findField(Build.class, "TYPE").set(null, SharedPref.getXValue("shenbei_type")); //设备版本类型
//                        XposedHelpers.findField(Build.VERSION.class, "INCREMENTAL").set(null, SharedPref.getXValue("incrementalincremental")); //源码控制版本号
//                        XposedHelpers.findField(android.os.Build.VERSION.class, "CODENAME").set(null, "REL"); //写死就行 这个值为固定
//                        XposedHelpers.setStaticObjectField(android.os.Build.class, "RADIO", pre.getString("radio", null));
        } catch (Exception e) {
            XposedBridge.log(" BuildProp 错误: " + e.getMessage());
        }
    }
}
