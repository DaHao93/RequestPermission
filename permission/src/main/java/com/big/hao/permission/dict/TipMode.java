package com.big.hao.permission.dict;


/**
 * Created by Hao on 2021/4/22
 */
public enum TipMode {

    None(0),
    Dialog(1),
    Toast(2);

    private final int value;

    private TipMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
