package com.dovar.fakermobile;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Size;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class SimulateDataTemp {
    private static final int DIANXING = 3;
    private static final int LIANTONG = 2;
    private static final int YIDONG = 1;
    private static HashMap<String, String> apiLevelMap = new HashMap<>();
    static HashMap<String, String> imeiMap = null;
    public static ArrayList<HashMap<String, String>> imeiStore;
    static HashMap<String, String[]> imsiPhoneTag;
    public static Random r;
    public String datagogo = "";

    static {
        apiLevelMap.put("6", "2.0.1");
        apiLevelMap.put("7", "2.1.2");
        apiLevelMap.put("8", "2.2.3");
        apiLevelMap.put("9", "2.3.1");
        apiLevelMap.put("10", "2.3.4");
        apiLevelMap.put("11", "3.0.3");
        apiLevelMap.put("12", "3.1.1");
        apiLevelMap.put("13", "3.2");
        apiLevelMap.put("14", "4.0.2");
        apiLevelMap.put("15", "4.0.4");
        apiLevelMap.put("16", "4.1.1");
        apiLevelMap.put("17", "4.2.2");
        apiLevelMap.put("18", "4.3.3");
        apiLevelMap.put("19", "4.4.2");
        apiLevelMap.put("19", "4.4.1");
        r = new Random(new Date().getTime());
        imsiPhoneTag = new HashMap<>();
        imeiStore = new ArrayList<>();
    }

    public static String getFromAssets(Context paramContext, String paramString) {
        String str = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(paramContext.getResources().getAssets().open(paramString)));
            for (paramString = str; ; paramString = paramString + str + "\n") {
                str = br.readLine();
                if (str == null) {
                    return paramString;
                }
            }
        } catch (Exception mE) {
            mE.printStackTrace();
        }
        return paramString;
    }

    public static String getDisplay() {
        if (imeiMap != null) {
            return (String) imeiMap.get("display");
        }
        return null;
    }

//    public static String getHead(boolean paramBoolean) {
//        int i;
//        if (paramBoolean) {
//            do {
//                i = r.nextInt(imeiStore.size());
//                imeiMap = (HashMap) imeiStore.get(i);
//            } while (((String) imeiMap.get("imei")).toUpperCase().indexOf("A") != 0);
//        }
//        for (; ; ) {
//            return (String) imeiMap.get("imei");
//            do {
//                i = r.nextInt(imeiStore.size());
//                imeiMap = (HashMap) imeiStore.get(i);
//            } while (((String) imeiMap.get("imei")).toUpperCase().indexOf("A") == 0);
//        }
//    }

//    public static String getMac() {
//        int i = 0;
//        Object localObject;
//        if (i >= SetDataActivity.c.length()) {
//            localObject = "";
//            i = 0;
//        }
//        for (; ; ) {
//            if (i >= 6) {
//                return localObject;
//                SetDataActivity.k.length();
//                i += 1;
//                break;
//            }
//            String str2 = Integer.toHexString(Byte.valueOf((byte) r.nextInt(255)).byteValue() & 0xFF);
//            String str1 = str2;
//            if (str2.length() == 1) {
//                str1 = "0" + str2;
//            }
//            str1 = localObject + str1;
//            localObject = str1;
//            if (i != 5) {
//                localObject = str1 + ":";
//            }
//            i += 1;
//        }
//    }

    public static String getModal() {
        if (imeiMap != null) {
            return (String) imeiMap.get("name") + " " + (String) imeiMap.get("modal");
        }
        return null;
    }

    public static String getPhoneName() {
        if (imeiMap != null) {
            return (String) imeiMap.get("name");
        }
        return null;
    }


    public static String getProductName() {
        if (imeiMap != null) {
            return (String) imeiMap.get("modal");
        }
        return null;
    }

//    public static String getRandData(int paramInt1, int paramInt2) {
//        String str2 = "";
//        String str1;
//        if (paramInt1 == 1) {
//            paramInt1 = 0;
//            str1 = str2;
//            if (paramInt1 < paramInt2) {
//            }
//        }
//        do {
//            do {
//                SetDataActivity.f.length();
//                return str1;
//                str1 = str1 + r.nextInt(10);
//                paramInt1 += 1;
//                break;
//                str1 = str2;
//            } while (paramInt1 == 2);
//            str1 = str2;
//        } while (paramInt1 != 3);
//        paramInt1 = 0;
//        for (; ; ) {
//            str1 = str2;
//            if (paramInt1 >= paramInt2) {
//                break;
//            }
//            str1 = Integer.toHexString(Byte.valueOf((byte) r.nextInt(17)).byteValue() & 0xF);
//            str2 = str2 + str1;
//            paramInt1 += 1;
//        }
//    }

