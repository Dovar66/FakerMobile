package com.dovar.fakermobile;

import com.dovar.fakermobile.util.Hook;
import com.dovar.fakermobile.util.Phone;
import com.dovar.fakermobile.util.Resolution;
import com.dovar.fakermobile.util.XBuild;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by heweizong on 2018/2/27.
 */

public class XposedUtil implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam mLoadPackageParam) throws Throwable {
//        XposedBridge.log("=========Loaded app: " + mLoadPackageParam.packageName);

//        if (mLoadPackageParam.packageName.equals("com.dovar.testxp")) {
            new Hook().HookTest(mLoadPackageParam);//使更新模块后不用重启手机就能生效
            new XBuild(mLoadPackageParam);
            new Phone(mLoadPackageParam);
            new Resolution().Display(mLoadPackageParam);  //屏幕
//        }


//        eg:
//        new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);//hook方法执行之前修改传入参数
//                param.args[0] =;
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);//hook方法执行之后修改返回结果
//                param.setResult();
//            }
//        };


/*        addHook(mLoadPackageParam.packageName, WifiInfo.class.getName(), mLoadPackageParam.classLoader, "getMacAddress", new Object[0]);
        addHook(mLoadPackageParam.packageName, WifiInfo.class.getName(), mLoadPackageParam.classLoader, "getSSID", new Object[0]);
        addHook(mLoadPackageParam.packageName, WifiInfo.class.getName(), mLoadPackageParam.classLoader, "getBSSID", new Object[0]);
        addHook(mLoadPackageParam.packageName, TelephonyManager.class.getName(), mLoadPackageParam.classLoader, "getSimSerialNumber", new Object[0]);
        addHook(mLoadPackageParam.packageName, TelephonyManager.class.getName(), mLoadPackageParam.classLoader, "getSubscriberId", new Object[0]);
        addHook(mLoadPackageParam.packageName, TelephonyManager.class.getName(), mLoadPackageParam.classLoader, "getLine1Number", new Object[0]);
        addHook(mLoadPackageParam.packageName, TelephonyManager.class.getName(), mLoadPackageParam.classLoader, "getSimCountryIso", new Object[0]);
        addHook(mLoadPackageParam.packageName, TelephonyManager.class.getName(), mLoadPackageParam.classLoader, "getSimOperator", new Object[0]);
        addHook(mLoadPackageParam.packageName, TelephonyManager.class.getName(), mLoadPackageParam.classLoader, "getSimOperatorName", new Object[0]);
        addHook(mLoadPackageParam.packageName, TelephonyManager.class.getName(), mLoadPackageParam.classLoader, "getNetworkCountryIso", new Object[0]);
        addHook(mLoadPackageParam.packageName, TelephonyManager.class.getName(), mLoadPackageParam.classLoader, "getNetworkOperator", new Object[0]);
        addHook(mLoadPackageParam.packageName, TelephonyManager.class.getName(), mLoadPackageParam.classLoader, "getNetworkOperatorName", new Object[0]);
        addHook(mLoadPackageParam.packageName, TelephonyManager.class.getName(), mLoadPackageParam.classLoader, "getNetworkType", new Object[0]);
        addHook(mLoadPackageParam.packageName, TelephonyManager.class.getName(), mLoadPackageParam.classLoader, "getPhoneType", new Object[0]);
        addHook(mLoadPackageParam.packageName, TelephonyManager.class.getName(), mLoadPackageParam.classLoader, "hasIccCard", new Object[0]);
        addHook(mLoadPackageParam.packageName, Settings.Secure.class.getName(), mLoadPackageParam.classLoader, "getString", new Object[]{ContentResolver.class.getName(), String.class.getName()});
        addHook(mLoadPackageParam.packageName, TelephonyManager.class.getName(), mLoadPackageParam.classLoader, "getSimState", new Object[0]);
        addHook(mLoadPackageParam.packageName, Build.class.getName(), mLoadPackageParam.classLoader, "getRadioVersion", new Object[0]);
        addHook(mLoadPackageParam.packageName, "android.app.ApplicationPackageManager", mLoadPackageParam.classLoader, "getInstalledPackages", new Object[]{Integer.TYPE.getName()});
        addHook(mLoadPackageParam.packageName, ActivityManager.class.getName(), mLoadPackageParam.classLoader, "getRunningAppProcesses", new Object[0]);
        addHook(mLoadPackageParam.packageName, "android.app.ApplicationPackageManager", mLoadPackageParam.classLoader, "getInstalledApplications", new Object[]{Integer.TYPE.getName()});
        addHook(mLoadPackageParam.packageName, ActivityManager.class.getName(), mLoadPackageParam.classLoader, "getRecentTasks", new Object[]{Integer.TYPE.getName(), Integer.TYPE.getName()});
        addHook(mLoadPackageParam.packageName, "android.os.SystemProperties", mLoadPackageParam.classLoader, "get", new Object[]{String.class, String.class});
        addHook(mLoadPackageParam.packageName, "android.os.SystemProperties", mLoadPackageParam.classLoader, "get", new Object[]{String.class});
        addHook(mLoadPackageParam.packageName, GsmCellLocation.class.getName(), mLoadPackageParam.classLoader, "getLac", new Object[0]);
        addHook(mLoadPackageParam.packageName, GsmCellLocation.class.getName(), mLoadPackageParam.classLoader, "getCid", new Object[0]);
        addHook(mLoadPackageParam.packageName, CdmaCellLocation.class.getName(), mLoadPackageParam.classLoader, "getNetworkId", new Object[0]);
        addHook(mLoadPackageParam.packageName, CdmaCellLocation.class.getName(), mLoadPackageParam.classLoader, "getBaseStationId", new Object[0]);
        addHook(mLoadPackageParam.packageName, NeighboringCellInfo.class.getName(), mLoadPackageParam.classLoader, "getLac", new Object[0]);
        addHook(mLoadPackageParam.packageName, NeighboringCellInfo.class.getName(), mLoadPackageParam.classLoader, "getCid", new Object[0]);
        addHook(mLoadPackageParam.packageName, Settings.System.class.getName(), mLoadPackageParam.classLoader, "putFloat", new Object[]{ContentResolver.class.getName(), String.class.getName(), Float.TYPE.getName()});
        addHook(mLoadPackageParam.packageName, Settings.System.class.getName(), mLoadPackageParam.classLoader, "putInt", new Object[]{ContentResolver.class.getName(), String.class.getName(), Integer.TYPE.getName()});
        addHook(mLoadPackageParam.packageName, Settings.System.class.getName(), mLoadPackageParam.classLoader, "putLong", new Object[]{ContentResolver.class.getName(), String.class.getName(), Long.TYPE.getName()});
        addHook(mLoadPackageParam.packageName, Settings.System.class.getName(), mLoadPackageParam.classLoader, "putString", new Object[]{ContentResolver.class.getName(), String.class.getName(), String.class.getName()});
        addHook(mLoadPackageParam.packageName, Settings.System.class.getName(), mLoadPackageParam.classLoader, "getFloat", new Object[]{ContentResolver.class.getName(), String.class.getName()});
        addHook(mLoadPackageParam.packageName, Settings.System.class.getName(), mLoadPackageParam.classLoader, "getInt", new Object[]{ContentResolver.class.getName(), String.class.getName()});
        addHook(mLoadPackageParam.packageName, Settings.System.class.getName(), mLoadPackageParam.classLoader, "getLong", new Object[]{ContentResolver.class.getName(), String.class.getName()});
        addHook(mLoadPackageParam.packageName, Settings.System.class.getName(), mLoadPackageParam.classLoader, "getString", new Object[]{ContentResolver.class.getName(), String.class.getName()});
        addHook(mLoadPackageParam.packageName, Settings.System.class.getName(), mLoadPackageParam.classLoader, "getFloat", new Object[]{ContentResolver.class.getName(), String.class.getName(), Float.TYPE.getName()});
        addHook(mLoadPackageParam.packageName, Settings.System.class.getName(), mLoadPackageParam.classLoader, "getInt", new Object[]{ContentResolver.class.getName(), String.class.getName(), Integer.TYPE.getName()});
        addHook(mLoadPackageParam.packageName, Settings.System.class.getName(), mLoadPackageParam.classLoader, "getLong", new Object[]{ContentResolver.class.getName(), String.class.getName(), Long.TYPE.getName()});
        addHook(mLoadPackageParam.packageName, Display.class.getName(), mLoadPackageParam.classLoader, "getMetrics", new Object[]{DisplayMetrics.class.getName()});
        addHook(mLoadPackageParam.packageName, Display.class.getName(), mLoadPackageParam.classLoader, "getWidth", new Object[0]);
        addHook(mLoadPackageParam.packageName, Display.class.getName(), mLoadPackageParam.classLoader, "getHeight", new Object[0]);
        addHook(mLoadPackageParam.packageName, Location.class.getName(), mLoadPackageParam.classLoader, "getLatitude", new Object[0]);
        addHook(mLoadPackageParam.packageName, Location.class.getName(), mLoadPackageParam.classLoader, "getLongitude", new Object[0]);
        addHook(mLoadPackageParam.packageName, WifiManager.class.getName(), mLoadPackageParam.classLoader, "getScanResults", new Object[0]);
        addHook(mLoadPackageParam.packageName, NetworkInfo.class.getName(), mLoadPackageParam.classLoader, "getTypeName", new Object[0]);
        addHook(mLoadPackageParam.packageName, ConnectivityManager.class.getName(), mLoadPackageParam.classLoader, "getNetworkInfo", new Object[]{Integer.TYPE.getName()});
        addHook(mLoadPackageParam.packageName, WebView.class.getName(), mLoadPackageParam.classLoader, "loadUrl", new Object[]{String.class.getName()});
        addHook(mLoadPackageParam.packageName, WebView.class.getName(), mLoadPackageParam.classLoader, "loadUrl", new Object[]{String.class.getName(), Map.class.getName()});
        addHook(mLoadPackageParam.packageName, Runtime.class.getName(), mLoadPackageParam.classLoader, "exec", new Object[]{String.class.getName()});
        addHookCon(mLoadPackageParam.packageName, FileReader.class.getName(), mLoadPackageParam.classLoader, new Object[]{String.class.getName()});
        addHookCon(mLoadPackageParam.packageName, File.class.getName(), mLoadPackageParam.classLoader, new Object[]{String.class.getName(), String.class.getName()});
        addHookCon(mLoadPackageParam.packageName, File.class.getName(), mLoadPackageParam.classLoader, new Object[]{File.class.getName(), String.class.getName()});
        addHookCon(mLoadPackageParam.packageName, File.class.getName(), mLoadPackageParam.classLoader, new Object[]{String.class.getName()});*/
    }


