
package be.uclouvain.lsinf1225.v.bartender.gui;
import android.app.Activity;
import android.os.Bundle;

import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Calendar;
import java.text.Format;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoPlots;


public class ChiffreAffaireActivity extends Activity
{
    List<Double> getTurnoverInRange;
    private XYPlot plot;
    Calendar calendar1 = new GregorianCalendar();
    Calendar calendar2 = new GregorianCalendar();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        calendar1.add(Calendar.DAY_OF_MONTH, -19);

        getTurnoverInRange = DaoPlots.getTurnoverInRange(calendar1,calendar2);


        super.onCreate(savedInstanceState);

        setContentView(R.layout.chiffre_affaire_activity);

        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);


        Number[] series1Numbers = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        int i=0;
        for(Double price : getTurnoverInRange){
            series1Numbers[i] = price;
            i++;

        }

        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                getString(R.string.affair_profit));

        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_plf1);

        plot.addSeries(series1, series1Format);

        plot.setTicksPerRangeLabel(4);
        plot.setTicksPerDomainLabel(10);
        plot.setDomainLabel("Dates");
        plot.setRangeLabel("euros");
        plot.getGraphWidget().setDomainLabelOrientation(-45);
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1); plot.setDomainValueFormat(new DecimalFormat("#"));

    }
}