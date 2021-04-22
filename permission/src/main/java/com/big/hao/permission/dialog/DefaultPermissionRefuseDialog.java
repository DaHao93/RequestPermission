package com.big.hao.permission.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.big.hao.permission.R;

/**
 * Created by Hao on 2021/4/22
 * 权限拒绝弹窗
 */
public class DefaultPermissionRefuseDialog extends Dialog {

    protected String[] permissions;
    private onDefaultPermissionRefuseListener listener;

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }


    public DefaultPermissionRefuseDialog(@NonNull Context context, onDefaultPermissionRefuseListener listener) {
        super(context, R.style.Dialog);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.dialog_default_permission_refuse, null);
        setContentView(contentView);
        setCanceledOnTouchOutside(false);

        TextView cancelBtn = contentView.findViewById(R.id.tv_permission_refuse_cancel);
        TextView settingBtn = contentView.findViewById(R.id.tv_permission_refuse_setting);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onCancel(DefaultPermissionRefuseDialog.this);
                }
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onSetting(DefaultPermissionRefuseDialog.this);
                }
            }
        });
    }

    public interface onDefaultPermissionRefuseListener {
        void onCancel(Dialog dialog);

        void onSetting(Dialog dialog);
    }

    @Override
    public void onBackPressed() {
    }
}
