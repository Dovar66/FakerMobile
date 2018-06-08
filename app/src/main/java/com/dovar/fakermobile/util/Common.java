package com.dovar.fakermobile.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Common {
    public static final String PREFS_FILE = "Device";
    public static final String PACKAGE_NAME = "com.soft.apk008v";


    public static final String[] DEFAULT_KEYWORD_LIST = new String[]{"supersu", "superuser", "Superuser",
            "noshufou", "xposed", "rootcloak",
            "chainfire", "titanium", "Titanium",
            "substrate", "greenify", "daemonsu",
            "root", "busybox", "titanium",
            ".tmpsu", "su", "rootcloak2"};

    public static final String[] DEFAULT_APPS_LIST = {"com.fde.DomesticDigitalCopy",
            "com.directv.application.android.go.production",
            "com.res.bby",
            "dk.excitor.dmemail",
            "com.BHTV",
            "com.bradfordnetworks.bma",
            "com.apriva.mobile.bams",
            "com.apriva.mobile.aprivapay",
            "pl.pkobp.iko",
            "au.com.auspost",
            "com.rogers.citytv.phone",
            "com.zenprise",
            "net.flixster.android",
            "com.starfinanz.smob.android.sfinanzstatus",
            "com.ovidos.yuppi",
            "klb.android.lovelive",
            "klb.android.lovelive_en",
            "com.nintendo.zaaa",
            "com.incube.epub",
            "com.airwatch.androidagent",
            "com.zappware.twintv.d3",
            "com.starfinanz.mobile.android.pushtan",
            "com.stofa.webtv",
            "com.barclays.android.barclaysmobilebanking",
            "com.bskyb.skygo",
            "com.hanaskcard.rocomo.potal",
            "com.hanabank.ebk.channel.android.hananbank",
            "com.ahnlab.v3mobileplus",
            "com.good.android.gfe",
            "it.phoenixspa.inbank",
            "dk.tv2.tv2play",
            "com.enterproid.divideinstaller",
            "com.isis.mclient.verizon.activity",
            "com.isis.mclient.atnt.activity",
            "be.telenet.yelo",
            "no.rdml.android.mobiletv",
            "uk.co.barclays.barclayshomeowner",
            "com.mcafee.apps.emmagent",
            "com.virginmedia.tvanywhere",
            "com.amis.mobiatv",
            "it.telecomitalia.cubovision",
            "nl.ziggo.android.tv",
            "com.orange.fr.ocs",
            "com.adb.android.app.iti",
            "com.dovar.testxp",
            "com.touchtv.touchtv",
            "com.mobileiron"};

    public static final Set<String> DEFAULT_KEYWORD_SET = new HashSet<>(Arrays.asList(DEFAULT_KEYWORD_LIST));

    public static final Set<String> DEFAULT_APPS_SET = new HashSet<String>(Arrays.asList(DEFAULT_APPS_LIST));

    public static final String[] DEFAULT_COMMAND_LIST = new String[]{"su", "which", "busybox", "pm", "am", "sh", "ps"};
    public static final Set<String> DEFAULT_COMMAND_SET = new HashSet<String>(Arrays.asList(DEFAULT_COMMAND_LIST));
    public static final String[] DEFAULT_LIBNAME_LIST = new String[]{"tool-checker"}; // RootBearNative
    public static final Set<String> DEFAULT_LIBNAME_SET = new HashSet<String>(Arrays.asList(DEFAULT_LIBNAME_LIST));
}
