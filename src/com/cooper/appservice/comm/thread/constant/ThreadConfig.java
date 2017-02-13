package com.cooper.appservice.comm.thread.constant;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-23
 * Time: 下午2:31
 * To change this template use File | Settings | File Templates.
 */
public class ThreadConfig {

    public static final int DO_FLG = 1;

    public static final int UNDO_FLG = 0;

    public static final int SYSTEM_CALL_STOP = 2;

    public static final int SYSTEM_SHUTDOWN_STOP = 3;


    public static String getJobConfigValue(String title) {
        //TODO jobConfig
        return "";
    }
    /**警告时间限制*/
    public final static int threadWarnTime = initWarningTime();

    public final static int warnKillCount = initWarnKillCount();

    public final static boolean warnKill = initWarnKillFlg();

    public final static boolean showLogFlg = initShowLogFlg();

    public final static boolean showStackFlg = initShowStackFlg();

    public final static boolean showWarningFlg = initWarningLogFlg();

    private static boolean initShowStackFlg() {
        String showStack = getJobConfigValue("showStack");
        if(showStack == null) return false;
        return Integer.valueOf(showStack) == 1;
    }

    private static boolean initShowLogFlg() {
        String showLog = getJobConfigValue("showLog");
        if(showLog == null) return false;
        return Integer.valueOf(showLog) == 1;
    }

    private static boolean initWarningLogFlg() {
        String showWarning = getJobConfigValue("showWarning");
        if(showWarning == null) return false;
        return Integer.valueOf(showWarning) == 1;
    }

    private static int initWarningTime() {
        String showStack = getJobConfigValue("warningTime");
        if(showStack == null) return 60;
        int i = Integer.valueOf(showStack);
        return i <= 0 ? 1 : i;
    }
    private static boolean initWarnKillFlg() {
        String warnKill = getJobConfigValue("warnKill");
        if(warnKill == null) return false;
        return Integer.valueOf(warnKill) == 1;
    }

    private static int initWarnKillCount() {
        String warnKillCount = getJobConfigValue("warnKillCount");
        if(warnKillCount == null) return 3;
        int i = Integer.valueOf(warnKillCount);
        return i <= 0 ? 0 : i;
    }
}
