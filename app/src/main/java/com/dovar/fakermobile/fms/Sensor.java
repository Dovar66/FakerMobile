package com.dovar.fakermobile.fms;

import java.util.Random;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 传感器相关
 */
public class Sensor {
    private static final int DURATION_INTERVAL = 10 * 1000;//限制模拟摇一摇最小时间间隔，即连续两次摇一摇的最小时间间隔
    private static long lastUpdateTime; // 上次检测时间

    /**
     * hook传感器
     * 模拟摇一摇或用于修改步数
     */
    public static void hook(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        final Class<?> sensorEL = XposedHelpers.findClass("android.hardware.SystemSensorManager$SensorEventQueue", loadPackageParam.classLoader);
        XposedBridge.hookAllMethods(sensorEL, "dispatchSensorEvent", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                ((float[]) param.args[1])[0] = (125.0F + 1200.0F * new Random().nextFloat());
                XposedBridge.log("dispatchSensorEvent");

                long currentUpdateTime = System.currentTimeMillis();
                long timeInterval = currentUpdateTime - lastUpdateTime;
                if (timeInterval < DURATION_INTERVAL) {
                    //小于最小时间间隔，return
                    super.beforeHookedMethod(param);
                    return;
                }
                ((float[]) param.args[1])[0] = (10.0F + 1000.0F * new Random().nextFloat());
                ((float[]) param.args[1])[1] = (10.0F + 1000.0F * new Random().nextFloat());
                ((float[]) param.args[1])[2] = (10.0F + 1000.0F * new Random().nextFloat());
                //更新lastUpdateTime
                lastUpdateTime = currentUpdateTime;
                super.beforeHookedMethod(param);
            }
        });
    }
}
