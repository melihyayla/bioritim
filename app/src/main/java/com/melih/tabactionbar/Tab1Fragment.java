package com.melih.tabactionbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Years;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    static LineChart lineChart;
    Button button;

    ImageView expandView, expandView2;
    LinearLayout linearLayout, tagLinearLayout, weeklylayout , choosePerson , weeklylinear;
    Boolean check = true, check2 =true;
    TextView zodiacText, phyText, emoText, intText, sezText, usersnameText , phyTextw, emoTextw, intTextw, sezTextw ;
    ImageView phyImage, emoImage, intImage, sezImage, zodiacImage , phyImagew, emoImagew, intImagew, sezImagew;
    ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
    int i = 0;
    DatabaseHelper databaseHelper;
    ArrayList<HashMap<String, String>> users;
    static ArrayList<String> userslist;
    static ArrayAdapter<String> arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout,container,false);

        databaseHelper = new DatabaseHelper(this.getContext());

        phyText = view.findViewById(R.id.physicalText);
        emoText = view.findViewById(R.id.emotionalText);
        intText = view.findViewById(R.id.intellectualText);
        sezText = view.findViewById(R.id.intuitiveText);

        phyImage = view.findViewById(R.id.physical);
        emoImage = view.findViewById(R.id.emotional);
        intImage = view.findViewById(R.id.intellectual);
        sezImage = view.findViewById(R.id.intuitive);
        zodiacImage = view.findViewById(R.id.zodiacImage);



        phyTextw = view.findViewById(R.id.physicalTextw);
        emoTextw = view.findViewById(R.id.emotionalTextw);
        intTextw = view.findViewById(R.id.intellectualTextw);
        sezTextw = view.findViewById(R.id.intuitiveTextw);

        phyImagew = view.findViewById(R.id.physicalw);
        emoImagew = view.findViewById(R.id.emotionalw);
        intImagew = view.findViewById(R.id.intellectualw);
        sezImagew = view.findViewById(R.id.intuitivew);

        lineChart =  view.findViewById(R.id.lineChart);
        linearLayout =  view.findViewById(R.id.faceImageLinear);
        tagLinearLayout = view.findViewById(R.id.tagLinearLayout);
        weeklylinear = view.findViewById(R.id.weeklyLinear);
        choosePerson = view.findViewById(R.id.choosePersonLayout);
        weeklylayout = view.findViewById(R.id.weeklyLayout);
        expandView  = view.findViewById(R.id.expandView);
        expandView2 = view.findViewById(R.id.expandView2);
        zodiacText =  view.findViewById(R.id.zodiacText);
        usersnameText = view.findViewById(R.id.choosePersonTextView);

        Date date1 = new Date(1993,6,14);

        String zodiac = createZodiacText(date1);

        zodiacText.setText(zodiac);
        zodiacText.setTextColor(Color.WHITE);
        zodiacText.setTextSize(16);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            zodiacText.setLineSpacing(1f,1.25f);
        }
        zodiacText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        zodiacText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        tagLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check) {
                    expand(linearLayout);
                    expandView.setRotation(180);
                }
                else{
                    collapse(linearLayout);
                    expandView.setRotation(0);
                }
                check = !check;
            }
        });

        weeklylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check2) {
                    expand(weeklylinear);
                    expandView2.setRotation(180);
                }
                else{
                    collapse(weeklylinear);
                    expandView2.setRotation(0);
                }
                check2 = !check2;
            }
        });



        choosePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogBox();
            }
        });

        createFragment1items(date1);

        createZodiacText(date1);

        return view;
    }

    public String createZodiacText(Date dateofBirth){

        Calendar c = Calendar.getInstance();
        c.setTime(dateofBirth);
        c.add(Calendar.HOUR, 3);
        //c.add(Calendar.YEAR, -1900);
        dateofBirth = c.getTime();

        Date date2 = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy", new Locale("tr"));

        DateTime  datex = new DateTime(dateofBirth);
        DateTime  datey = new DateTime(date2);

        int years =  Years.yearsBetween(datex,datey).getYears();

        c.setTime(dateofBirth);
        c.add(Calendar.YEAR, years+1);

        Date nextYear = c.getTime();

        Date thisYear = nextYear;

        Log.i("Date Next Year ", dateFormat.format(nextYear));

        DateTime  nextYearD = new DateTime(nextYear);

        int monthsRem =  Months.monthsBetween(datey,nextYearD).getMonths();

        c.add(Calendar.MONTH, -monthsRem);

        nextYear = c.getTime();

        nextYearD = new DateTime(nextYear);

        int daysRem = Days.daysBetween(datey,nextYearD).getDays();

        c.setTime(thisYear);
        c.add(Calendar.YEAR,-1);
        thisYear = c.getTime();

        DateTime thisYearD = new DateTime(thisYear);

        int monthsD =  Months.monthsBetween(thisYearD,datey).getMonths();

        c.add(Calendar.MONTH, monthsD);

        thisYear = c.getTime();

        thisYearD = new DateTime(thisYear);

        int daysD = Days.daysBetween(thisYearD,datey).getDays();


        String x  = String.valueOf(years) + " yıl, "+String.valueOf(monthsD) + " ay, "+ String.valueOf(daysD) + " gün yaşadın. "
                + "Sonraki doğum gününe: "+ String.valueOf(monthsRem) + " ay, "+ String.valueOf(daysRem) + " gün kaldı";

        return x;
    }

    public void addNewLine(Date newDate, int cycle, String name, int myColor){

        if (i > 3) {
            lineDataSets = new ArrayList<>();
            i=0;
        }

        i++;

        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.HOUR, 3);
        //c.add(Calendar.YEAR, -1900);
        currentDate = c.getTime();

        long diffInMillies = Math.abs(currentDate.getTime()- newDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        DateFormat dateFormat = new SimpleDateFormat("dd MMMM", new Locale("tr"));



        double [] arr = new double[19];
        final String [] dates = new String[19];

        Log.i("DATE: ", dateFormat.format(currentDate));

        ArrayList<Entry> entries = new ArrayList<>();

        for(int i = 0 ; i<arr.length ; i++){

            arr[i] = (Math.sin(2*Math.PI*((int)diff + i)/cycle)*100);

            entries.add(new Entry(i, (float) arr[i]));

            dates[i]=dateFormat.format(currentDate);

            Log.i("DATE: ", i + " " + dateFormat.format(currentDate));

            c.setTime(currentDate);
            c.add(Calendar.DATE, 1);
            currentDate = c.getTime();

        }

        int weeklyValue = 0 ;

        for(int i=0; i<7 ; i++){
            weeklyValue += arr[i];
        }

        weeklyValue = weeklyValue/7;

        Log.i("WEEKLY VALUE: ", i + " " + weeklyValue);


        Drawable good = getResources().getDrawable(R.drawable.good);
        Drawable bad = getResources().getDrawable(R.drawable.bad);
        Drawable normal = getResources().getDrawable(R.drawable.normal);


        if(cycle==23){
            phyText.setText(String.valueOf(Math.round(arr[0])));
            if(Math.round(arr[0])>=50)
                phyImage.setImageDrawable(good);
            else if(Math.round(arr[0])<= -50)
                phyImage.setImageDrawable(bad);
            else
                phyImage.setImageDrawable(normal);

            phyTextw.setText(String.valueOf(Math.round(weeklyValue)));
            if(Math.round(weeklyValue)>=50)
                phyImagew.setImageDrawable(good);
            else if(Math.round(weeklyValue)<= -50)
                phyImagew.setImageDrawable(bad);
            else
                phyImagew.setImageDrawable(normal);


        }
        else if (cycle==28) {
            emoText.setText(String.valueOf(Math.round(arr[0])));
            if(Math.round(arr[0])>=50)
                emoImage.setImageDrawable(good);
            else if(Math.round(arr[0])<= -50)
                emoImage.setImageDrawable(bad);
            else
                emoImage.setImageDrawable(normal);


            emoTextw.setText(String.valueOf(Math.round(weeklyValue)));
            if(Math.round(weeklyValue)>=50)
                emoImagew.setImageDrawable(good);
            else if(Math.round(weeklyValue)<= -50)
                emoImagew.setImageDrawable(bad);
            else
                emoImagew.setImageDrawable(normal);
        }
        else if (cycle==33) {
            intText.setText(String.valueOf(Math.round(arr[0])));
            if(Math.round(arr[0])>=50)
                intImage.setImageDrawable(good);
            else if(Math.round(arr[0])<= -50)
                intImage.setImageDrawable(bad);
            else
                intImage.setImageDrawable(normal);

            intTextw.setText(String.valueOf(Math.round(weeklyValue)));
            if(Math.round(weeklyValue)>=50)
                intImagew.setImageDrawable(good);
            else if(Math.round(weeklyValue)<= -50)
                intImagew.setImageDrawable(bad);
            else
                intImagew.setImageDrawable(normal);
        }
        else if (cycle==38) {
            sezText.setText(String.valueOf(Math.round(arr[0])));
            if(Math.round(arr[0])>=50)
                sezImage.setImageDrawable(good);
            else if(Math.round(arr[0])<= -50)
                sezImage.setImageDrawable(bad);
            else
                sezImage.setImageDrawable(normal);

            sezTextw.setText(String.valueOf(Math.round(weeklyValue)));
            if(Math.round(weeklyValue)>=50)
                sezImagew.setImageDrawable(good);
            else if(Math.round(weeklyValue)<= -50)
                sezImagew.setImageDrawable(bad);
            else
                sezImagew.setImageDrawable(normal);
        }



        LineDataSet lineDataSet = new LineDataSet(entries, name);
        lineDataSet.setDrawCircles(false);
        //lineDataSet.setCircleRadius(1f);
        lineDataSet.setColor(myColor);
        //lineDataSet.setValueTextColor(color);

        //lineDataSet.setFillColor(color);
        //lineDataSet.setValueTextColor(color);


        lineDataSets.add(lineDataSet);
        lineChart.setData(new LineData( lineDataSets));
        lineDataSet.setLineWidth(2f);
        lineChart.getDescription().setEnabled(false);

        lineDataSet.setDrawValues(false);

        //lineChart.getXAxis().setTextColor(color);

        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.getLegend().setTextSize(15f);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return dates[(int) value];
            }

            // we don't draw numbers, so no decimal digits needed
            private int getDecimalDigits() {  return 0; }
        };

        XAxis xAxis = lineChart.getXAxis();

        //xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        //xAxis.setAvoidFirstLastClipping(true);
        //xAxis.setTextSize(11f);
        //xAxis.setGranularityEnabled(true);

        xAxis.setTextColor(Color.WHITE);

        xAxis.setGridColor(Color.WHITE);
        xAxis.setValueFormatter(formatter);
        xAxis.setSpaceMin(0.25f);
        //xAxis.setTextSize(9f);
        //xAxis.setTextColor(Color.RED);

        YAxis yAxisLeft = lineChart.getAxisLeft();

        //yAxisLeft.setValueFormatter();

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxisLeft.setEnabled(false);
        yAxisLeft.setAxisMinimum(-100f); // start at zero
        yAxisLeft.setAxisMaximum(100f); // the axis maximum is 100

        //lineChart.setHovered(true);
        //lineChart.setDrawBorders(true);
        lineChart.setAutoScaleMinMaxEnabled(true);



    }

    public static void expand(final View v) {

        int height = v.getMeasuredHeight();
        int width = v.getMeasuredWidth();

        v.measure(height,width);

        //v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public void calculateZodiac(Date date){


        int month = date.getMonth() + 1;



        int day = date.getDate();

        Log.i("month  day"," "+month +" " + day);

        if      ((month == 12 && day >= 22 && day <= 31) || (month ==  1 && day >= 1 && day <= 19)){
            Log.i("Zodiac ","Capricorn");
            zodiacImage.setImageDrawable(getResources().getDrawable(R.drawable.capricorn));
        }

        else if ((month ==  1 && day >= 20 && day <= 31) || (month ==  2 && day >= 1 && day <= 17)){
            Log.i("Zodiac ","Aquarius");
            zodiacImage.setImageDrawable(getResources().getDrawable(R.drawable.aquarius));
        }

        else if ((month ==  2 && day >= 18 && day <= 29) || (month ==  3 && day >= 1 && day <= 19)){
            Log.i("Zodiac ","Pisces");
            zodiacImage.setImageDrawable(getResources().getDrawable(R.drawable.pisces));
        }

        else if ((month ==  3 && day >= 20 && day <= 31) || (month ==  4 && day >= 1 && day <= 19)){
            Log.i("Zodiac ","Aries");
            zodiacImage.setImageDrawable(getResources().getDrawable(R.drawable.aries));
        }

        else if ((month ==  4 && day >= 20 && day <= 30) || (month ==  5 && day >= 1 && day <= 20)){
            Log.i("Zodiac ","Taurus");
            zodiacImage.setImageDrawable(getResources().getDrawable(R.drawable.taurus));
        }

        else if ((month ==  5 && day >= 21 && day <= 31) || (month ==  6 && day >= 1 && day <= 20)){
            Log.i("Zodiac ","Gemini");
            zodiacImage.setImageDrawable(getResources().getDrawable(R.drawable.gemini));
        }

        else if ((month ==  6 && day >= 21 && day <= 30) || (month ==  7 && day >= 1 && day <= 22)){
            Log.i("Zodiac ","Cancer");
            zodiacImage.setImageDrawable(getResources().getDrawable(R.drawable.cancer));
        }

        else if ((month ==  7 && day >= 23 && day <= 31) || (month ==  8 && day >= 1 && day <= 22)){
            Log.i("Zodiac ","Leo");
            zodiacImage.setImageDrawable(getResources().getDrawable(R.drawable.leo));
        }

        else if ((month ==  8 && day >= 23 && day <= 31) || (month ==  9 && day >= 1 && day <= 22)){
            zodiacImage.setImageDrawable(getResources().getDrawable(R.drawable.virgo));
            Log.i("Zodiac ","Virgo");
        }

        else if ((month ==  9 && day >= 23 && day <= 30) || (month == 10 && day >= 1 && day <= 22)){
            zodiacImage.setImageDrawable(getResources().getDrawable(R.drawable.libra));
            Log.i("Zodiac ","Libra");
        }

        else if ((month == 10 && day >= 23 && day <= 31) || (month == 11 && day >= 1 && day <= 21)){
            zodiacImage.setImageDrawable(getResources().getDrawable(R.drawable.scorpio));
            Log.i("Zodiac ","Scorpio");
        }

        else if ((month == 11 && day >= 22 && day <= 30) || (month == 12 && day >= 1 && day <= 21)){
            zodiacImage.setImageDrawable(getResources().getDrawable(R.drawable.sagittarius));
            Log.i("Zodiac ","Sagittarius");
        }

        else
            System.out.println("Illegal date");
    }

    public void createFragment1items(Date dateofBirth){

        lineChart.removeAllViews();

        addNewLine(dateofBirth,23, "Fiziksel", Color.GREEN);
        addNewLine(dateofBirth,28, "Duygusal", Color.RED);
        addNewLine(dateofBirth,33, "Zihinsel", Color.YELLOW);
        addNewLine(dateofBirth,38, "Sezgisel", Color.WHITE);

        calculateZodiac(dateofBirth);
    }


    public void createDialogBox(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(Tab1Fragment.this.getContext());
        final View mView = getLayoutInflater().inflate(R.layout.listuser,null);
        final ListView listView = mView.findViewById(R.id.userListView);

        builder.setView(mView);
        final AlertDialog dialog = builder.create();

        userslist = new ArrayList<>();

        final String usernamelist [],surnamelist [],dateofbirthlist [];
        int userID [];
        users = databaseHelper.activeUsers();

        if(users.size()==0){
            Tab3Fragment tab3Fragment = new Tab3Fragment();

            tab3Fragment.createDialogBox("","","",-1);
        }

        else {

            usernamelist = new String[users.size()];
            surnamelist = new String[users.size()];
            dateofbirthlist = new String[users.size()];
            userID = new int[users.size()];
            for (int i = 0; i < users.size(); i++) {
                usernamelist[i] = users.get(i).get("username");
                surnamelist[i] = users.get(i).get("surname");
                dateofbirthlist[i] = users.get(i).get("dateofbirth");
                userID[i] = Integer.parseInt(users.get(i).get("id"));

                userslist.add(""+ usernamelist[i] +" "+ surnamelist[i]);
            }

            arrayAdapter = new ArrayAdapter<String>(this.getView().getContext(), android.R.layout.simple_list_item_1,userslist){

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view =super.getView(position, convertView, parent);


                    TextView textView=(TextView) view.findViewById(android.R.id.text1);
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    textView.setBackground(getResources().getDrawable(R.drawable.imageviewborder));
                    textView.setTextColor(Color.WHITE);

                    return view;
                }
            };


        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                usersnameText.setText(usernamelist[position] + " " + surnamelist[position]);

                String dateofbirth = dateofbirthlist[position];
                Date date1= null;

                try {
                    date1 = new SimpleDateFormat("dd.MM.yyyy").parse(dateofbirth);

                    String zodiac = createZodiacText(date1);
                    zodiacText.setText(zodiac);
                    createFragment1items(date1);
                    createZodiacText(date1);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();

            }
        });

        }
        dialog.show();

    }

}