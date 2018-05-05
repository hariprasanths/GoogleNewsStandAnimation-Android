package hari.allagi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 15/4/18.
 */

public class ScrollableMenuActivity extends AppCompatActivity {

    private static final int SWIPE_THRESHOLD = 50;
    private static final int SWIPE_VELOCITY_THRESHOLD = 50;
    private static final int COLOR_TRUE_WHITE = -1;
    private static final int COLOR_GRADIENT_WHITE = -1275068417;
    private static final int STROKE_WIDTH = 5;
    private static final int STROKE_GAP = 10;

    CoordinatorLayout coordinatorLayout;
    int viewPagerInitialPosition = 0;
    ViewPager viewPager;
    CustomTabLayout tabLayout;
    CustomTabLayoutText tabLayoutText;
    LinePageIndicator linePageIndicator;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<Integer> imagesList = new ArrayList<>();
    ArrayList<Fragment> fragmentsList = new ArrayList<>();
    long transitionDuration = 1000;
    ImageButton backButton;
    View view;
    GestureDetector gestureDetector;
    boolean isSlideUp = false;

    public static int getPosition(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && data.hasExtra("currentPosition")) {
            return data.getIntExtra("currentPosition", -1);
        }
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_menu);

        isSlideUp = false;
        System.gc();

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        backButton = findViewById(R.id.back_navigation_button);
        view = findViewById(R.id.view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        tabLayoutText = findViewById(R.id.tabsText);
        linePageIndicator = (LinePageIndicator) findViewById(R.id.titles);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");

        viewPagerInitialPosition = getIntent().getIntExtra("viewPagerInitialPosition", 0);
        list = getIntent().getStringArrayListExtra("list");
        imagesList = getIntent().getIntegerArrayListExtra("imagesList");

        fragmentsList = Allagi.getFragments();
        transitionDuration = Allagi.getTransitionDuration();

        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        tabLayout.setupWithViewPager(viewPager);
        tabLayoutText.setupWithViewPager(viewPager);

        setUpLinePageIndicator();

        onTouchTabLayout();

        registerGestureDetector();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }

        initializeTabs();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(new ChangeBounds().setDuration(transitionDuration));
            startPostponedEnterTransition();
            setSharedElementCallback();
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

        slideDownView(viewPager);
        fadeOutView(linePageIndicator);
        fadeOutView(backButton);

        int position = viewPager.getCurrentItem();
        Intent data = new Intent();
        data.putExtra("currentPosition", position);
        setResult(RESULT_OK, data);
    }

    private void onTouchTabLayout() {
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
    }

    private void registerGestureDetector() {
        gestureDetector = new GestureDetector(ScrollableMenuActivity.this, new GestureDetector.SimpleOnGestureListener() {

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
    }

    private void setUpLinePageIndicator() {

        linePageIndicator.setViewPager(viewPager);

        linePageIndicator.setStrokeWidth(STROKE_WIDTH);
        linePageIndicator.setLineWidth(AllagiUtils.getLineWidth(getApplicationContext()));
        linePageIndicator.setGapWidth(STROKE_GAP);
        linePageIndicator.setSelectedColor(COLOR_TRUE_WHITE);
        linePageIndicator.setCurrentItem(viewPager.getCurrentItem());

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
    }

    private void setupViewPager(ViewPager viewPager) {

        CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager());

        adapter.addFragmentsList(fragmentsList, list);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(viewPagerInitialPosition, true);
    }

    private void initializeTabs() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            TabLayout.Tab tabText = tabLayoutText.getTabAt(i);
            View tabView = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            View tabViewText = ((ViewGroup) tabLayoutText.getChildAt(0)).getChildAt(i);

            tabView.requestLayout();
            tabViewText.requestLayout();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                tabView.setBackgroundResource(imagesList.get(i));

            } else
                tabView.setBackgroundColor(AllagiUtils.getThemePrimaryColor(getApplicationContext()));

            View view = LayoutInflater.from(this).inflate(R.layout.tab, null);
            TextView iv = view.findViewById(R.id.imageView);
            iv.setText(list.get(i));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                iv.setTransitionName("text" + i);
            }
            tabText.setCustomView(view);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tabView.setTransitionName("image" + i);
            }
            tab.setText("");
        }

    }

    private void setSharedElementCallback() {
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {

                if (!isSlideUp) {
                    slideUpView(viewPager);
                    fadeInView(linePageIndicator);
                    fadeInView(backButton);
                    isSlideUp = true;
                }

                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
            }
        });
    }

    private void slideUpView(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                view.getHeight(),
                0);
        animate.setDuration(transitionDuration);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    private void slideDownView(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                0,
                view.getHeight());
        animate.setDuration(transitionDuration);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    private void fadeInView(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(transitionDuration);
        view.startAnimation(anim);
    }

    private void fadeOutView(View view) {
        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(transitionDuration);
        view.startAnimation(anim);
    }

}