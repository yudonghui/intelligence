package com.ydh.intelligence.activitys;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.ydh.intelligence.R;
import com.ydh.intelligence.common.mpchart.CallCountFormatter;
import com.ydh.intelligence.common.mpchart.CallCountValueFormatter;
import com.ydh.intelligence.common.mpchart.DecimalFormatter;
import com.ydh.intelligence.common.mpchart.MyAxisValueFormatter;
import com.ydh.intelligence.common.mpchart.RadarChartFormatter;
import com.ydh.intelligence.common.mpchart.RadarMarkerView;
import com.ydh.intelligence.common.mpchart.XAxisValueFormatter;
import com.ydh.intelligence.views.MyMarkerView;
import com.ydh.intelligence.views.XYMarkerView;

import java.util.ArrayList;
import java.util.List;

public class MpAndroidChartActivity extends BaseActivity implements OnChartValueSelectedListener {
    private BarChart yearBarChart;
    private BarChart mBarChart;
    private HorizontalBarChart hBarChart;
    private RadarChart radarChart;
    private LineChart lineChart;
    private LineChart lineChart2;
    private LineChart lineChart3;
    private PieChart mPieChart;
   // private Typeface tf;

    protected final String[] parties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp_android_chart);
        initYearBarChart();
        initmBarChar();
        inithBarChart();
        initRadarChart();
        initLineChart();
        initLineChart2();
        initLineChart3();
        initmPieChart();
    }
    private void initmPieChart() {
        mPieChart = findViewById(R.id.mPieChart);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);

       // tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        //mPieChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        mPieChart.setCenterText(generateCenterSpannableText());

        mPieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" ???");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        mPieChart.setOnChartValueSelectedListener(this);

