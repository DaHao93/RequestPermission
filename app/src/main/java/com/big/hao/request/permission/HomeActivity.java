package com.big.hao.request.permission;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.big.hao.permission.aspect.RequestPermissions;
import com.big.hao.permission.dict.TipMode;

/**
 * Created by Hao on 2021/4/22
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.tv_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }


    @RequestPermissions(value = {Manifest.permission.ACCESS_COARSE_LOCATION}, tipMode = TipMode.Toast)
    private void check() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
