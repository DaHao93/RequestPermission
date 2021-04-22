package com.big.hao.permission.aspect;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.big.hao.permission.config.PermissionConfig;
import com.big.hao.permission.dialog.DefaultPermissionRefuseDialog;
import com.big.hao.permission.dialog.PermissionRefuseDialog;
import com.big.hao.permission.dict.TipMode;

import java.util.ArrayList;

/**
 * Created by Hao on 2021/4/22
 */
public class RequestFragment extends Fragment {

    private RequestListener mRequestListener;
    TipMode tipMode;

    public RequestFragment() {
    }

    public void setTipMode(TipMode tipMode) {
        this.tipMode = tipMode;
    }

    public void requestPermissions(RequestListener requestListener, String[] permissions) {
        this.mRequestListener = requestListener;
        requestPermissions(permissions, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        ArrayList<String> deniedArray = new ArrayList<>();

        for (int i = 0; i < grantResults.length; i++) {
            if (PackageManager.PERMISSION_DENIED == grantResults[i]) {
                deniedArray.add(permissions[i]);
            }
        }
        if (null != mRequestListener) {
            if (deniedArray.isEmpty()) {
                mRequestListener.allow();
            } else {
                if (tipMode == TipMode.None) {
                    mRequestListener.refuse(deniedArray.toArray(new String[0]));
                } else if (tipMode == TipMode.Dialog) {
                    showRefuseDialog(deniedArray.toArray(new String[0]));
                } else if (tipMode == TipMode.Toast) {
                    showToast();
                }
            }
        }
    }

    private void showRefuseDialog(String[] permissions) {
        PermissionRefuseDialog refuseDialog = PermissionConfig.getRefuseDialog();
        if (null != refuseDialog) {
            refuseDialog.setPermissions(permissions);
            refuseDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mRequestListener.refuse(permissions);
                }
            });
            refuseDialog.show();
        } else {
            if (null != getContext()) {
                DefaultPermissionRefuseDialog dialog = new DefaultPermissionRefuseDialog(getContext(), new DefaultPermissionRefuseDialog.onDefaultPermissionRefuseListener() {
                    @Override
                    public void onCancel(Dialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onSetting(Dialog dialog) {
                        try {
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            Uri uri = Uri.fromParts("package", getActivity().getApplication().getPackageName(), (String) null);
                            intent.setData(uri);
                            getActivity().startActivityForResult(intent, 29511);
                        } catch (Exception e) {

                        }
                    }
                });
                dialog.setPermissions(permissions);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mRequestListener.refuse(permissions);
                    }
                });
                dialog.show();
            } else {
                mRequestListener.refuse(permissions);
            }
        }
    }

    private void showToast() {
        Toast.makeText(getContext(), "您已禁止授予相关权限，可能会造成部分功能不可用,请到设置中打开相关权限", Toast.LENGTH_SHORT).show();
    }
}