//        seekBarX.setProgress(4);
//        seekBarY.setProgress(100);

        initPieChartData(6, 100);

        mPieChart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }

    private void initPieChartData(int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) (Math.random() * range) + range / 5, parties[i % parties.length]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
       // data.setValueTypeface(tf);
        mPieChart.setData(data);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();
    }

    private void initLineChart3() {
        lineChart3 = findViewById(R.id.lineChart3);
        lineChart3.setViewPortOffsets(0, 0, 0, 0);
        lineChart3.setBackgroundColor(Color.rgb(104, 241, 175));

        // no description text
        lineChart3.getDescription().setEnabled(false);

        // enable touch gestures
        lineChart3.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart3.setDragEnabled(true);
        lineChart3.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart3.setPinchZoom(false);

        lineChart3.setDrawGridBackground(false);
        lineChart3.setMaxHighlightDistance(300);

        XAxis x = lineChart3.getXAxis();
        x.setEnabled(false);

        YAxis y = lineChart3.getAxisLeft();
//        y.setTypeface(tfLight);
        y.setLabelCount(6, false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);

        lineChart3.getAxisRight().setEnabled(false);

        // add data


        lineChart3.getLegend().setEnabled(false);

        lineChart3.animateXY(2000, 2000);

        setLinChartData3();

        // don't forget to refresh the drawing
        lineChart3.invalidate();
    }

    private void setLinChartData3() {
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            float val = (float) (Math.random() * (30 + 1)) + 20;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (lineChart3.getData() != null &&
                lineChart3.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart3.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart3.getData().notifyDataChanged();
            lineChart3.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return lineChart3.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData(set1);
//            data.setValueTypeface(tfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            lineChart3.setData(data);
        }
    }

    private void initLineChart2() {
        lineChart2 = findViewById(R.id.lineChart2);

        // background color
        lineChart2.setBackgroundColor(Color.WHITE);

        // disable description text
        lineChart2.getDescription().setEnabled(false);

        // enable touch gestures
        lineChart2.setTouchEnabled(true);

        // set listeners
        lineChart2.setOnChartValueSelectedListener(this);
        lineChart2.setDrawGridBackground(false);

        // create marker to display box when values are selected
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // Set the marker to the chart
        mv.setChartView(lineChart2);
        lineChart2.setMarker(mv);

        // enable scaling and dragging
        lineChart2.setDragEnabled(true);
        lineChart2.setScaleEnabled(true);

        lineChart2.setPinchZoom(true);

        XAxis xAxis = lineChart2.getXAxis();
        // vertical grid lines
        xAxis.enableGridDashedLine(10f, 10f, 0f);

        YAxis yAxis = lineChart2.getAxisLeft();
        // disable dual axis (only use LEFT axis)
        lineChart2.getAxisRight().setEnabled(false);

        // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 10f, 0f);

        // axis range
        yAxis.setAxisMaximum(200f);
        yAxis.setAxisMinimum(-50f);

        LimitLine llXAxis = new LimitLine(9f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);
//        llXAxis.setTypeface(tfRegular);

        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
//        ll1.setTypeface(tfRegular);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
//        ll2.setTypeface(tfRegular);

        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(true);
        xAxis.setDrawLimitLinesBehindData(true);

        // add limit lines
        yAxis.addLimitLine(ll1);
        yAxis.addLimitLine(ll2);
        //xAxis.addLimitLine(llXAxis);


        setLineChart1Data(45, 180);

        // draw points over time
        lineChart2.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = lineChart2.getLegend();

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);

    }

    private void setLineChart1Data(int count, float range) {
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) - 30;
            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.shape_black_15)));
        }

        LineDataSet set1;

        if (lineChart2.getData() != null &&
                lineChart2.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart2.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            lineChart2.getData().notifyDataChanged();
            lineChart2.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return lineChart2.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            lineChart2.setData(data);
        }
    }

    private void initLineChart() {
        lineChart = findViewById(R.id.lineChart);
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.getDescription().setEnabled(false);
        lineChart.setBackgroundColor(Color.WHITE);

        //??????????????????????????????X???
//        IAxisValueFormatter xAxisFormatter = new XAxisValueFormatter();
        XAxisValueFormatter xAxisFormatter = new XAxisValueFormatter();

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(xAxisFormatter);


        MyAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = lineChart.getAxisLeft();

        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);

        lineChart.getAxisRight().setEnabled(false);

        setLineChartData();


        lineChart.animateY(3000);//????????????
    }

    private void setLineChartData() {
        //????????????????????????????????????????????????
        List<Entry> valsComp1 = new ArrayList<>();
        List<Entry> valsComp2 = new ArrayList<>();

        valsComp1.add(new Entry(0, 2));
        valsComp1.add(new Entry(1, 4));
        valsComp1.add(new Entry(2, 0));
        valsComp1.add(new Entry(3, 2));
        valsComp1.add(new Entry(4, 10));
        valsComp1.add(new Entry(5, 7));
        valsComp1.add(new Entry(6, 5));
        valsComp1.add(new Entry(7, 2));

        //??????????????????new??????LineDataSet?????????????????????????????????
        //?????????LineDataSet??????????????????????????????:???????????????LineDataSet???setComp1???setComp2???
        //?????????????????????????????????????????????????????????1 ??? ??????2 ?????????.?????????????????????
        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1 ");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColor(Color.BLUE);
        setComp1.setDrawCircles(false);
        setComp1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

//        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2 ");
//        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
//        setComp2.setDrawCircles(true);
//        setComp2.setColor(getResources().getColor(R.color.colorAccent));
//        setComp2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
//        dataSets.add(setComp2);

        LineData lineData = new LineData(dataSets);

        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void initRadarChart() {
        radarChart = findViewById(R.id.radarChart);

        radarChart.setBackgroundColor(Color.rgb(60, 65, 82));
        radarChart.getDescription().setEnabled(false);

        radarChart.setWebLineWidth(1f);
        radarChart.setWebColor(Color.LTGRAY);
        radarChart.setWebLineWidthInner(1f);
        radarChart.setWebColorInner(Color.LTGRAY);
        radarChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
        mv.setChartView(radarChart); // For bounds control
        radarChart.setMarker(mv); // Set the marker to the chart

        setRadarChartData();

        radarChart.animateXY(1400, 1400, Easing.EaseInOutQuad);

        XAxis xAxis = radarChart.getXAxis();
//        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new RadarChartFormatter());

        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = radarChart.getYAxis();
//        yAxis.setTypeface(tfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = radarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setTypeface(tfLight);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);

    }

    private void setRadarChartData() {
        float mul = 80;
        float min = 20;
        int cnt = 5;

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mul) + min;
            entries1.add(new RadarEntry(val1));

            float val2 = (float) (Math.random() * mul) + min;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Last Week");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "This Week");
        set2.setColor(Color.rgb(121, 162, 175));
        set2.setFillColor(Color.rgb(121, 162, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
//        data.setValueTypeface(tfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        radarChart.setData(data);
        radarChart.invalidate();
    }


    private void initmBarChar() {
        mBarChart = (BarChart) findViewById(R.id.mBarChart);

        //????????????????????????????????????????????????
        mBarChart.setOnChartValueSelectedListener(this);
        //??????????????????
        mBarChart.setDrawBarShadow(false);
        //???????????????Value??????????????????????????? true ????????????????????????false value???????????????????????????
        mBarChart.setDrawValueAboveBar(true);
        //Description Label ????????????
        mBarChart.getDescription().setEnabled(false);
        // ??????????????????Value???????????? ?????????ValueFormartter?????????
        mBarChart.setMaxVisibleValueCount(60);
        // ????????????X???Y???????????????
        mBarChart.setPinchZoom(false);
        //??????????????????????????????
        mBarChart.setDrawGridBackground(false);
        //??????X???????????????????????????-60?????????????????????60???
        mBarChart.getXAxis().setLabelRotationAngle(-30);


        XAxis xAxis = mBarChart.getXAxis();
        //??????X???????????????
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //X??????????????????????????????????????????
        xAxis.setDrawGridLines(false);
        // X?????????Value???????????????????????????X????????????Value???????????????
        xAxis.setGranularity(1f);
        //X???????????????????????????
//        xAxis.setLabelCount(7);
//        //X???????????????
//        xAxis.setAxisMaximum(6f);
//        //X???????????????
//        xAxis.setAxisMinimum(0.5f);

        //?????????X???
        ValueFormatter xAxisFormatter = new CallCountFormatter(mBarChart);
        xAxis.setValueFormatter(xAxisFormatter);


        //???????????????????????????????????????
        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mBarChart);
        mBarChart.setMarker(mv);

        //Y?????????
        YAxis leftAxis = mBarChart.getAxisLeft();

        //??????Y????????????????????? label ??????
        leftAxis.setLabelCount(8, false);
        //?????????????????????????????????????????????????????????Y?????????
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //??????Y??? ????????????????????? ????????????30f??????30%??????????????????10%
        leftAxis.setSpaceTop(30f);
        //??????Y??????????????????????????????
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(80f);

        //??????????????????Y???
        mBarChart.getAxisRight().setEnabled(false);


        // ???????????????????????????
        Legend l = mBarChart.getLegend();
        //??????????????????????????????
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //??????????????????
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //??????????????????
        l.setDrawInside(false);
        //??????
        l.setForm(Legend.LegendForm.SQUARE);
        //??????
        l.setFormSize(9f);
        //??????
        l.setTextSize(11f);

        //????????????
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(1f, 10, "??????"));
        yVals1.add(new BarEntry(2f, 20, "qq"));
        yVals1.add(new BarEntry(3f, 30, "??????"));
        yVals1.add(new BarEntry(4f, 40, "??????"));
        yVals1.add(new BarEntry(5f, 50, "?????????"));
        yVals1.add(new BarEntry(6f, 60, "?????????"));

        setData(yVals1);

        mBarChart.animateY(3000);//????????????
    }


    private void setData(ArrayList yVals1) {
        float start = 1f;
        BarDataSet set1;
        if (mBarChart.getData() != null &&
                mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "????????????????????????");
            //?????????????????????
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.6f);
            data.setValueFormatter(new CallCountValueFormatter());
            //????????????
            mBarChart.setData(data);
        }
    }

    //????????????????????????
    private void inithBarChart() {
        hBarChart = findViewById(R.id.hBarChart);

        hBarChart.setOnChartValueSelectedListener(this);
        hBarChart.setDrawBarShadow(false);
        hBarChart.setDrawValueAboveBar(true);
        hBarChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        hBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        hBarChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        hBarChart.setDrawGridBackground(false);


        //???????????????????????????????????????X???
        DecimalFormatter formatter = new DecimalFormatter();
        XAxis xl = hBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setLabelRotationAngle(-45f);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(1f);
//        xl.setAxisMinimum(0);
        xl.setValueFormatter(formatter);

        //???Y???????????????
        YAxis yl = hBarChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        hBarChart.getAxisRight().setEnabled(false);

        //????????????
        Legend l = hBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

        setHBarChartData();
        hBarChart.setFitBars(true);
        hBarChart.animateY(3000);//????????????

    }

    private void setHBarChartData() {
        //????????????????????????????????????????????????
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        yVals1.add(new BarEntry(0, 4));
        yVals1.add(new BarEntry(1, 2));
        yVals1.add(new BarEntry(2, 6));
        yVals1.add(new BarEntry(3, 1));
        BarDataSet set1;

        if (hBarChart.getData() != null &&
                hBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) hBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            hBarChart.getData().notifyDataChanged();
            hBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "?????????????????????");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);

            data.setBarWidth(0.5f);

            hBarChart.setData(data);
        }
    }


    //??????????????????????????????
    private void initYearBarChart() {
        //?????????
        yearBarChart = (BarChart) findViewById(R.id.yearBarChart);
        yearBarChart.getDescription().setEnabled(false);

        //????????????????????????????????????????????????
        yearBarChart.setMaxVisibleValueCount(60);
        //?????????x??????y??????????????????
        yearBarChart.setPinchZoom(true);
        //??????????????????????????????
        yearBarChart.setDrawBarShadow(false);
        //??????????????????
        yearBarChart.setDrawGridBackground(true);
        //?????????????????????x??????????????????2,015 ???????????????bug
//        ValueFormatter custom = new MyValueFormatter("");
        //??????x??????
        XAxis xAxis = yearBarChart.getXAxis();

        //??????x??????????????????
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //??????????????????
        xAxis.setDrawGridLines(true);
        //???????????????????????????????????????????????????????????????????????????????????????
        xAxis.setAvoidFirstLastClipping(false);
        //????????????  ???x????????????????????? ??????true
        xAxis.setDrawLabels(true);
        //??????X??????????????????
        xAxis.setTextSize(12);

//        xAxis.setValueFormatter(custom);
        //?????????x ?????????????????????
        xAxis.setGranularityEnabled(true);
        //????????????y??????
        YAxis axisRight = yearBarChart.getAxisRight();

        axisRight.setAxisMinimum(0f);


        //????????????y????????????
        YAxis axisLeft = yearBarChart.getAxisLeft();
        //??????Y????????? ????????????
        axisLeft.setAxisMinimum(0f);
        yearBarChart.getAxisLeft().setDrawGridLines(false);
        //?????????????????????X??????Y???????????????
//        mBarChart.animateXY(5000, 5000);
        //?????????X???????????????
        yearBarChart.animateY(5000);//????????????

        yearBarChart.getLegend().setEnabled(true);

        getYearBarData();
        //??????????????????  ?????????????????????
        yearBarChart.getData().setValueTextSize(10);

        for (IDataSet set : yearBarChart.getData().getDataSets()) {
            set.setDrawValues(!set.isDrawValuesEnabled());
        }
    }

    private void getYearBarData() {
        ArrayList<BarEntry> values = new ArrayList<>();
        BarEntry barEntry = new BarEntry(Float.valueOf("2016"), Float.valueOf("290"));
        BarEntry barEntry1 = new BarEntry(Float.valueOf("2017"), Float.valueOf("210"));
        BarEntry barEntry2 = new BarEntry(Float.valueOf("2018"), Float.valueOf("300"));
        BarEntry barEntry3 = new BarEntry(Float.valueOf("2019"), Float.valueOf("450"));
        BarEntry barEntry4 = new BarEntry(Float.valueOf("2020"), Float.valueOf("300"));
        BarEntry barEntry5 = new BarEntry(Float.valueOf("2021"), Float.valueOf("650"));
        BarEntry barEntry6 = new BarEntry(Float.valueOf("2022"), Float.valueOf("740"));
        BarEntry barEntry7 = new BarEntry(Float.valueOf("2023"), Float.valueOf("240"));

        values.add(barEntry);
        values.add(barEntry1);
        values.add(barEntry2);
        values.add(barEntry3);
        values.add(barEntry4);
        values.add(barEntry5);
        values.add(barEntry6);
        values.add(barEntry7);

        BarDataSet set1;

        if (yearBarChart.getData() != null &&
                yearBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) yearBarChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            yearBarChart.getData().notifyDataChanged();
            yearBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "?????????");
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawValues(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            yearBarChart.setData(data);
            yearBarChart.setFitBars(true);
        }

        //????????????
        yearBarChart.invalidate();

    }




    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.5f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.65f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

}