package com.zcs.android.androidcharts.charts.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZengCS on 2017/9/26.
 * E-mail:zcs@sxw.cn
 * Add:成都市天府软件园E3-3F
 */

public class ChartBean implements Serializable {
    private String xTag = "x";
    private String yTag = "y";
    private int rowCount = 0;
    private int columnCount = 0;
    private List<Integer> values;

    public String getxTag() {
        return xTag;
    }

    public void setxTag(String xTag) {
        this.xTag = xTag;
    }

    public String getyTag() {
        return yTag;
    }

    public void setyTag(String yTag) {
        this.yTag = yTag;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }
}
