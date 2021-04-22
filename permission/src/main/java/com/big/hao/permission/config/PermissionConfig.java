package com.big.hao.permission.config;

import com.big.hao.permission.dialog.PermissionRefuseDialog;

/**
 * Created by Hao on 2021/4/22
 */
public class PermissionConfig {

    private static PermissionRefuseDialog refuseDialog;


    public static PermissionRefuseDialog getRefuseDialog() {
        return refuseDialog;
    }

    public static void setRefuseDialog(PermissionRefuseDialog refuseDialog) {
        PermissionConfig.refuseDialog = refuseDialog;
    }
}
