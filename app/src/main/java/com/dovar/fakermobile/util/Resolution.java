package com.dovar.fakermobile.util;

import android.graphics.Point;
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

        try {
            /**
             *  DisplayMetrics dm = new DisplayMetrics(); display.getRealMetrics(dm); height = dm.heightPixels;
             *  针对这种方式的获取分辨率
             */
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
            /**
             * DisplayMetrics dm = new DisplayMetrics(); display.getMetrics(dm); height = dm.heightPixels;
             * 针对这种方式的获取分辨率
             */
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

        /**
         * Display.getWidth()  Display.getHeight()
         * 针对这种方式的获取分辨率
         */
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


        /**
         *  Display display = wm.getDefaultDisplay();Point size = new Point();display.getSize(size);
         *  针对这种方式的获取分辨率
         */
        XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getSize", Point.class, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                Point mPoint = (Point) param.args[0];
                mPoint.y = SharedPref.getintXValue("height");
                mPoint.x = SharedPref.getintXValue("width");
                XposedBridge.log("getSize");
            }
        });
    }


    private static int tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 320;
        }
    }

    private static float tryParsefloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return (float) 480.0;
        }
    }

}


