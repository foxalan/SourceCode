package com.alan.tfive_ui.activity.permission;

public interface PermissionCallback {
    /**
     * 权限允许
     */
    void onPermissionGranted();

    /**
     * 拒绝
     * @param permission
     */
    void onPermissionRefuse(String permission);

    /**
     * 禁止
     * @param permission
     */
    void onPermissionReject(String permission);
}
