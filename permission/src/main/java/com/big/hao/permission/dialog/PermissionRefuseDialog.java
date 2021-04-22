package com.big.hao.permission.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Created by Hao on 2021/4/22
 */
public class PermissionRefuseDialog extends Dialog {

    protected String[] permissions;

    public PermissionRefuseDialog(@NonNull Context context) {
        super(context);
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }
}
