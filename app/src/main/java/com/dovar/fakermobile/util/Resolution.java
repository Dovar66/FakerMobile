package com.dovar.fakermobile.util;

import android.graphics.Point;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Random;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XCallback;

/**
 * Created by Administrator on 2017/4/17 0017.
 * 屏幕相关
 */

public class Resolution {

    public void Display(XC_LoadPackage.LoadPackageParam loadPkgParam) {

        /*    try {
         *//**
         *  DisplayMetrics dm = new DisplayMetrics(); display.getRealMetrics(dm); height = dm.heightPixels;
         *  针对这种方式的获取分辨率
         *//*
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getRealMetrics", DisplayMetrics.class, new XC_MethodHook(XCallback.PRIORITY_LOWEST) {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
//                    final int dpi = SharedPref.getintXValue("DPI");
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
//                    metrics.densityDpi = dpi;
                    metrics.widthPixels = SharedPref.getintXValue("width");
                    metrics.heightPixels = SharedPref.getintXValue("height");
                    XposedBridge.log("getRealMetrics_dpi");
                }

            });
        } catch (Exception e) {

        }


        try {
            *//**
         * DisplayMetrics dm = new DisplayMetrics(); display.getMetrics(dm); height = dm.heightPixels;
         * 针对这种方式的获取分辨率
         *//*
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.widthPixels = SharedPref.getintXValue("width");
                    metrics.heightPixels = SharedPref.getintXValue("height");
//                    metrics.density = SharedPref.getfloatXValue("density");
//                    metrics.scaledDensity = SharedPref.getfloatXValue("scaledDensity");
//                    metrics.densityDpi = tryParseInt(SharedPref.getXValue("DPI"));
//                    metrics.xdpi = SharedPref.getfloatXValue("xdpi");
//                    metrics.ydpi = SharedPref.getfloatXValue("ydpi");

                    XposedBridge.log("getMetrics");
                }

            });
        } catch (Exception e) {

        }

        *//**
         * Display.getWidth()  Display.getHeight()
         * 针对这种方式的获取分辨率
         *//*
        XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getWidth", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                param.setResult(SharedPref.getintXValue("width"));
                XposedBridge.log("getWidth");
            }
        });

        XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getHeight", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                param.setResult(SharedPref.getintXValue("height"));
                XposedBridge.log("getHeight");
            }
        });


        *//**
         *  Display display = wm.getDefaultDisplay();Point size = new Point();display.getSize(size);
         *  针对这种方式的获取分辨率
         *//*
        XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getSize", Point.class, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                Point mPoint = (Point) param.args[0];
                mPoint.y = SharedPref.getintXValue("height");
                mPoint.x = SharedPref.getintXValue("width");
                XposedBridge.log("getSize");
            }
        });*/

        hookDisplay(loadPkgParam);
        hookRealMetrics(loadPkgParam);
        hookMetrics(loadPkgParam);
        hookGetWidth(loadPkgParam);
        hookGetHeight(loadPkgParam);
        getNetworkType(loadPkgParam);
    }

