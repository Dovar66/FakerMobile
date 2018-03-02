package com.dovar.fakermobile.util;

import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

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
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook(XCallback.PRIORITY_LOWEST) {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    final int dpi = tryParseInt(SharedPref.getXValue("DPI"));
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.densityDpi = dpi;
                    XposedBridge.log("getMetrics_dpi");
                }

            });
        } catch (Exception e) {
            XposedBridge.log("Fake DPI ERROR: " + e.getMessage());
        }

        try {
            /**
             *  Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);method.invoke(display, dm);dpi = dm.heightPixels;
             *  针对这种方式的获取分辨率
             */
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getRealMetrics", DisplayMetrics.class, new XC_MethodHook(XCallback.PRIORITY_LOWEST) {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    final int dpi = SharedPref.getintXValue("DPI");
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.densityDpi = dpi;
                    metrics.widthPixels = SharedPref.getintXValue("width");
                    metrics.heightPixels = SharedPref.getintXValue("height");
                    XposedBridge.log("getRealMetrics_dpi");
                }

            });
        } catch (Exception e) {

        }


        try {
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param)
                        throws Throwable {
                    super.afterHookedMethod(param);
                    final float sdensity = SharedPref.getfloatXValue("density");
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.density = sdensity;
                    XposedBridge.log("getMetrics_density");
                }

            });
        } catch (Exception e) {

        }


        try {
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param)
                        throws Throwable {
                    super.afterHookedMethod(param);
                    final float sxdpi = SharedPref.getfloatXValue("xdpi");
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.xdpi = sxdpi;

                    XposedBridge.log("getMetrics_xdpi");
                }

            });
        } catch (Exception e) {
            XposedBridge.log("Fake Real DPI ERROR: " + e.getMessage());
        }


        try {
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param)
                        throws Throwable {
                    super.afterHookedMethod(param);
                    final float sydpi = SharedPref.getfloatXValue("ydpi");
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.ydpi = sydpi;
                    XposedBridge.log("getMetrics_ydpi");
                }

            });
        } catch (Exception e) {

        }


        try {
            XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    final float scdensity = SharedPref.getfloatXValue("scaledDensity");
                    DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                    metrics.scaledDensity = scdensity;
                    XposedBridge.log("getMetrics_scaledDensity");
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

        // 宽
        XposedHelpers.findAndHookMethod("android.view.Display", loadPkgParam.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook(XCallback.PRIORITY_LOWEST) {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                final int zhenwidth = SharedPref.getintXValue("width");
                DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                metrics.widthPixels = zhenwidth;
                XposedBridge.log("getMetrics_width");

//                try {
//                    Field mField=XposedHelpers.findField(Point.class, "x");
//                    mField.setAccessible(true);
//                    mField.set(new Point(),SharedPref.getintXValue("width"));
//                    XposedHelpers.findField(Point.class, "x").setInt(new Point(), SharedPref.getintXValue("width"));//非静态变量只能修改指定对象的属性
//                    XposedHelpers.findField(Point.class, "y").setInt(new Point(), SharedPref.getintXValue("height"));
//                } catch (IllegalAccessException mE) {
//                    mE.printStackTrace();
//                }
            }
        });
        // 高
        XposedHelpers.findAndHookMethod(Display.class, "getMetrics", DisplayMetrics.class, new XC_MethodHook(XCallback.PRIORITY_LOWEST) {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                final int zhenheight = SharedPref.getintXValue("height");
                DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                metrics.heightPixels = zhenheight;
                XposedBridge.log("getMetrics_height");
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


