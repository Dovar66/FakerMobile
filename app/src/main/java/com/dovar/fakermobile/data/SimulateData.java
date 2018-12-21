package com.dovar.fakermobile.data;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.SparseArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class SimulateData {
    public static final ArrayList<HashMap<String, String>> imeiStore;
    private static final HashMap<String, String> sdkLevel;
    private static final HashMap<String, String[]> imsiPhoneTag;

    static {
        imeiStore = new ArrayList<>();

        sdkLevel = new HashMap<>();
        sdkLevel.put("17", "4.2.2");
        sdkLevel.put("18", "4.3.3");
        sdkLevel.put("19", "4.4.2");
        sdkLevel.put("20", "4.4");
        sdkLevel.put("21", "5.0");
        sdkLevel.put("22", "5.1");
        sdkLevel.put("23", "6.0");
        sdkLevel.put("24", "7.0");
        sdkLevel.put("25", "7.1");

        imsiPhoneTag = new HashMap<>();
        imsiPhoneTag.put("46000", new String[]{"135", "136", "137", "138", "139"});
        imsiPhoneTag.put("46002", new String[]{"150", "151", "152", "158", "159", "134"});
        imsiPhoneTag.put("46007", new String[]{"147", "157", "187", "188"});
        imsiPhoneTag.put("46001", new String[]{"130", "131", "132", "155", "156"});
        imsiPhoneTag.put("46003", new String[]{"133", "1349", "180", "153", "189"});
    }

    public static void init(Context mContext) {
        if (imeiStore.size() > 0) {
            return;
        }
        String[] datas = getFromAssets(mContext).split("\n");
        for (String str : datas) {
            if (str.trim().length() != 0) {
                String[] localObject4 = str.split(" ");
                if (localObject4.length == 4) {
                    HashMap<String, String> localHashMap = new HashMap<>();
                    localHashMap.put("imei", localObject4[0]);
                    localHashMap.put("brand", localObject4[1]);
                    localHashMap.put("model", localObject4[2]);
                    localHashMap.put("display", localObject4[3]);
                    imeiStore.add(localHashMap);
                }
            }
        }
    }

    private static String getFromAssets(Context mContext) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(mContext.getResources().getAssets().open("imeiStore")));
            String str;
            do {
                str = br.readLine();
                if (str == null) return result.toString();
                result.append(str).append("\n");
            } while (true);
        } catch (Exception mE) {
            mE.printStackTrace();
        }
        return result.toString();
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

    public static NetworkInfos genNetworkInfo() {
        NetworkInfos networkType = new NetworkInfos();
        int i = new Random().nextInt(3);//随机运营商
        String imsi = getSubscriberId(i);
        networkType.setImsi(imsi);
        networkType.setNetworkOperator(imsi.substring(0, 5));
        networkType.setPhoneNumber(genPhoneNumber(imsi));
        networkType.setSimSerial(getSimSerial(i));
        switch (i) {
            case 0:
                networkType.setNetworkOperatorName("中国移动");
                break;
            case 1:
                networkType.setNetworkOperatorName("中国联通");
                break;
            case 2:
                networkType.setNetworkOperatorName("中国电信");
                break;
        }
        if (System.currentTimeMillis() % 2 == 0) {//随机WIFI
            networkType.setType(1);
            networkType.setTypeName("WIFI");
            return networkType;
        }
        networkType.setType(0);
        networkType.setTypeName("MOBILE");

        SparseArray<String> netType = new SparseArray<>();
        netType.put(TelephonyManager.NETWORK_TYPE_GPRS, "GPRS");//1
        netType.put(TelephonyManager.NETWORK_TYPE_EDGE, "EDGE");//2
        netType.put(TelephonyManager.NETWORK_TYPE_UMTS, "UMTS");//3
        netType.put(TelephonyManager.NETWORK_TYPE_CDMA, "CDMA");
        netType.put(TelephonyManager.NETWORK_TYPE_EVDO_0, "EVDO_O");
        netType.put(TelephonyManager.NETWORK_TYPE_EVDO_A, "EVDO_A");
        netType.put(TelephonyManager.NETWORK_TYPE_1xRTT, "1xRTT");
        netType.put(TelephonyManager.NETWORK_TYPE_HSDPA, "HSDPA");
        netType.put(TelephonyManager.NETWORK_TYPE_HSUPA, "HSUPA");
        netType.put(TelephonyManager.NETWORK_TYPE_HSPA, "HSPA");
        netType.put(TelephonyManager.NETWORK_TYPE_IDEN, "IDEN");
        netType.put(TelephonyManager.NETWORK_TYPE_EVDO_B, "EVDO_B");
        netType.put(TelephonyManager.NETWORK_TYPE_LTE, "LTE");
        netType.put(TelephonyManager.NETWORK_TYPE_EHRPD, "EHRPD");//14
        netType.put(TelephonyManager.NETWORK_TYPE_HSPAP, "HSPAP");//15
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            netType.put(TelephonyManager.NETWORK_TYPE_GSM, "GSM");//16
            netType.put(TelephonyManager.NETWORK_TYPE_TD_SCDMA, "TD_SCDMA");//17
        }

        int subType = new Random().nextInt(netType.size()) + 1;
        networkType.setSubType(subType);
        networkType.setSubTypeName(netType.get(subType));
        return networkType;
    }

    //手机卡序列号
    private static String getSimSerial(int networkOperator) {
        String str = "";
        switch (networkOperator) {
            case 0://"中国移动"
                if (System.currentTimeMillis() % 2 == 0) {
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
        for (int i = 0; i < 14; i++) {
            int index = (new Random().nextInt(10));
            str += index;
        }
        return str;
    }

    //imsi
    @NonNull
    private static String getSubscriberId(int networkOperator) {
        String str = "";
        switch (networkOperator) {
            case 0://"中国移动"
                if (System.currentTimeMillis() % 2 == 0) {
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
        for (int i = 0; i < 10; i++) {
            int index = new Random().nextInt(10);
            str += index;
        }
        return str;
    }

    private static String genPhoneNumber(String imsi) {
        if (imsi == null) return "";
        if (imsi.length() >= 5) {
            imsi = imsi.substring(0, 5);
        }
        String[] mStrings = imsiPhoneTag.get(imsi);
        if (mStrings == null) return "";
        imsi = mStrings[new Random().nextInt(mStrings.length)];
        int j = imsi.length();
        for (int i = 0; i < 11 - j; i++) {
            int k = new Random().nextInt(10);
            imsi = imsi + k;
        }
        return imsi;
    }

    /*public static String generateAndriodID() {
        int a = new Random().nextInt(10);
        int b = new Random().nextInt(10);
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
    }*/

    public static String getRandomProp(String name) {
        Random r = new Random();

        if (name.equals("SERIAL")) {
            long v = r.nextLong();
            return Long.toHexString(v).toUpperCase();
        }

        if (name.equals("MAC")) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                if (i != 0)
                    sb.append(':');
                int v = r.nextInt(256);
                if (i == 0)
                    v = v & 0xFC; // unicast, globally unique
                sb.append(Integer.toHexString(0x100 | v).substring(1));
            }
            return sb.toString().toUpperCase();
        }

        // IMEI/MEID
        if (name.equals("IMEI")) {
            // http://en.wikipedia.org/wiki/Reporting_Body_Identifier
            String[] rbi = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "30", "33",
                    "35", "44", "45", "49", "50", "51", "52", "53", "54", "86", "91", "98", "99"};
            String imei = rbi[r.nextInt(rbi.length)];
            while (imei.length() < 14)
                imei += Character.forDigit(r.nextInt(10), 10);
            imei += getLuhnDigit(imei);
            return imei;
        }

        if (name.equals("PHONE")) {
            String phone = "0";
            for (int i = 1; i < 10; i++)
                phone += Character.forDigit(r.nextInt(10), 10);
            return phone;
        }

        if (name.equals("ANDROID_ID")) {
            long v = r.nextLong();
            return Long.toHexString(v);
        }

        if (name.equals("ISO3166")) {
            String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String country = Character.toString(letters.charAt(r.nextInt(letters.length())))
                    + Character.toString(letters.charAt(r.nextInt(letters.length())));
            return country;
        }

        if (name.equals("GSF_ID")) {
            long v = Math.abs(r.nextLong());
            return Long.toString(v, 16).toUpperCase();
        }

        if (name.equals("AdvertisingId"))
            return UUID.randomUUID().toString().toUpperCase();

        if (name.equals("LAT")) {
            double d = r.nextDouble() * 180 - 90;
            d = Math.rint(d * 1e7) / 1e7;
            return Double.toString(d);
        }

        if (name.equals("LON")) {
            double d = r.nextDouble() * 360 - 180;
            d = Math.rint(d * 1e7) / 1e7;
            return Double.toString(d);
        }

        if (name.equals("ALT")) {
            double d = r.nextDouble() * 2 * 686;
            return Double.toString(d);
        }

        if (name.equals("SubscriberId")) {
            String subscriber = "00101";
            while (subscriber.length() < 15)
                subscriber += Character.forDigit(r.nextInt(10), 10);
            return subscriber;
        }

        if (name.equals("SSID")) {
            String ssid = "";
            while (ssid.length() < 6)
                ssid += (char) (r.nextInt(26) + 'A');

            ssid += Character.forDigit(r.nextInt(10), 10);
            ssid += Character.forDigit(r.nextInt(10), 10);
            return ssid;
        }

        return "";
    }

    private static char getLuhnDigit(String x) {
        // http://en.wikipedia.org/wiki/Luhn_algorithm
        int sum = 0;
        for (int i = 0; i < x.length(); i++) {
            int n = Character.digit(x.charAt(x.length() - 1 - i), 10);
            if (i % 2 == 0) {
                n *= 2;
                if (n > 9)
                    n -= 9; // n = (n % 10) + 1;
            }
            sum += n;
        }
        return Character.forDigit((sum * 9) % 10, 10);
    }

    /**
     * 生成IMEI号,共15位，由TAC+FAC+SNR+CD组成，其中
     * TAC IMEI前8位（早期功能机时代是6位），与机型和产地相关
     * FAC 装配码，2位，仅在早期TAC码为6位的机型中存在，可以忽略
     * SNR 生成序列号，6位，可以随机生成
     * CD 检验码，由前14位数字根据Luhn算法计算得到
     * <p>
     * tac IMEI前8位，与机型和产地相关
     *
     * @return IMEI号 实测有效
     */
    public static String genIMEI(String imei) {
        String tac;
        if (imei == null) return "";
        if (imei.length() > 8) {
            tac = imei.substring(0, 8);
        } else {
            return "";
        }
        int rdSNR = (int) (Math.random() * 1000000);
        String snr = String.format(Locale.US, "%06d", rdSNR);
        StringBuffer result = new StringBuffer();
        result.append(tac);
        result.append(snr);
        result.append(genCheckCode(result.toString()));
        return result.toString();
    }

    /**
     * 生成IMEI校验码，由前14位数字根据Luhn算法计算得到
     *
     * @param code IMEI前14位
     * @return IMEI校验码
     */
    private static String genCheckCode(String code) {
        int total = 0, sum1 = 0, sum2 = 0;
        int temp = 0;
        char[] chs = code.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            int num = chs[i] - '0';    // ascii to num
            /*(1)将奇数位数字相加(从1开始计数)*/
            if (i % 2 == 0) {
                sum1 = sum1 + num;
            } else {
                /*(2)将偶数位数字分别乘以2,分别计算个位数和十位数之和(从1开始计数)*/
                temp = num * 2;
                if (temp < 10) {
                    sum2 = sum2 + temp;
                } else {
                    sum2 = sum2 + temp + 1 - 10;
                }
            }
        }
        total = sum1 + sum2;
        /*如果得出的数个位是0则校验位为0,否则为10减去个位数 */
        if (total % 10 == 0) {
            return "0";
        } else {
            return (10 - (total % 10)) + "";
        }

    }
}

