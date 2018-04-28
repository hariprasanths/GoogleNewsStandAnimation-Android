package hari.allagi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;

/**
 * Created by hari on 15/4/18.
 */

public class ScrollableMenuActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    int viewPagerInitialPosition = 0;
    ViewPager viewPager;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<Integer> imagesList = new ArrayList<>();
    ArrayList<Fragment> fragmentsList = new ArrayList<>();
    ImageButton backButton;
    View view;
    GestureDetector gestureDetector;

    public static int getPosition(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && data.hasExtra("currentPosition")) {
            return data.getIntExtra("currentPosition", -1);
        }
        return -1;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_menu);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        backButton = findViewById(R.id.back_navigation_button);
        view = findViewById(R.id.view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");

        viewPagerInitialPosition = getIntent().getIntExtra("viewPagerInitialPosition", 0);
        list = getIntent().getStringArrayListExtra("list");
        imagesList = getIntent().getIntegerArrayListExtra("imagesList");
        fragmentsList = Allagi.getFragments();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        final CustomTabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        final CustomTabLayoutText tabLayoutText = findViewById(R.id.tabsText);
        tabLayoutText.setupWithViewPager(viewPager);

        final LinePageIndicator linePageIndicator = (LinePageIndicator) findViewById(R.id.titles);
        linePageIndicator.setViewPager(viewPager);


        linePageIndicator.setStrokeWidth(5);
        linePageIndicator.setLineWidth(AllagiUtils.getLineWidth(getApplicationContext()));
        linePageIndicator.setGapWidth(10);
        linePageIndicator.setSelectedColor(Color.parseColor("#ffffff"));
        linePageIndicator.setCurrentItem(viewPager.getCurrentItem());

        tabLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        tabLayoutText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        gestureDetector = new GestureDetector(ScrollableMenuActivity.this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 50;
            private static final int SWIPE_VELOCITY_THRESHOLD = 50;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                            } else {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                            }
                            result = true;
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                        } else {
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                linePageIndicator.onPageSelected(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                linePageIndicator.onPageScrolled(arg0, arg1, arg2);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                linePageIndicator.onPageScrollStateChanged(arg0);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }

        tabLayoutText.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabLayout.Tab selectedTab = tabLayout.getTabAt(tab.getPosition());
                View selected = tab.getCustomView();
                TextView iv_text = (TextView) selected.findViewById(R.id.imageView);
                iv_text.setTextColor(-1);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TabLayout.Tab unSelectedTab = tabLayout.getTabAt(tab.getPosition());
                View selected = tab.getCustomView();
                TextView iv_text = (TextView) selected.findViewById(R.id.imageView);
                iv_text.setTextColor(-1275068417);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            TabLayout.Tab tabText = tabLayoutText.getTabAt(i);
            View tabView = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            View tabViewText = ((ViewGroup) tabLayoutText.getChildAt(0)).getChildAt(i);

            tabView.requestLayout();
            tabViewText.requestLayout();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                tabView.setBackgroundResource(imagesList.get(i));

            } else tabView.setBackgroundColor(Color.parseColor("#1e6ac7"));

            View view = LayoutInflater.from(this).inflate(R.layout.tab, null);
            TextView iv = view.findViewById(R.id.imageView);
            iv.setText(list.get(i));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                iv.setTransitionName("text" + i);
            }
            if (tabViewText.isSelected()) {
                iv.setTextColor(-1);
            } else iv.setTextColor(-1275068417);
            tabText.setCustomView(view);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tabView.setTransitionName("image" + i);
            }
            tab.setText("");

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(new ChangeBounds().setDuration(1000));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startPostponedEnterTransition();
        }

    }

    @Override
    public void finish() {
        setResult();
        super.finish();
    }

    @Override
    public void finishAfterTransition() {
        setResult();
        super.finishAfterTransition();
    }

    @Override
    public void onBackPressed() {
        setResult();
        super.onBackPressed();
    }

    private void setResult() {
        int position = viewPager.getCurrentItem();
        Intent data = new Intent();
        data.putExtra("currentPosition", position);
        setResult(RESULT_OK, data);
    }

    private void setupViewPager(ViewPager viewPager) {

        CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager());

        adapter.addFragmentsList(fragmentsList, list);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(viewPagerInitialPosition, true);
    }
}