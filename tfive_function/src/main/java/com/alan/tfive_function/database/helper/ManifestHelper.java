package com.alan.tfive_function.database.helper;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;



public final class ManifestHelper {

    private static final String LOG_TAG = "Sugar";

    private static Boolean debugEnabled = null;


    public final static String METADATA_DATABASE = "DATABASE";
    public final static String METADATA_VERSION = "VERSION";
    public final static String METADATA_DOMAIN_PACKAGE_NAME = "DOMAIN_PACKAGE_NAME";
    public final static String METADATA_QUERY_LOG = "QUERY_LOG";
    public final static String DATABASE_DEFAULT_NAME = "Sugar.db";


    private ManifestHelper() { }

    /**
     * 获取数据的版本
     * @return
     */
    public static int getDatabaseVersion(Context context) {
        Integer databaseVersion = getMetaDataInteger(METADATA_VERSION,context);

        if ((databaseVersion == null) || (databaseVersion == 0)) {
            databaseVersion = 1;
        }

        return databaseVersion;
    }


    public static String getDomainPackageName(Context context) {
        String domainPackageName = getMetaDataString(METADATA_DOMAIN_PACKAGE_NAME,context);

        if (domainPackageName == null) {
            domainPackageName = "";
        }

        return domainPackageName;
    }

    /**
     * 获取数据库名
     * @return
     */
    public static String getDatabaseName(Context context) {
        String databaseName = getMetaDataString(METADATA_DATABASE,context);

        if (databaseName == null) {
            databaseName = DATABASE_DEFAULT_NAME;
        }

        return databaseName;
    }

    public static String getDbName(Context context) {
        return getDatabaseName(context);
    }



    private static String getMetaDataString(String name,Context context) {
        PackageManager pm = getPackageManager(context);
        String value = null;

        try {
            ApplicationInfo ai = pm.getApplicationInfo(getPackageName(context), PackageManager.GET_META_DATA);
            value = ai.metaData.getString(name);
        } catch (Exception e) {

                Log.d(LOG_TAG, "Couldn't find config value: " + name);
        }

        return value;
    }

    private static Integer getMetaDataInteger(String name,Context context) {
        PackageManager pm = getPackageManager(context);
        Integer value = null;

        try {
            ApplicationInfo ai = pm.getApplicationInfo(getPackageName(context), PackageManager.GET_META_DATA);
            value = ai.metaData.getInt(name);
        } catch (Exception e) {

                Log.d(LOG_TAG, "Couldn't find config value: " + name);
        }

        return value;
    }

    private static Boolean getMetaDataBoolean(String name,Context context) {
        PackageManager pm = getPackageManager(context);
        Boolean value = false;

        try {
            ApplicationInfo ai = pm.getApplicationInfo(getPackageName(context), PackageManager.GET_META_DATA);
            value = ai.metaData.getBoolean(name);
        } catch (Exception e) {
            Log.d(LOG_TAG, "Couldn't find config value: " + name);
        }

        return value;
    }

    public static PackageManager getPackageManager(Context context) {
        return context.getPackageManager();
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }
}