/*    public void addHook(final String paramString1, final String paramString2, ClassLoader paramClassLoader, final String paramString3, Object... paramVarArgs) {
        paramString1 = new XC_MethodHook() {
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam paramAnonymousMethodHookParam)
                    throws Throwable {
             *//*   if (paramString2.equals("cn.yqzq.zqb.tools.EncryptHelpr")) {
                    onLog("yqzq param0" + paramAnonymousMethodHookParam.args[0]);
                    onLog("yqzq param1" + paramAnonymousMethodHookParam.args[1]);
                }
                Object localObject1;
                Object localObject2;
                Object localObject3;
                label366:
                int i;
                label669:
                label823:
                int j;
                label781:
                label949:
                do {
                    Object localObject4;
                    do {
                        do {
                            do {
                                return;
                                if ("android.app.ApplicationPackageManager".equals(paramString2)) {
                                    localObject1 = JSON.parseObject(PoseHelper008.getFileData("hideProgess"));
                                    if ((localObject1 != null) && (((JSONObject) localObject1).get("ifOpen") != null) && (((JSONObject) localObject1).get("ifOpen").equals("true")) && (((JSONObject) localObject1).get("hide_target") != null)) {
                                        localObject2 = ((JSONObject) localObject1).get("hide_target").split("_");
                                        if (localObject2.length != 2) {
                                            onLog("获取程序列表参数错误");
                                            super.beforeHookedMethod(paramAnonymousMethodHookParam);
                                            return;
                                        }
                                        if (paramString1.equals(localObject2[1])) {
                                            onLog("阻止获取程序列表成功");
                                            if (!paramString3.equals("getInstalledApplications")) {
                                                break label366;
                                            }
                                            localObject2 = new ArrayList();
                                            localObject3 = (ArrayList) paramAnonymousMethodHookParam.getResult();
                                            localObject1 = (JSONObject) ((JSONObject) localObject1).get("appArray");
                                            if (localObject1 != null) {
                                                localObject4 = ((ArrayList) localObject3).iterator();
                                            }
                                        }
                                    }
                                }
                                Object localObject5;
                                for (; ; ) {
                                    if (!((Iterator) localObject4).hasNext()) {
                                        ((ArrayList) localObject3).removeAll((Collection) localObject2);
                                        paramAnonymousMethodHookParam.setResult(localObject3);
                                        if (!ActivityManager.class.getName().equals(paramString2)) {
                                            break label669;
                                        }
                                        localObject1 = JSON.parseObject(PoseHelper008.getFileData("hideProgess"));
                                        if (localObject1 != null) {
                                            break;
                                        }
                                        super.beforeHookedMethod(paramAnonymousMethodHookParam);
                                        return;
                                    }
                                    localObject5 = (ApplicationInfo) ((Iterator) localObject4).next();
                                    if (((JSONObject) localObject1).get(((ApplicationInfo) localObject5).packageName) != null) {
                                        ((ArrayList) localObject2).add(localObject5);
                                    }
                                }
                                localObject2 = new ArrayList();
                                localObject3 = (ArrayList) paramAnonymousMethodHookParam.getResult();
                                localObject1 = (JSONObject) ((JSONObject) localObject1).get("appArray");
                                if (localObject1 != null) {
                                    localObject4 = ((ArrayList) localObject3).iterator();
                                }
                                for (; ; ) {
                                    if (!((Iterator) localObject4).hasNext()) {
                                        ((ArrayList) localObject3).removeAll((Collection) localObject2);
                                        paramAnonymousMethodHookParam.setResult(localObject3);
                                        break;
                                    }
                                    localObject5 = (PackageInfo) ((Iterator) localObject4).next();
                                    if (((JSONObject) localObject1).get(((PackageInfo) localObject5).packageName) != null) {
                                        ((ArrayList) localObject2).add(localObject5);
                                    }
                                }
                                if ((((JSONObject) localObject1).get("ifOpen") != null) && (((JSONObject) localObject1).get("ifOpen").equals("true")) && (((JSONObject) localObject1).get("hide_target") != null)) {
                                    localObject2 = ((JSONObject) localObject1).get("hide_target").split("_");
                                    if (localObject2.length != 2) {
                                        onLog("获取程序列表参数错误");
                                        super.beforeHookedMethod(paramAnonymousMethodHookParam);
                                        return;
                                    }
                                    if (paramString1.equals(localObject2[1])) {
                                        if (!paramString3.equals("getRecentTasks")) {
                                            break label823;
                                        }
                                        onLog("阻止getRecentTasks列表成功");
                                        localObject2 = (ArrayList) paramAnonymousMethodHookParam.getResult();
                                        localObject3 = new ArrayList();
                                        localObject1 = (JSONObject) ((JSONObject) localObject1).get("appArray");
                                        if (localObject1 != null) {
                                            localObject4 = ((ArrayList) localObject2).iterator();
                                            if (((Iterator) localObject4).hasNext()) {
                                                break label781;
                                            }
                                            ((ArrayList) localObject2).removeAll((Collection) localObject3);
                                        }
                                        paramAnonymousMethodHookParam.setResult(localObject2);
                                    }
                                }
                                while (!paramString3.equals("getRunningAppProcesses")) {
                                    for (; ; ) {
                                        if (!Settings.System.class.getName().equals(paramString2)) {
                                            break label1159;
                                        }
                                        if (!paramString1.equals(SetSystemValueActivity.getSystemValuePackageName())) {
                                            break;
                                        }
                                        if (paramString3.indexOf("put") < 0) {
                                            break label949;
                                        }
                                        onLog("设置系统值" + paramAnonymousMethodHookParam.args[1] + "---" + paramAnonymousMethodHookParam.args[2]);
                                        SetSystemValueActivity.addItem(paramAnonymousMethodHookParam.args[1], false, paramAnonymousMethodHookParam.args[2]);
                                        return;
                                        localObject5 = (ActivityManager.RecentTaskInfo) ((Iterator) localObject4).next();
                                        if (((JSONObject) localObject1).get(((ActivityManager.RecentTaskInfo) localObject5).baseIntent.getComponent().getPackageName()) != null) {
                                            ((ArrayList) localObject3).add(localObject5);
                                        }
                                    }
                                }
                                onLog("阻止getRunningAppProcesses列表成功");
                                localObject2 = (ArrayList) paramAnonymousMethodHookParam.getResult();
                                localObject3 = new ArrayList();
                                localObject1 = (JSONObject) ((JSONObject) localObject1).get("appArray");
                                if (localObject1 != null) {
                                    localObject4 = ((ArrayList) localObject2).iterator();
                                }
                                for (; ; ) {
                                    if (!((Iterator) localObject4).hasNext()) {
                                        ((ArrayList) localObject2).removeAll((Collection) localObject3);
                                        paramAnonymousMethodHookParam.setResult(localObject2);
                                        break;
                                    }
                                    localObject5 = (ActivityManager.RunningAppProcessInfo) ((Iterator) localObject4).next();
                                    if (((JSONObject) localObject1).get(((ActivityManager.RunningAppProcessInfo) localObject5).processName) != null) {
                                        ((ArrayList) localObject3).add(localObject5);
                                    }
                                }
                            } while (paramAnonymousMethodHookParam.args[1].equals("android_id"));
                            if (paramAnonymousMethodHookParam.args.length == 2) {
                                onLog("获取系统值" + paramAnonymousMethodHookParam.args[1]);
                                onLog("值" + paramAnonymousMethodHookParam.getResult());
                                SetSystemValueActivity.addItem(paramAnonymousMethodHookParam.args[1], true, paramAnonymousMethodHookParam.getResult());
                                return;
                            }
                        } while (paramAnonymousMethodHookParam.args.length != 3);
                        onLog("获取系统值" + paramAnonymousMethodHookParam.args[1] + "---" + paramAnonymousMethodHookParam.args[2]);
                        onLog("值" + paramAnonymousMethodHookParam.getResult());
                        SetSystemValueActivity.addItem(paramAnonymousMethodHookParam.args[1], true, paramAnonymousMethodHookParam.getResult());
                        return;
                        if ((!paramString2.equals(Display.class.getName())) || (PoseHelper008.valueMap == null) || (!SettingActivity.getValue("setDisplay").equals("true"))) {
                            break;
                        }
                        paramAnonymousMethodHookParam.getResult();
                        localObject1 = (String) PoseHelper008.valueMap.get("getMetrics");
                        localObject2 = ((String) localObject1).split("x");
                        if (!paramString3.equals("getMetrics")) {
                            break label1576;
                        }
                        localObject3 = (DisplayMetrics) paramAnonymousMethodHookParam.args[0];
                        localObject4 = PoseHelper008.getFileData("sourceDisplay").split("_");
                    } while (localObject4.length != 2);
                    i = Integer.parseInt(localObject4[0]);
                    j = Integer.parseInt(localObject4[1]);
                }
                while ((i != ((DisplayMetrics) localObject3).widthPixels) || (j != ((DisplayMetrics) localObject3).heightPixels));
                label1159:
                if (localObject2.length == 2) {
                    onLog("模拟分辨率1" + (String) localObject1);
                    i = Integer.parseInt(localObject2[0]);
                    ((DisplayMetrics) localObject3).heightPixels = Integer.parseInt(localObject2[1]);
                    ((DisplayMetrics) localObject3).widthPixels = i;
                    if (PoseHelper008.valueMap.get("density") != null) {
                        ((DisplayMetrics) localObject3).density = Float.parseFloat(PoseHelper008.valueMap.get("density"));
                    }
                    if (PoseHelper008.valueMap.get("densityDpi") != null) {
                        ((DisplayMetrics) localObject3).densityDpi = Integer.parseInt(PoseHelper008.valueMap.get("densityDpi"));
                    }
                    if (PoseHelper008.valueMap.get("scaledDensity") != null) {
                        ((DisplayMetrics) localObject3).scaledDensity = Float.parseFloat(PoseHelper008.valueMap.get("scaledDensity"));
                    }
                    if (PoseHelper008.valueMap.get("xdpi") != null) {
                        ((DisplayMetrics) localObject3).xdpi = Float.parseFloat(PoseHelper008.valueMap.get("xdpi"));
                    }
                    if (PoseHelper008.valueMap.get("ydpi") != null) {
                        ((DisplayMetrics) localObject3).ydpi = Float.parseFloat(PoseHelper008.valueMap.get("ydpi"));
                    }
                    paramAnonymousMethodHookParam.setResult(localObject3);
                }
                for (; ; ) {
                    super.afterHookedMethod(paramAnonymousMethodHookParam);
                    return;
                    label1576:
                    if (paramString3.equals("getWidth")) {
                        if (localObject2.length == 2) {
                            onLog("模拟分辨率getWidth" + localObject2[0]);
                            paramAnonymousMethodHookParam.setResult(Integer.valueOf(Integer.parseInt(localObject2[0])));
                        }
                    } else if ((paramString3.equals("getHeight")) && (localObject2.length == 2)) {
                        onLog("模拟分辨率getHeight" + localObject2[1]);
                        paramAnonymousMethodHookParam.setResult(Integer.valueOf(Integer.parseInt(localObject2[1])));
                    }
                }*//*
            }

            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam paramAnonymousMethodHookParam)
                    throws Throwable {
                if (Settings.System.class.getName().equals(paramString2)) {
                    if ((paramAnonymousMethodHookParam.args.length >= 2) && (paramString3.equals("getString")) && (paramAnonymousMethodHookParam.args[1].equals("android_id")) && (PoseHelper008.valueMap != null) && (PoseHelper008.valueMap.get(paramString3) != null)) {
                        paramAnonymousMethodHookParam.setResult(PoseHelper008.valueMap.get(paramString3));
                        onLog("settings.system.  android" + PoseHelper008.valueMap.get(paramString3));
                    }
                }
                label570:
                label597:
                label632:
                do {
                    do {
                        do {
                            do {
                                do {
                                    do {
                                        return;
                                        Object localObject2;
                                        try {
                                            if (!Environment.getExternalStorageState().equals("mounted")) {
                                                onLog("存储卡还未挂载");
                                                super.beforeHookedMethod(paramAnonymousMethodHookParam);
                                                return;
                                            }
                                        } catch (Exception localException) {
                                            localObject2 = new StringWriter();
                                            localException.printStackTrace(new PrintWriter((Writer) localObject2));
                                            onLog(localObject2.toString());
                                            super.beforeHookedMethod(paramAnonymousMethodHookParam);
                                            return;
                                        }
                                        if (!"android.os.SystemProperties".equals(paramString2)) {
                                            if (!Tutorial008.check.isCanUse()) {
                                                super.beforeHookedMethod(paramAnonymousMethodHookParam);
                                            }
                                        } else if (paramAnonymousMethodHookParam.args.length >= 1) {
                                            if (paramAnonymousMethodHookParam.args[0].equals("gsm.version.baseband")) {
                                                if (PoseHelper008.valueMap.get("getRadioVersion") != null) {
                                                    onLog("gsm.version.baseband " + PoseHelper008.valueMap.get("getRadioVersion"));
                                                    paramAnonymousMethodHookParam.setResult(PoseHelper008.valueMap.get("getRadioVersion"));
                                                }
                                            } else {
                                                if (!paramAnonymousMethodHookParam.args[0].equals("gsm.sim.state")) {
                                                    break label570;
                                                }
                                                if (SettingActivity.getValue("setCard").trim().equals("true")) {
                                                    paramAnonymousMethodHookParam.setResult("READY");
                                                }
                                            }
                                        }
                                        if ((paramString3.equals("hasIccCard")) && (SettingActivity.getValue("setCard").trim().equals("true"))) {
                                            paramAnonymousMethodHookParam.setResult(Boolean.valueOf(true));
                                        }
                                        PoseHelper008.initData(null);
                                        setProductData();
                                        if ((NetworkInfo.class.getName().equals(paramString2)) && (PoseHelper008.valueMap.getString("connect_mode") != null)) {
                                            localObject1 = PoseHelper008.valueMap.getString("connect_mode");
                                            if (((String) localObject1).equals("1")) {
                                                paramAnonymousMethodHookParam.setResult("WIFI");
                                            }
                                        } else if ((ConnectivityManager.class.getName().equals(paramString2)) && (PoseHelper008.valueMap.getString("connect_mode") != null)) {
                                            localObject1 = PoseHelper008.valueMap.getString("connect_mode");
                                            if ((((String) localObject1).equals("1")) || (((String) localObject1).equals("2"))) {
                                                if ((NetworkInfo) paramAnonymousMethodHookParam.getResult() != null) {
                                                    break label597;
                                                }
                                                if ((paramAnonymousMethodHookParam.args[0].equals(Integer.valueOf(1))) && (((String) localObject1).equals("2"))) {
                                                    paramAnonymousMethodHookParam.args[0] = Integer.valueOf(0);
                                                }
                                            }
                                        }
                                        for (; ; ) {
                                            if (PoseHelper008.valueMap.getString("getDeviceId") != null) {
                                                break label632;
                                            }
                                            super.beforeHookedMethod(paramAnonymousMethodHookParam);
                                            return;
                                            super.beforeHookedMethod(paramAnonymousMethodHookParam);
                                            return;
                                            if (!((String) localObject1).equals("2")) {
                                                break;
                                            }
                                            paramAnonymousMethodHookParam.setResult("MOBILE");
                                            break;
                                            if ((paramAnonymousMethodHookParam.args[0].equals(Integer.valueOf(1))) && (((String) localObject1).equals("1"))) {
                                                paramAnonymousMethodHookParam.setResult(null);
                                            }
                                        }
                                        localObject1 = SetDataActivity.getSign(PoseHelper008.valueMap.getString("getDeviceId"));
                                        if (!((String) localObject1).trim().equals(PoseHelper008.valueMap.getString("sign").trim())) {
                                            onLog("签名错误" + (String) localObject1 + "   sign:" + PoseHelper008.valueMap.getString("sign"));
                                            super.beforeHookedMethod(paramAnonymousMethodHookParam);
                                            return;
                                        }
                                        if (PoseHelper008.valueMap == null) {
                                            break label2145;
                                        }
                                        int i;
                                        if ((GsmCellLocation.class.getName().equals(paramString2)) || (NeighboringCellInfo.class.getName().equals(paramString2))) {
                                            if ((PoseHelper008.valueMap.get("getJiZhan") != null) && (PoseHelper008.valueMap.get("location_mode").equals("1"))) {
                                                localObject1 = PoseHelper008.valueMap.get("getJiZhan").split("_");
                                                if (!paramString3.equals("getLac")) {
                                                    break label976;
                                                }
                                                i = Integer.parseInt(localObject1[0]);
                                                paramAnonymousMethodHookParam.setResult(Integer.valueOf(i));
                                                if (Tutorial008.debug) {
                                                    onLog("getLac  " + i);
                                                }
                                            }
                                        }
                                        do {
                                            for (; ; ) {
                                                if (PoseHelper008.valueMap.get(paramString3) == null) {
                                                    break label2139;
                                                }
                                                if ((!paramString3.equals("getNetworkType")) && (!paramString3.equals("getPhoneType")) && (!paramString3.equals("getSimState"))) {
                                                    break label1724;
                                                }
                                                paramAnonymousMethodHookParam.setResult(Integer.valueOf(Integer.parseInt(PoseHelper008.valueMap.get(paramString3))));
                                                return;
                                                if (paramString3.equals("getCid")) {
                                                    i = Integer.parseInt(localObject1[1]);
                                                    paramAnonymousMethodHookParam.setResult(Integer.valueOf(i));
                                                    if (Tutorial008.debug) {
                                                        onLog("getCid  " + i);
                                                        continue;
                                                        if (Location.class.getName().equals(paramString2)) {
                                                            if ((PoseHelper008.valueMap.get("gps") != null) && (PoseHelper008.valueMap.get("location_mode").equals("2"))) {
                                                                localObject1 = PoseHelper008.valueMap.get("gps");
                                                                localObject2 = ((String) localObject1).split("_");
                                                                if (localObject2.length == 2) {
                                                                    onLog("gps定位" + (String) localObject1);
                                                                    double d1 = Double.parseDouble(localObject2[0]);
                                                                    double d2 = Double.parseDouble(localObject2[1]);
                                                                    if (paramString3.equals("getLatitude")) {
                                                                        paramAnonymousMethodHookParam.setResult(Double.valueOf(d1));
                                                                    } else if (paramString3.equals("getLongitude")) {
                                                                        paramAnonymousMethodHookParam.setResult(Double.valueOf(d2));
                                                                    }
                                                                }
                                                            }
                                                        } else if (CdmaCellLocation.class.getName().equals(paramString2)) {
                                                            if ((PoseHelper008.valueMap.get("getJiZhan") != null) && (PoseHelper008.valueMap.get("location_mode").equals("1"))) {
                                                                onLog("基站定位");
                                                                localObject1 = PoseHelper008.valueMap.get("getJiZhan").split("_");
                                                                if (paramString3.equals("getNetworkId")) {
                                                                    i = Integer.parseInt(localObject1[0]);
                                                                    paramAnonymousMethodHookParam.setResult(Integer.valueOf(i));
                                                                    if (Tutorial008.debug) {
                                                                        onLog("getNetworkId  " + i);
                                                                    }
                                                                } else if (paramString3.equals("getBaseStationId")) {
                                                                    i = Integer.parseInt(localObject1[1]);
                                                                    paramAnonymousMethodHookParam.setResult(Integer.valueOf(i));
                                                                    if (Tutorial008.debug) {
                                                                        onLog("getBaseStationId  " + i);
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            if (!WifiManager.class.getName().equals(paramString2)) {
                                                                break;
                                                            }
                                                            if (SettingActivity.getValue("setScan").equals("true")) {
                                                                onLog("阻止--获取wifi列表");
                                                                paramAnonymousMethodHookParam.setResult(new ArrayList());
                                                            } else {
                                                                super.beforeHookedMethod(paramAnonymousMethodHookParam);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if (WebView.class.getName().equals(paramString2)) {
                                                localObject1 = WebViewHookActivity.getSet();
                                                localObject2 = ((JSONObject) localObject1).get("setStr");
                                                if (!((JSONObject) localObject1).get("open").trim().equals("true")) {
                                                    break;
                                                }
                                                i = ((String) localObject2).indexOf("_");
                                                if (i == -1) {
                                                    break;
                                                }
                                                localObject1 = ((String) localObject2).substring(i + 1);
                                                if (!paramString1.equals(localObject1)) {
                                                    break;
                                                }
                                                onLog("webView");
                                                WebViewHookActivity.addLog(paramAnonymousMethodHookParam.args[0]);
                                                return;
                                            }
                                        } while (!Runtime.class.getName().equals(paramString2));
                                        localObject1 = (String) paramAnonymousMethodHookParam.args[0];
                                    }
                                    while (((String) localObject1).indexOf("/sys/class/net/wlan0/address") < 0);
                                    paramAnonymousMethodHookParam.args[0] = ((String) localObject1).replace("/sys/class/net/wlan0/address", PoseHelper008.getMacFile());
                                    return;
                                    if (!PackageManager.class.getName().equals(paramString2)) {
                                        break;
                                    }
                                    localObject1 = JSON.parseObject(PoseHelper008.getFileData("hideProgess"));
                                }
                                while ((localObject1 == null) || (((JSONObject) localObject1).get("hide_target") == null));
                                localObject1 = localObject1.get("hide_target").split("_")[0];
                                return;
                                if (!ActivityManager.class.getName().equals(paramString2)) {
                                    break;
                                }
                                localObject1 = JSON.parseObject(PoseHelper008.getFileData("hideProgess"));
                            }
                            while ((localObject1 == null) || (((JSONObject) localObject1).get("hide_target") == null));
                            Object localObject1 = localObject1.get("hide_target").split("_")[0];
                            return;
                            if (!"android.os.SystemProperties".equals(paramString2)) {
                                break;
                            }
                        } while (paramAnonymousMethodHookParam.args.length <= 1);
                        if ((paramAnonymousMethodHookParam.args[0].equals("gsm.version.baseband")) && (paramAnonymousMethodHookParam.args[1].equals("no message"))) {
                            onLog("gsm.version.baseband " + PoseHelper008.valueMap.get(paramString3));
                            paramAnonymousMethodHookParam.setResult(PoseHelper008.valueMap.get(paramString3));
                            return;
                        }
                        onLog("android.os.SystemProperties  " + paramAnonymousMethodHookParam.args[0]);
                        super.beforeHookedMethod(paramAnonymousMethodHookParam);
                        return;
                        if (!paramString2.equals(Settings.Secure.class.getName())) {
                            break;
                        }
                    } while (paramAnonymousMethodHookParam.args.length <= 1);
                    if (((String) paramAnonymousMethodHookParam.args[1]).equals("android_id")) {
                        onLog("android_id :" + PoseHelper008.valueMap.get(paramString3));
                        paramAnonymousMethodHookParam.setResult(PoseHelper008.valueMap.get(paramString3));
                        return;
                    }
                    super.beforeHookedMethod(paramAnonymousMethodHookParam);
                    return;
                } while (paramString2.equals(Display.class.getName()));
                label976:
                paramAnonymousMethodHookParam.setResult(PoseHelper008.valueMap.get(paramString3));
                label1724:
                return;
                label2139:
                super.beforeHookedMethod(paramAnonymousMethodHookParam);
                return;
                label2145:
                super.beforeHookedMethod(paramAnonymousMethodHookParam);
            }
        };
        Object[] arrayOfObject = new Object[paramVarArgs.length + 1];
        int i = 0;
        for (; ; ) {
            if (i >= paramVarArgs.length) {
                arrayOfObject[paramVarArgs.length] = paramString1;
                XposedHelpers.findAndHookMethod(paramString2, paramClassLoader, paramString3, arrayOfObject);
                return;
            }
            arrayOfObject[i] = paramVarArgs[i];
            i += 1;
        }
    }*/
}
