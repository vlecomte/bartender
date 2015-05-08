package be.uclouvain.lsinf1225.v.bartender.gui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import android.util.Pair;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoPlots;
import be.uclouvain.lsinf1225.v.bartender.model.Product;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import be.uclouvain.lsinf1225.v.bartender.model.Product;

public class WaiterPlotActivity extends Activity
{

    private TextView donutSizeTextView;
    private SeekBar donutSizeSeekBar;
    private int[] couleur = {Color.BLUE, Color.RED, Color.GRAY, Color.GREEN, Color.CYAN, Color.DKGRAY, Color.YELLOW, Color.MAGENTA, Color.WHITE};
    private EmbossMaskFilter emf;

    private PieChart pie;
    private List<Pair<String, Integer>> list;

    Calendar calendar1 = new GregorianCalendar();
    Calendar calendar2 = new GregorianCalendar();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_plot);

        calendar1.add(Calendar.DAY_OF_MONTH, -15);
        updateTab();

        pie = (PieChart) findViewById(R.id.barak2);

        emf = new EmbossMaskFilter(
                new float[]{1, 1, 1}, 0.4f, 10, 8.2f);

        updatePie();

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
        PieRenderer prend = pie.getRenderer(PieRenderer.class);
        prend.setDonutSize((float) 0/100, PieRenderer.DonutMode.PERCENT);
    }

    protected void updateDonutText() {
        donutSizeTextView.setText(donutSizeSeekBar.getProgress() + "%");
    }

    private void updateTab(){
        list = DaoPlots.getWaiterByServices(calendar1,calendar2);
    }

    private void updatePie(){
        int i=1;
        for(Pair<String, Integer> entry : list){
            Segment seg = new Segment(entry.first + "["+entry.second+"]",entry.second);
            SegmentFormatter sf = new SegmentFormatter(couleur[i]);
            sf.getLabelPaint().setColor(Color.BLACK);
            sf.getFillPaint().setMaskFilter(emf);
            pie.addSeries(seg,sf);
            i++;
        }
    }
}