//    public static String getRandData(String paramString, int paramInt1, int paramInt2) {
//        SetDataActivity.a.length();
//        String str = paramString;
//        int i = paramInt1 - paramString.length();
//        if (paramInt2 == 1) {
//            paramInt1 = 0;
//            paramString = str;
//            if (paramInt1 < i) {
//            }
//        }
//        do {
//            do {
//                return paramString;
//                paramString = paramString + r.nextInt(10);
//                paramInt1 += 1;
//                break;
//                paramString = str;
//            } while (paramInt2 == 2);
//            paramString = str;
//        } while (paramInt2 != 3);
//        paramInt1 = 0;
//        for (; ; ) {
//            paramString = str;
//            if (paramInt1 >= i) {
//                break;
//            }
//            paramString = Integer.toHexString(Byte.valueOf((byte) r.nextInt(17)).byteValue() & 0xF);
//            str = str + paramString;
//            paramInt1 += 1;
//        }
//    }


    public static void init(Context paramContext) {
        if (imeiStore.size() > 0) {
            return;
        }
        String[] datas = getFromAssets(paramContext, "imeiStore").split("\n");
        int j = datas.length;
        int i = 0;
        for (; ; ) {
            i += 1;
            if (i < j) {
                String mData = datas[i];
                if (mData.trim().length() != 0) {
                    String[] localObject4 = mData.split(" ");
                    if (localObject4.length == 4) {
                        HashMap<String, String> localHashMap = new HashMap<>();
                        localHashMap.put("imei", localObject4[0]);
                        localHashMap.put("brand", localObject4[1]);
                        localHashMap.put("model", localObject4[2]);
                        localHashMap.put("display", localObject4[3]);
                        imeiStore.add(localHashMap);
                    }
                }
            } else {
                break;
            }
        }
    }

    public static boolean isNumeric(String paramString) {
        int i = paramString.length();
        int j;
        do {
            i -= 1;
            if (i < 0) {
                return true;
            }
            j = paramString.charAt(i);
        } while ((j >= 48) && (j <= 57));
        return false;
    }


    private static HashMap<String, String> sdkLevel;

    static {
        if (sdkLevel == null) {
            sdkLevel = new HashMap<>();
        }
        sdkLevel.put("17", "4.2.2");
        sdkLevel.put("18", "4.3.3");
        sdkLevel.put("19", "4.4.2");
        sdkLevel.put("20", "4.4");
        sdkLevel.put("21", "5.0");
        sdkLevel.put("22", "5.1");
        sdkLevel.put("23", "6.0");
        sdkLevel.put("24", "7.0");
        sdkLevel.put("25", "7.1");
    }

    public static String getSdkVersion(String key) {
        return sdkLevel.get(key);
    }

    public static String randomSdkLevel() {
        if (sdkLevel == null || sdkLevel.size() == 0) {
            return "19";
        }
        Random r = new Random();
        return String.valueOf(r.nextInt(sdkLevel.size()) + 17);
    }

    public static final int TYPE_MOBILE = 0;
    public static final int TYPE_WIFI = 1;

    /**
     * Network type is unknown
     */
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    /**
     * Current network is GPRS
     */
    public static final int NETWORK_TYPE_GPRS = 1;
    /**
     * Current network is EDGE
     */
    public static final int NETWORK_TYPE_EDGE = 2;
    /**
     * Current network is UMTS
     */
    public static final int NETWORK_TYPE_UMTS = 3;
    /**
     * Current network is CDMA: Either IS95A or IS95B
     */
    public static final int NETWORK_TYPE_CDMA = 4;
    /**
     * Current network is EVDO revision 0
     */
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    /**
     * Current network is EVDO revision A
     */
    public static final int NETWORK_TYPE_EVDO_A = 6;
    /**
     * Current network is 1xRTT
     */
    public static final int NETWORK_TYPE_1xRTT = 7;
    /**
     * Current network is HSDPA
     */
    public static final int NETWORK_TYPE_HSDPA = 8;
    /**
     * Current network is HSUPA
     */
    public static final int NETWORK_TYPE_HSUPA = 9;
    /**
     * Current network is HSPA
     */
    public static final int NETWORK_TYPE_HSPA = 10;
    /**
     * Current network is iDen
     */
    public static final int NETWORK_TYPE_IDEN = 11;
    /**
     * Current network is EVDO revision B
     */
    public static final int NETWORK_TYPE_EVDO_B = 12;
    /**
     * Current network is LTE
     */
    public static final int NETWORK_TYPE_LTE = 13;
    /**
     * Current network is eHRPD
     */
    public static final int NETWORK_TYPE_EHRPD = 14;
    /**
     * Current network is HSPA+
     */
    public static final int NETWORK_TYPE_HSPAP = 15;
    /**
     * Current network is GSM {@hide}
     */
    public static final int NETWORK_TYPE_GSM = 16;
    /**
     * Current network is TD_SCDMA {@hide}
     */
    public static final int NETWORK_TYPE_TD_SCDMA = 17;


    public static
    @Size(2)
    int[] getNetworkType(String networkOperatorName) {
        int[] types = new int[2];
        if (networkOperatorName == null) return types;
        Random r = new Random();
        if (r.nextBoolean()) {
            types[0] = TYPE_MOBILE;
            ArrayList<Integer> netType = new ArrayList<>();
            netType.add(NETWORK_TYPE_GPRS);
            netType.add(NETWORK_TYPE_HSPAP);
            netType.add(NETWORK_TYPE_HSDPA);
            netType.add(NETWORK_TYPE_HSUPA);
            netType.add(NETWORK_TYPE_GSM);
            netType.add(NETWORK_TYPE_LTE);
            switch (networkOperatorName) {//添加各自特有的网络制式
                case "中国联通"://NETWORK_TYPE_UMTS
                    netType.add(NETWORK_TYPE_UMTS);
                    break;
                case "中国移动":
                    break;
                case "中国电信"://NETWORK_TYPE_CDMA
                    netType.add(NETWORK_TYPE_CDMA);
                    break;
            }
            int index = r.nextInt(netType.size());
            types[1] = netType.get(index);
        } else {
            types[0] = TYPE_WIFI;
            types[1] = NETWORK_TYPE_UNKNOWN;
        }
        return types;
    }

    public static
    @Size(3)
    String[] getSimData() {
        String[] strs = new String[3];
        int i = r.nextInt(4);
        switch (i) {
            case 0:
                strs[0] = "中国移动";
                break;
            case 1:
                strs[0] = "中国联通";
                break;
            case 2:
                strs[0] = "中国电信";
                break;
        }

        strs[1] = getSimSerial(i);
        strs[2] = getSubscriberId(i);
        return strs;
    }

    //手机卡序列号
    public static String getSimSerial(int networkOperator) {
        String str = "";
        switch (networkOperator) {
            case 0://"中国移动"
                if (r.nextBoolean()) {
                    str = "898600";
                } else {
                    str = "898602";
                }
                break;
            case 1://"中国联通"
                str = "898601";
                break;
            case 2://"中国电信"
                str = "898603";
                break;
        }
        Random localRandom;
        for (int i = 0; i < 14; i++) {
            localRandom = new Random(new Date().getTime());
            int index = (int) (localRandom.nextFloat() * 10.0F);
            str += index;
        }
        return str;
    }

    //imsi
    public static String getSubscriberId(int networkOperator) {
        String str = "";
        switch (networkOperator) {
            case 0://"中国移动"
                if (r.nextBoolean()) {
                    str = "46000";
                } else {
                    str = "46002";
                }
                break;
            case 1://"中国联通"
                str = "46001";
                break;
            case 2://"中国电信"
                str = "46003";
                break;
        }
//        Random localRandom;
//        for (int i = 0; i < 10; i++) {
//            localRandom = new Random(new Date().getTime());
//            int index = (int) (localRandom.nextFloat() * 10.0F);
//            str += index;
//        }
        return str;
    }

    public static String getPhoneNumber(String imsi) {
        if (imsi == null) return "";
        if (imsiPhoneTag.size() == 0) {
            imsiPhoneTag.put("46000", new String[]{"135", "136", "137", "138", "139"});
            imsiPhoneTag.put("46002", new String[]{"150", "151", "152", "158", "159", "134"});
            imsiPhoneTag.put("46007", new String[]{"147", "157", "187", "188"});
            imsiPhoneTag.put("46001", new String[]{"130", "131", "132", "155", "156"});
            imsiPhoneTag.put("46003", new String[]{"133", "1349", "180", "153", "189"});
        }
        if (imsi.length() > 5) {
            imsi = imsi.substring(0, 5);
        }
        String[] mStrings = imsiPhoneTag.get(imsi);
        if (mStrings == null) return "";
        imsi = mStrings[r.nextInt(mStrings.length)];
        int j = imsi.length();
        for (int i = 0; i < 11 - j; i++) {
            int k = r.nextInt(10);
            imsi = imsi + k;
        }
        return imsi;
//                String[] ss = new String[4];
//                ss[0] = "3";
//                ss[1] = "5";
//                ss[2] = "4";
//                ss[3] = "8";
//                paramString = "1" + ss[r.nextInt(ss.length)];
//                break;
    }

    public static String generateImei(String imei) {
        if (imei == null) return "";
        if (imei.length() > 8) {
            imei = imei.substring(0, 8);
        }
        int j = imei.length();
        for (int i = 0; i < 15 - j; i++) {
            int k = r.nextInt(10);
            imei = imei + k;
        }
        return imei;
    }

    public static String generateAndriodID() {
        int a = r.nextInt(10);
        int b = r.nextInt(10);
        String m_szDevIDShort = "" + a + b +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        //使用硬件信息拼凑出来的15位号码
        return m_szDevIDShort;
    }

    public static String getRandData(int size) {
        String str2 = "";
        for (int i = 0; i < size; i++) {
            str2 += Integer.toHexString(Byte.valueOf((byte) r.nextInt(17)).byteValue() & 0xF);
        }
        return str2;
    }
}

