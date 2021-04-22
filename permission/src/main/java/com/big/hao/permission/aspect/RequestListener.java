package com.big.hao.permission.aspect;

/**
 * Created by Hao on 2021/4/22
 */
public interface RequestListener {
    void allow();
    void refuse(String[] permissions);
}
