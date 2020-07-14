package com.melih.tabactionbar;


import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());




        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{ContextCompat.getColor(this, R.color.color1),
                        ContextCompat.getColor(this, R.color.color2),
                        ContextCompat.getColor(this, R.color.color3),
                        ContextCompat.getColor(this, R.color.color4),
                        ContextCompat.getColor(this, R.color.color5),
                        ContextCompat.getColor(this, R.color.color6)});

        GradientDrawable gradientDrawable2 = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{ContextCompat.getColor(this, R.color.pink1),
                        ContextCompat.getColor(this, R.color.pink2),
                        ContextCompat.getColor(this, R.color.pink3),
                        ContextCompat.getColor(this, R.color.pink4),
                        ContextCompat.getColor(this, R.color.pink5)});



        Drawable myGradient = getResources().getDrawable(R.drawable.dialog_background);

        findViewById(R.id.relLayout).setBackground(myGradient);

        mSectionsPageAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        tabLayout.getTabAt(0).setIcon(R.drawable.homepassive);
        tabLayout.getTabAt(1).setIcon(R.drawable.xicon);
        tabLayout.getTabAt(2).setIcon(R.drawable.userpassive);
        tabLayout.getTabAt(3).setIcon(R.drawable.userspassive);

    }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment());
        adapter.addFragment(new Tab2Fragment());
        adapter.addFragment(new Tab3Fragment());
        adapter.addFragment(new Tab4Fragment());
        viewPager.setAdapter(adapter);
    }






}
