package com.zcs.android.androidcharts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.zcs.android.androidcharts.charts.bean.ChartBean;
import com.zcs.android.androidcharts.charts.widget.AndroidLineChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LineChartActivity extends AppCompatActivity {
    private AndroidLineChart mLineChart;
    private CheckBox mCheckBox1;
    private CheckBox mCheckBox2;
    private CheckBox mCheckBox3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        // initViews
        mCheckBox1 = findViewById(R.id.id_cb_1);
        mCheckBox2 = findViewById(R.id.id_cb_2);
        mCheckBox3 = findViewById(R.id.id_cb_3);

        mCheckBox1.setOnCheckedChangeListener(this::onCheckedChanged);
        mCheckBox2.setOnCheckedChangeListener(this::onCheckedChanged);
        mCheckBox3.setOnCheckedChangeListener(this::onCheckedChanged);

        findViewById(R.id.id_btn_refresh).setOnClickListener(this::onClick);

        mLineChart = findViewById(R.id.id_line_chart);
        invalidateChart();
    }

    private void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.id_cb_1:
                mLineChart.withVal(b);
                mLineChart.invalidate();
                break;
            case R.id.id_cb_2:
                mLineChart.withCircle(b);
                mLineChart.invalidate();
                break;
            case R.id.id_cb_3:
                mLineChart.withShadow(b);
                mLineChart.invalidate();
                break;
        }
    }

    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_refresh:
                invalidateChart();
                break;
        }
    }

    private void invalidateChart() {
        ChartBean bean = new ChartBean();
        bean.setxTag("(分钟)");
        bean.setyTag("(人数)");
        Random random = new Random();
        int rowCount = random.nextInt(7) + 3;
        int columnCount = random.nextInt(6) + rowCount + 3;
        bean.setRowCount(rowCount);
        bean.setColumnCount(columnCount);

        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < columnCount; i++) {
            if (i == 0) {
                values.add(0);
            } else {
                values.add((int) (random.nextFloat() * 10 * rowCount));
            }
        }
        bean.setValues(values);

        mLineChart.withVal(mCheckBox1.isChecked())// 是否展示具体的值
                .withCircle(mCheckBox2.isChecked())// 是否展示圆点
                .withShadow(mCheckBox3.isChecked())// 是否展示阴影
                .bindChartData(bean);// 绑定数据
    }
}
