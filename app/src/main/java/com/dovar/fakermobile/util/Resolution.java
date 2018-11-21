package com.dovar.fakermobile.util;

import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

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
        //屏幕分辨率相关
        hookDisplay(loadPkgParam);
        hookRealMetrics(loadPkgParam);
        hookMetrics(loadPkgParam);
        hookGetWidth(loadPkgParam);
        hookGetHeight(loadPkgParam);

        //网络状态
        getNetworkType(loadPkgParam);
    }

    /**
     * DisplayMetrics dm = new DisplayMetrics(); display.getRealMetrics(dm); height = dm.heightPixels;
     * 针对这种方式的获取分辨率
     */
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

    /**
     * Display display = wm.getDefaultDisplay();Point size = new Point();display.getSize(size);
     * 针对这种方式的获取分辨率
     */
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

    /**
     * DisplayMetrics dm = new DisplayMetrics(); display.getMetrics(dm); height = dm.heightPixels;
     * 针对这种方式的获取分辨率
     */
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

    /**
     * Display.getWidth()  Display.getHeight()
     * 针对这种方式的获取分辨率
     */
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

//    public static void getNetworkType(XC_LoadPackage.LoadPackageParam param) {
//        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", param.classLoader, "getType", new XC_MethodHook() {
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                try {
//                    String mode = (String) PoseHelper008.valueMap.get("connect_mode");//1.wifi连接 2.数据流量
//                    if ("1".equals(mode)) {
//                        param.setResult(1);//SimulateDataTemp.TYPE_WIFI
//                    } else if ("2".equals(mode)) {
//                        param.setResult(0);//SimulateDataTemp.TYPE_MOBILE
//                    }
//                    XposedBridge.log("NetworkInfo_getType:" + mode);
//                } catch (Exception e) {
//                    XposedBridge.log("NetworkInfo_getType:Error");
//                }
//                super.afterHookedMethod(param);
//            }
//        });
//
//        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", param.classLoader, "getTypeName", new XC_MethodHook() {
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                try {
//                    String mode = (String) PoseHelper008.valueMap.get("connect_mode");//1.wifi连接 2.数据流量
//                    if ("1".equals(mode)) {//WIFI
//                        param.setResult("WIFI");
//                    } else if ("2".equals(mode)) {
//                        param.setResult("MOBILE");
//                    }
//                    XposedBridge.log("NetworkInfo_getTypeName:" + mode);
//                } catch (Exception e) {
//                    XposedBridge.log("NetworkInfo_getTypeName:Error");
//                }
//                super.afterHookedMethod(param);
//            }
//        });
//
////        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", param.classLoader, "getNetworkType", new XC_MethodHook() {
////
////            @Override
////            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
////                super.afterHookedMethod(param);
////                XposedBridge.log("getNetworkType");
////                try {
////                    String mode = (String) PoseHelper008.valueMap.get("connect_mode");
////                    if ("2".equals(mode)) {//手机流量
////                        /*ArrayList<Integer> netType = new ArrayList<>();
////                        netType.add(1);
////                        netType.add(15);
////                        netType.add(8);
////                        netType.add(9);
////                        netType.add(16);
////                        netType.add(13);
////                        int index = new Random().nextInt(netType.size());*/
////                        param.setResult(1);
////                    }
////                } catch (Exception e) {
////                    XposedBridge.log("getNetworkType:Error");
////                }
////            }
////        });
//
//        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", param.classLoader, "getSubtype", new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                XposedBridge.log("getSubtype");
////                ArrayList<Integer> types = new ArrayList<>();
////                types.add(TelephonyManager.NETWORK_TYPE_GPRS);
////                types.add(TelephonyManager.NETWORK_TYPE_CDMA);
////                types.add(TelephonyManager.NETWORK_TYPE_EDGE);
////                types.add(TelephonyManager.NETWORK_TYPE_1xRTT);
////                types.add(TelephonyManager.NETWORK_TYPE_IDEN);
////                types.add(TelephonyManager.NETWORK_TYPE_EVDO_A);
////                types.add(TelephonyManager.NETWORK_TYPE_UMTS);
////                types.add(TelephonyManager.NETWORK_TYPE_EVDO_0);
////                types.add(TelephonyManager.NETWORK_TYPE_HSDPA);
////                types.add(TelephonyManager.NETWORK_TYPE_HSUPA);
////                types.add(TelephonyManager.NETWORK_TYPE_HSPA);
////                types.add(TelephonyManager.NETWORK_TYPE_EVDO_B);
////                types.add(TelephonyManager.NETWORK_TYPE_EHRPD);
////                types.add(TelephonyManager.NETWORK_TYPE_HSPAP);
////                types.add(TelephonyManager.NETWORK_TYPE_LTE);
////                int index = new Random().nextInt(types.size());
//                String mode = (String) PoseHelper008.valueMap.get("connect_mode");
//                if ("2".equals(mode)) {//手机流量
//                    param.setResult(TelephonyManager.NETWORK_TYPE_GPRS);
//                }
//                super.afterHookedMethod(param);
//            }
//        });
//
//        XposedHelpers.findAndHookMethod(NetworkInfo.class.getName(), param.classLoader, "getSubtypeName", new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                XposedBridge.log("getSubtypeName");
//                String mode = (String) PoseHelper008.valueMap.get("connect_mode");
//                if ("2".equals(mode)) {//手机流量
//                    param.setResult("GPRS");
//                }
//                super.afterHookedMethod(param);
//            }
//        });
//
//        XposedHelpers.findAndHookMethod(ConnectivityManager.class.getName(), param.classLoader, "getNetworkInfo", Integer.TYPE.getName(), new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                XposedBridge.log("getNetworkInfo_after");
//                if (param.getResult() != null) {
//                    XposedBridge.log("getNetworkInfo_after" + JSON.toJSONString(param.getResult()));
//                }
//                String mode = (String) PoseHelper008.valueMap.get("connect_mode");//1.wifi连接 2.数据流量
//                int networkType = (int) param.args[0];
//                if ("1".equals(mode) && networkType == 1) {
//
//                } else if ("2".equals(mode) && networkType == 0) {
//
//                } else {
//                    param.setResult(null);
//                }
//                super.afterHookedMethod(param);
//            }
//        });
//
////        XposedHelpers.findAndHookMethod(ConnectivityManager.class.getName(), param.classLoader, "getActiveNetworkInfo", new XC_MethodHook() {
////
////            @Override
////            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
////                XposedBridge.log("getActiveNetworkInfo");
////                super.afterHookedMethod(param);
////            }
////        });
//
//        XposedHelpers.findAndHookMethod(NetworkInfo.class.getName(), param.classLoader, "isConnected", new XC_MethodHook() {
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                XposedBridge.log("isConnected");
//                param.setResult(true);
//                super.afterHookedMethod(param);
//            }
//        });
//
//        XposedHelpers.findAndHookMethod(NetworkInfo.class.getName(), param.classLoader, "isAvailable", new XC_MethodHook() {
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                XposedBridge.log("isAvailable");
//                param.setResult(true);
//                super.afterHookedMethod(param);
//            }
//        });
//
//        XposedHelpers.findAndHookMethod(NetworkInfo.class.getName(), param.classLoader, "getState", new XC_MethodHook() {
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                XposedBridge.log("getState");
//                param.setResult(NetworkInfo.State.CONNECTED);
//                super.afterHookedMethod(param);
//            }
//        });
//    }

    //设置网络状态为WIFI，实测有效
    public static void getNetworkType(XC_LoadPackage.LoadPackageParam param) {
        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", param.classLoader, "getType", new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(1);
                XposedBridge.log("NetworkInfo_getType:");
                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", param.classLoader, "getTypeName", new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult("WIFI");
                XposedBridge.log("NetworkInfo_getTypeName:");
                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod(ConnectivityManager.class.getName(), param.classLoader, "getNetworkInfo", Integer.TYPE.getName(), new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("getNetworkInfo_after");
                int networkType = (int) param.args[0];
                if (networkType != 1) {
                    param.setResult(null);
                }
                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod(NetworkInfo.class.getName(), param.classLoader, "isConnected", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("isConnected");
                param.setResult(true);
                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod(NetworkInfo.class.getName(), param.classLoader, "isAvailable", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("isAvailable");
                param.setResult(true);
                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod(NetworkInfo.class.getName(), param.classLoader, "getState", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("getState");
                param.setResult(NetworkInfo.State.CONNECTED);
                super.afterHookedMethod(param);
            }
        });
    }

    // TODO: 2018/11/21  友盟获取分辨率的方法
   /* //分辨率
    public static int[] r(Context var0) {
        try {
            DisplayMetrics var1 = new DisplayMetrics();
            WindowManager var2 = (WindowManager)((WindowManager)var0.getSystemService("window"));
            var2.getDefaultDisplay().getMetrics(var1);
            int var3 = -1;
            int var4 = -1;
            if ((var0.getApplicationInfo().flags & 8192) == 0) {
                var3 = a((Object)var1, "noncompatWidthPixels");
                var4 = a((Object)var1, "noncompatHeightPixels");
            }

            if (var3 == -1 || var4 == -1) {
                var3 = var1.widthPixels;
                var4 = var1.heightPixels;
            }

            int[] var5 = new int[2];
            if (var3 > var4) {
                var5[0] = var4;
                var5[1] = var3;
            } else {
                var5[0] = var3;
                var5[1] = var4;
            }

            return var5;
        } catch (Throwable var6) {
            return null;
        }
    }

    private static int a(Object var0, String var1) {
        try {
            Field var2 = DisplayMetrics.class.getDeclaredField(var1);
            var2.setAccessible(true);
            return var2.getInt(var0);
        } catch (Throwable var3) {
            return -1;
        }
    }*/
}

