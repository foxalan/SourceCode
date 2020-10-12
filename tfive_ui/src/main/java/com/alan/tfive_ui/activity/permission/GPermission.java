package com.alan.tfive_ui.activity.permission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class GPermission {

    public static final String TAG = "GPermission";

    private PermissionCallback permissionCallback;
    private String[] permissions;
    private Context context;

    private String refuseTips;

    public GPermission(Context context) {
        this.context = context;
    }

    public static GPermission init(Context context) {
        GPermission permission = new GPermission(context);
        return permission;
    }

    public GPermission permission(String[] permissions) {
        this.permissions = permissions;
        return this;
    }

    public GPermission withPermissionCallback(PermissionCallback callback) {
        this.permissionCallback = callback;
        return this;
    }

    public GPermission withRefuseTips(String tips) {
        this.refuseTips = tips;
        return this;
    }

    /**
     * 请求权限
     */
    public void requestPermission() {
        if (permissions == null || permissions.length <= 0) {
            return;
        }
        PermissionActivity.request(context, permissions, permissionCallback);
    }

    /**
     * Jump to Settings page of your application
     * @param context
     */
    public static void startSettingsActivity(Context context) {
        Uri packageURI = Uri.parse("package:" + context.getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
