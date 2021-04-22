package com.big.hao.permission.aspect;

import com.big.hao.permission.dict.TipMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Hao on 2021/4/22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestPermissions {
    String[] value();

    boolean execWhenRejected() default false;

    TipMode tipMode() default TipMode.Dialog;
}
