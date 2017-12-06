package com.zcs.android.androidcharts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.id_btn_line_chart).setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_line_chart:
                startActivity(new Intent(this, LineChartActivity.class));
                break;
        }
    }
}
