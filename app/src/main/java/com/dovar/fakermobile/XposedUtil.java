package com.dovar.fakermobile;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import com.dovar.fakermobile.util.Common;
import com.dovar.fakermobile.util.Hook;
import com.dovar.fakermobile.util.Phone;
import com.dovar.fakermobile.util.Resolution;
import com.dovar.fakermobile.util.XBuild;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by heweizong on 2018/2/27.
 */

public class XposedUtil implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam mLoadPackageParam) throws Throwable {
//        XposedBridge.log("=========Loaded app: " + mLoadPackageParam.packageName);

        if (mLoadPackageParam.packageName.equals("de.robv.android.xposed")) return;
        if (mLoadPackageParam.packageName.equals("com.touchtv.touchtv")) {
            new Hook().HookTest(mLoadPackageParam);//使更新模块后不用重启手机就能生效
            new XBuild(mLoadPackageParam);
            new Phone(mLoadPackageParam);
            new Resolution().Display(mLoadPackageParam);  //屏幕

            try {
                //不能hook抽象方法
                XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", mLoadPackageParam.classLoader, "getInstalledApplications", int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
//                    param.setResult(null);
                        XposedBridge.log("getInstalledApplications");

                        List<ApplicationInfo> packages = (List<ApplicationInfo>) param.getResult(); // Get the results from the method call
                        Iterator<ApplicationInfo> iter = packages.iterator();
                        ApplicationInfo tempAppInfo;
                        String tempPackageName;

                        // 通过ApplicationInfo列表迭代，并删除与keywordSet中的关键字匹配的任何提及
                        while (iter.hasNext()) {
                            tempAppInfo = iter.next();
                            tempPackageName = tempAppInfo.packageName;
                            if (tempPackageName != null && stringContainsFromSet(tempPackageName, keywordSet)) {
                                iter.remove();
                                XposedBridge.log("找到并隐藏包：" + tempPackageName);
                            }
                        }

                        param.setResult(packages); // 将返回值设置为干净列表
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                XposedBridge.log("getInstalledApplications:" + e.getMessage());
            }
            try {
                XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", mLoadPackageParam.classLoader, "getInstalledPackages", int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
//                    param.setResult(null);
                        XposedBridge.log("getInstalledPackages");

                        List<PackageInfo> packages = (List<PackageInfo>) param.getResult(); // Get the results from the method call
                        Iterator<PackageInfo> iter = packages.iterator();
                        PackageInfo tempPackageInfo;
                        String tempPackageName;

                        // 通过PackageInfo列表迭代，并删除与keywordSet中的关键字匹配的任何提及
                        while (iter.hasNext()) {
                            tempPackageInfo = iter.next();
                            tempPackageName = tempPackageInfo.packageName;
                            if (tempPackageName != null && stringContainsFromSet(tempPackageName, keywordSet)) {
                                iter.remove();
                                XposedBridge.log("找到并隐藏包：" + tempPackageName);
                            }
                        }

                        param.setResult(packages); // 将返回值设置为干净列表
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();

                XposedBridge.log("getInstalledPackages  error");
            }
            try {
                XposedHelpers.findAndHookMethod("android.app.ActivityManager", mLoadPackageParam.classLoader, "getRunningAppProcesses", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        param.setResult(null);
                        XposedBridge.log("getRunningAppProcesses");

//                        List<ActivityManager.RunningAppProcessInfo> processes = (List<ActivityManager.RunningAppProcessInfo>) param.getResult(); // Get the results from the method call
//                        Iterator<ActivityManager.RunningAppProcessInfo> iter = processes.iterator();
//                        ActivityManager.RunningAppProcessInfo tempProcess;
//                        String tempProcessName;
//
//                        // 通过RunningAppProcessInfo列表迭代，并删除与keywordSet中的关键字匹配的任何提及
//                        while (iter.hasNext()) {
//                            tempProcess = iter.next();
//                            tempProcessName = tempProcess.processName;
//                            if (tempProcessName != null && stringContainsFromSet(tempProcessName, keywordSet)) {
//                                iter.remove();
//                            }
//                        }
//
//                        param.setResult(processes); // 将返回值设置为干净列表

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                XposedHelpers.findAndHookMethod("android.app.ActivityManager", mLoadPackageParam.classLoader, "getRunningServices", int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        param.setResult(null);
                        XposedBridge.log("getRunningServices");

//                        List<ActivityManager.RunningServiceInfo> services = (List<ActivityManager.RunningServiceInfo>) param.getResult(); // 从方法调用获取结果
//                        Iterator<ActivityManager.RunningServiceInfo> iter = services.iterator();
//                        ActivityManager.RunningServiceInfo tempService;
//                        String tempProcessName;
//
//                        // 通过RunningServiceInfo列表迭代，并删除与keywordSet中的关键字匹配的任何提及
//                        while (iter.hasNext()) {
//                            tempService = iter.next();
//                            tempProcessName = tempService.process;
//                            if (tempProcessName != null && stringContainsFromSet(tempProcessName, keywordSet)) {
//                                iter.remove();
//                            }
//                        }
//
//                        param.setResult(services); //将返回值设置为干净列表
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                XposedHelpers.findAndHookMethod("android.app.ActivityManager", mLoadPackageParam.classLoader, "getRunningTasks", int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        param.setResult(null);
                        XposedBridge.log("getRunningTasks");
//
//                        List<ActivityManager.RunningTaskInfo> services = (List<ActivityManager.RunningTaskInfo>) param.getResult(); // 从方法调用获取结果
//                        Iterator<ActivityManager.RunningTaskInfo> iter = services.iterator();
//                        ActivityManager.RunningTaskInfo tempTask;
//                        String tempBaseActivity;
//
//                        // 通过RunningTaskInfo列表迭代，并删除与keywordSet中的关键字匹配的任何提及
//                        while (iter.hasNext()) {
//                            tempTask = iter.next();
//                            tempBaseActivity = tempTask.baseActivity.flattenToString(); // Need to make it a string for comparison
//                            if (tempBaseActivity != null && stringContainsFromSet(tempBaseActivity, keywordSet)) {
//                                iter.remove();
//                            }
//                        }
//
//                        param.setResult(services); // 将返回值设置为干净列表
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                XposedHelpers.findAndHookMethod("android.app.ActivityManager", mLoadPackageParam.classLoader, "getRecentTasks", int.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        param.setResult(null);
                        XposedBridge.log("getRecentTasks");

//                        List<ActivityManager.RecentTaskInfo> services = (List<ActivityManager.RecentTaskInfo>) param.getResult(); // 从方法调用获取结果
//                        Iterator<ActivityManager.RecentTaskInfo> iter = services.iterator();
//                        ActivityManager.RecentTaskInfo tempTask;
//                        String tempBaseActivity;
//
//                        // 通过RunningTaskInfo列表迭代，并删除与keywordSet中的关键字匹配的任何提及
//                        while (iter.hasNext()) {
//                            tempTask = iter.next();
//                            tempBaseActivity = tempTask.origActivity.getPackageName(); // Need to make it a string for comparison
//                            if (tempBaseActivity != null && stringContainsFromSet(tempBaseActivity, keywordSet)) {
//                                iter.remove();
//                            }
//                        }
//
//                        param.setResult(services); // 将返回值设置为干净列表
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    }


    private Set<String> keywordSet;
    private static final String FAKE_PACKAGE = "FAKE.JUNK.PACKAGE";
    private static final String FAKE_APPLICATION = "FAKE.JUNK.APPLICATION";

    /**
     * 处理与PackageManager相关的所有挂钩。
     */
    private void initPackageManager(final XC_LoadPackage.LoadPackageParam lpparam) {
        keywordSet = Common.DEFAULT_KEYWORD_SET;
        /**
         钩子在PackageManager中获取安装应用程序。
                   *应用程式可以通过这种方式检查其他应用程式。 在有根设备的上下文中，应用程序可能会查找SuperSU，Xposed，Superuser或其他。
                   *匹配关键字集中的条目的结果将被隐藏。
         */
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstalledApplications", int.class, new XC_MethodHook() {
            @SuppressWarnings("unchecked")
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable { // Hook after getIntalledApplications is called
                XposedBridge.log("Hooked getInstalledApplications");

                List<ApplicationInfo> packages = (List<ApplicationInfo>) param.getResult(); // Get the results from the method call
                Iterator<ApplicationInfo> iter = packages.iterator();
                ApplicationInfo tempAppInfo;
                String tempPackageName;

                // 通过ApplicationInfo列表迭代，并删除与keywordSet中的关键字匹配的任何提及
                while (iter.hasNext()) {
                    tempAppInfo = iter.next();
                    tempPackageName = tempAppInfo.packageName;
                    if (tempPackageName != null && stringContainsFromSet(tempPackageName, keywordSet)) {
                        iter.remove();
                        XposedBridge.log("找到并隐藏包：" + tempPackageName);
                    }
                }

                param.setResult(packages); // 将返回值设置为干净列表
            }
        });

        /**
         Hooks在PackageManager中getInstalledPackages。
           应用程式可以通过这种方式检查其他应用程式。 在有根设备的上下文中，应用程序可能会查找SuperSU，Xposed，Superuser或其他。
               匹配关键字集中的条目的结果将被隐藏。
         */
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstalledPackages", int.class, new XC_MethodHook() {
            @SuppressWarnings("unchecked")
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable { // Hook after getInstalledPackages is called
                XposedBridge.log("Hooked getInstalledPackages");

                List<PackageInfo> packages = (List<PackageInfo>) param.getResult(); // Get the results from the method call
                Iterator<PackageInfo> iter = packages.iterator();
                PackageInfo tempPackageInfo;
                String tempPackageName;

                // 通过PackageInfo列表迭代，并删除与keywordSet中的关键字匹配的任何提及
                while (iter.hasNext()) {
                    tempPackageInfo = iter.next();
                    tempPackageName = tempPackageInfo.packageName;
                    if (tempPackageName != null && stringContainsFromSet(tempPackageName, keywordSet)) {
                        iter.remove();
                        XposedBridge.log("找到并隐藏包：" + tempPackageName);
                    }
                }

                param.setResult(packages); // 将返回值设置为干净列表
            }
        });

        /**
         在PackageManager中挂钩getPackageInfo。
                   *应用程序可以以这种方式检查其他包。 我们在getPackageInfo被调用之前钩。
                   *如果正在查看的包与keywordSet中的条目匹配，则替换假包名称。
                   *这将最终抛出一个PackageManager.NameNotFoundException。
         */
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getPackageInfo", String.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("Hooked getPackageInfo");
                String name = (String) param.args[0];

                if (name != null && stringContainsFromSet(name, keywordSet)) {
                    param.args[0] = FAKE_PACKAGE; // 设置假包名称
                    XposedBridge.log("设置假包名称: " + name);
                }
            }
        });

        /**
         Hooks在PackageManager中获取getApplicationInfo。
                   *应用程序可以通过这种方式检查其他应用程序。 我们在getApplicationInfo被调用之前钩。
                   *如果正在查看的应用程序与keywordSet中的条目匹配，则替换假冒的应用程序名称。
                   *这将最终抛出一个PackageManager.NameNotFoundException。
         */
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getApplicationInfo", String.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                String name = (String) param.args[0];
                XposedBridge.log("Hooked getApplicationInfo : " + name);

                if (name != null && stringContainsFromSet(name, keywordSet)) {
                    param.args[0] = FAKE_APPLICATION; // 设置一个假的应用程序名称
                    XposedBridge.log("发现和隐藏应用程序： " + name);
                }
            }
        });
    }

    /**
     * 获取字符串和一组字符串，并检查基本字符串是否包含集合中的任何值。
     */
    public boolean stringContainsFromSet(String base, Set<String> values) {
        if (base != null && values != null) {
            for (String tempString : values) {
                if (base.matches(".*(\\W|^)" + tempString + "(\\W|$).*")) {
                    return true;
                }
            }
        }

        return false;
    }
}