    public static void hookRealMetrics(XC_LoadPackage.LoadPackageParam param) {
        XposedHelpers.findAndHookMethod("android.view.Display", param.classLoader, "getRealMetrics", DisplayMetrics.class, new XC_MethodHook(XCallback.PRIORITY_LOWEST) {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try {
                    String display = (String) PoseHelper008.valueMap.get("getMetrics");
                    if (display != null) {
                        String[] size = display.split("x");
                        if (size.length == 2) {
                            DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                            metrics.widthPixels = Integer.parseInt(size[0]);
                            metrics.heightPixels = Integer.parseInt(size[1]);
                        }
                    }
                    XposedBridge.log("getRealMetrics_dpi");
                } catch (Exception e) {
                    XposedBridge.log("getRealMetrics_dpi:Error");
                }
            }

        });
    }

    public static void hookDisplay(XC_LoadPackage.LoadPackageParam param) {
        XposedHelpers.findAndHookMethod("android.view.Display", param.classLoader, "getSize", Point.class, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try {
                    String display = (String) PoseHelper008.valueMap.get("getMetrics");
                    if (display != null) {
                        String[] size = display.split("x");
                        if (size.length == 2) {
                            Point mPoint = (Point) param.args[0];
                            mPoint.x = Integer.parseInt(size[0]);
                            mPoint.y = Integer.parseInt(size[1]);
                        }
                    }
                    XposedBridge.log("getSize");
                } catch (Exception e) {
                    XposedBridge.log("getSize:Error");
                }
            }
        });
    }

    public static void hookMetrics(XC_LoadPackage.LoadPackageParam param) {
        XposedHelpers.findAndHookMethod("android.view.Display", param.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try {
                    String display = (String) PoseHelper008.valueMap.get("getMetrics");
                    if (display != null) {
                        String[] size = display.split("x");
                        if (size.length == 2) {
                            DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                            metrics.widthPixels = Integer.parseInt(size[0]);
                            metrics.heightPixels = Integer.parseInt(size[1]);
                        }
                    }
                    XposedBridge.log("getMetrics");
                } catch (Exception e) {
                    XposedBridge.log("getMetrics:Error");
                }
            }

        });
    }

    public static void hookGetWidth(XC_LoadPackage.LoadPackageParam param) {
        XposedHelpers.findAndHookMethod("android.view.Display", param.classLoader, "getWidth", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try {
                    String display = (String) PoseHelper008.valueMap.get("getMetrics");
                    if (display != null) {
                        String[] size = display.split("x");
                        if (size.length == 2) {
                            param.setResult(Integer.parseInt(size[0]));
                        }
                    }
                    XposedBridge.log("getWidth");
                } catch (Exception e) {
                    XposedBridge.log("getWidth:Error");
                }
            }
        });
    }

    public static void hookGetHeight(XC_LoadPackage.LoadPackageParam param) {
        XposedHelpers.findAndHookMethod("android.view.Display", param.classLoader, "getHeight", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try {
                    String display = (String) PoseHelper008.valueMap.get("getMetrics");
                    if (display != null) {
                        String[] size = display.split("x");
                        if (size.length == 2) {
                            param.setResult(Integer.parseInt(size[1]));
                        }
                    }
                    XposedBridge.log("getHeight");
                } catch (Exception e) {
                    XposedBridge.log("getHeight:Error");
                }
            }
        });
    }

    public static void getNetworkType(XC_LoadPackage.LoadPackageParam param) {
        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", param.classLoader, "getType", new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try {
                    String mode = (String) PoseHelper008.valueMap.get("connect_mode");//1.wifi连接 2.数据流量
                    if ("1".equals(mode)) {
                        param.setResult(1);//SimulateDataTemp.TYPE_WIFI
                    } else {
                        param.setResult(0);//SimulateDataTemp.TYPE_MOBILE
                    }
                    XposedBridge.log("NetworkInfo_getType:" + mode);
                } catch (Exception e) {
                    XposedBridge.log("NetworkInfo_getType:Error");
                }
            }
        });

        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", param.classLoader, "getTypeName", new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try {
                    String mode = (String) PoseHelper008.valueMap.get("connect_mode");//1.wifi连接 2.数据流量
                    if ("1".equals(mode)) {//WIFI
                        param.setResult("WIFI");
                    } else {
                        param.setResult("MOBILE");
                    }
                    XposedBridge.log("NetworkInfo_getTypeName:" + mode);
                } catch (Exception e) {
                    XposedBridge.log("NetworkInfo_getTypeName:Error");
                }
            }
        });

        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", param.classLoader, "getNetworkType", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedBridge.log("getNetworkType");
                try {
                    String mode = (String) PoseHelper008.valueMap.get("connect_mode");
                    if ("2".equals(mode)) {//手机流量
                        ArrayList<Integer> netType = new ArrayList<>();
                        netType.add(1);
                        netType.add(15);
                        netType.add(8);
                        netType.add(9);
                        netType.add(16);
                        netType.add(13);
                        int index = new Random().nextInt(netType.size());
                        param.setResult(netType.get(index));
                        XposedBridge.log("getNetworkType:" + netType.get(index));
                    }
                } catch (Exception e) {
                    XposedBridge.log("getNetworkType:Error");
                }
            }
        });

        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", param.classLoader, "getSubtype", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedBridge.log("getSubtype");

                ArrayList<Integer> types = new ArrayList<>();
                types.add(TelephonyManager.NETWORK_TYPE_GPRS);
                types.add(TelephonyManager.NETWORK_TYPE_CDMA);
                types.add(TelephonyManager.NETWORK_TYPE_EDGE);
                types.add(TelephonyManager.NETWORK_TYPE_1xRTT);
                types.add(TelephonyManager.NETWORK_TYPE_IDEN);
                types.add(TelephonyManager.NETWORK_TYPE_EVDO_A);
                types.add(TelephonyManager.NETWORK_TYPE_UMTS);
                types.add(TelephonyManager.NETWORK_TYPE_EVDO_0);
                types.add(TelephonyManager.NETWORK_TYPE_HSDPA);
                types.add(TelephonyManager.NETWORK_TYPE_HSUPA);
                types.add(TelephonyManager.NETWORK_TYPE_HSPA);
                types.add(TelephonyManager.NETWORK_TYPE_EVDO_B);
                types.add(TelephonyManager.NETWORK_TYPE_EHRPD);
                types.add(TelephonyManager.NETWORK_TYPE_HSPAP);
                types.add(TelephonyManager.NETWORK_TYPE_LTE);
                int index = new Random().nextInt(types.size());
                param.setResult(types.get(index));
            }

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }
        });
    }
}


