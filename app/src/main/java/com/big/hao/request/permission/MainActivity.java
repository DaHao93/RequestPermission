package com.big.hao.request.permission;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.big.hao.permission.aspect.RequestPermissions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }

    @RequestPermissions(value = {Manifest.permission.CAMERA})
    private void check() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}