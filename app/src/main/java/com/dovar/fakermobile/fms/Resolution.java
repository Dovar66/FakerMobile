package com.dovar.fakermobile.fms;

import android.graphics.Point;
import android.util.DisplayMetrics;

import com.dovar.fakermobile.util.DataUtil;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XCallback;

/**
 * 屏幕相关
 */
public class Resolution {

    public static void hook(XC_LoadPackage.LoadPackageParam loadPkgParam) {
        //屏幕分辨率相关
        hookDisplay(loadPkgParam);
        hookRealMetrics(loadPkgParam);
        hookMetrics(loadPkgParam);
        hookGetWidth(loadPkgParam);
        hookGetHeight(loadPkgParam);
    }

    /**
     * DisplayMetrics dm = new DisplayMetrics(); display.getRealMetrics(dm); height = dm.heightPixels;
     * 针对这种方式的获取分辨率
     */
    private static void hookRealMetrics(XC_LoadPackage.LoadPackageParam param) {
        XposedHelpers.findAndHookMethod("android.view.Display", param.classLoader, "getRealMetrics", DisplayMetrics.class, new XC_MethodHook(XCallback.PRIORITY_LOWEST) {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try {
                    String display = DataUtil.getData().getMetrics();
                    if (display != null) {
                        String[] size = display.split("\\*");
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
    private static void hookDisplay(XC_LoadPackage.LoadPackageParam param) {
        XposedHelpers.findAndHookMethod("android.view.Display", param.classLoader, "getSize", Point.class, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try {
                    String display = DataUtil.getData().getMetrics();
                    if (display != null) {
                        String[] size = display.split("\\*");
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
    private static void hookMetrics(XC_LoadPackage.LoadPackageParam param) {
        XposedHelpers.findAndHookMethod("android.view.Display", param.classLoader, "getMetrics", DisplayMetrics.class, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try {
                    String display = DataUtil.getData().getMetrics();
                    if (display != null) {
                        String[] size = display.split("\\*");
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
    private static void hookGetWidth(XC_LoadPackage.LoadPackageParam param) {
        XposedHelpers.findAndHookMethod("android.view.Display", param.classLoader, "getWidth", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try {
                    String display = DataUtil.getData().getMetrics();
                    if (display != null) {
                        String[] size = display.split("\\*");
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

    private static void hookGetHeight(XC_LoadPackage.LoadPackageParam param) {
        XposedHelpers.findAndHookMethod("android.view.Display", param.classLoader, "getHeight", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try {
                    String display = DataUtil.getData().getMetrics();
                    if (display != null) {
                        String[] size = display.split("\\*");
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

