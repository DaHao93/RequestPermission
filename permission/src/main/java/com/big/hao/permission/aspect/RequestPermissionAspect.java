package com.big.hao.permission.aspect;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.big.hao.permission.dict.TipMode;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Hao on 2021/4/22
 */
@Aspect
public class RequestPermissionAspect {

    @Pointcut("execution(@com.big.hao.permission.aspect.RequestPermissions * *(..))")
    public void methodPermissions() {

    }

    @Around("methodPermissions()")
    public void requestPermission(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequestPermissions annotation = method.getAnnotation(RequestPermissions.class);

        String[] permissions = annotation.value();
        boolean execWhenRejected = annotation.execWhenRejected();
        TipMode tipMode = annotation.tipMode();

        Object object = joinPoint.getThis();

        FragmentActivity activity = null;
        if (object instanceof Fragment) {
            activity = ((Fragment) object).getActivity();
        } else if (object instanceof Activity) {
            activity = (FragmentActivity) object;
        }
        if (activity == null) {
            return;
        }
        ArrayList<String> afterCheckPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(activity, permission)) {
                afterCheckPermissions.add(permission);
            }
        }
        if (afterCheckPermissions.isEmpty()) {
            joinPoint.proceed();
            return;
        }
        getFragment(activity, tipMode).requestPermissions(new RequestListener() {
            @Override
            public void allow() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void refuse(String[] permissions) {
                try {
                    if (execWhenRejected) {
                        joinPoint.proceed();
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }, afterCheckPermissions.toArray(new String[0]));
    }

    private RequestFragment getFragment(FragmentActivity activity, TipMode tipMode) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment existedFragment = fragmentManager.findFragmentByTag("RequestFragment");
        if (existedFragment != null) {
            RequestFragment requestFragment = (RequestFragment) existedFragment;
            requestFragment.setTipMode(tipMode);
            return requestFragment;
        } else {
            RequestFragment invisibleFragment = new RequestFragment();
            invisibleFragment.setTipMode(tipMode);
            fragmentManager.beginTransaction().add(invisibleFragment, "RequestFragment").commitNow();
            return invisibleFragment;
        }
    }

}
