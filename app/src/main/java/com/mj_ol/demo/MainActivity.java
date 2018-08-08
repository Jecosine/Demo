package com.mj_ol.demo;

import android.content.res.ColorStateList;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GestureDetector.OnGestureListener {

    private TextView home_tab = null;
    private TextView feed_tab = null;
    private TextView shop_tab = null;
    private TextView me_tab = null;
    private FrameLayout page_content = null;
    //define page viewer part
    private HomePage homePage = null;
    private FeedPage feedPage = null;
    private ShopPage shopPage = null;
    private MePage mePage = null;

    private PageFragment p1,p2,p3,p4;
    private FragmentManager manager;
    private ViewPager viewPager = null;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private  FragmentAdapter fragmentAdapter = null;

    //define swiping element
    final private int MIN_SWIPE_LENGTH = 100;
    final private int MIN_SWIPE_VELOCITY = 100;
    private GestureDetector gestureDetector = null;

    private boolean isDragMode = false;


    private int currentView = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        gestureDetector = new GestureDetector(this,this);

        //implement viewer part
        start_View();
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(),fragmentList);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(fragmentAdapter);

        //set view pager listen event
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //called when page is selected
                //Animation animation = new TranslateAnimation();
                selected();
                setHighLight(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                //called when state changed
                //case 0 nothing happened
                //case 1 is slipping
                //case 2 finish slipping
            }
        });
        selected();
        viewPager.setCurrentItem(0);
        setHighLight(0);
    }


    private void start_View() {
        home_tab = (TextView)this.findViewById(R.id.tab_home);
        feed_tab = (TextView)this.findViewById(R.id.tab_feed);
        shop_tab = (TextView)this.findViewById(R.id.tab_shop);
        me_tab = (TextView)this.findViewById(R.id.tab_me);
        //page_content = (FrameLayout)findViewById(R.id.content_container);

        home_tab.setOnClickListener(this);
        feed_tab.setOnClickListener(this);
        shop_tab.setOnClickListener(this);
        me_tab.setOnClickListener(this);

        viewPager = (ViewPager)findViewById(R.id.mainViewPager);
        homePage = new HomePage();
        feedPage = new FeedPage();
        shopPage = new ShopPage();
        mePage = new MePage();

        fragmentList.add(homePage);
        fragmentList.add(feedPage);
        fragmentList.add(shopPage);
        fragmentList.add(mePage);
        //Initially home page
        //this.onClick(home_tab);

    }

    public void selected(){
        //simultaneously set text and icon active
        home_tab.setSelected(false);
        home_tab.setTextColor(getResources().getColor(R.color.custom_gray));
        feed_tab.setSelected(false);
        feed_tab.setTextColor(getResources().getColor(R.color.custom_gray));
        shop_tab.setSelected(false);
        shop_tab.setTextColor(getResources().getColor(R.color.custom_gray));
        me_tab.setSelected(false);
        me_tab.setTextColor(getResources().getColor(R.color.custom_gray));
    }
    public void setHighLight(int i){
        selected();
        switch (i){
            case 0:
                home_tab.setSelected(true);
                home_tab.setTextColor(getResources().getColor(R.color.tea));
                break;
            case 1:
                feed_tab.setSelected(true);
                feed_tab.setTextColor(getResources().getColor(R.color.tea));
                break;
            case 2:
                shop_tab.setSelected(true);
                shop_tab.setTextColor(getResources().getColor(R.color.tea));
                break;
            case 3:
                me_tab.setSelected(true);
                me_tab.setTextColor(getResources().getColor(R.color.tea));
                break;
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tab_home:
                selected();
                home_tab.setSelected(true);
                home_tab.setTextColor(getResources().getColor(R.color.tea));
                viewPager.setCurrentItem(0,true);

                break;
            case R.id.tab_feed:
                selected();
                feed_tab.setSelected(true);
                feed_tab.setTextColor(getResources().getColor(R.color.tea));

                viewPager.setCurrentItem(1,true);
                break;
            case R.id.tab_shop:
                selected();
                shop_tab.setSelected(true);
                shop_tab.setTextColor(getResources().getColor(R.color.tea));

                viewPager.setCurrentItem(2,true);
                break;
            case R.id.tab_me:
                selected();
                me_tab.setSelected(true);
                me_tab.setTextColor(getResources().getColor(R.color.tea));

                viewPager.setCurrentItem(3,true);
                break;
        }

    }

    //implement flip
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    //onDown is called when the user first touch screen
    @Override
    public boolean onDown(MotionEvent event) {
        return false;
    }

    //onFling is called when the user swipes the screen in any direction
    @Override
    public boolean onFling(MotionEvent event1,MotionEvent event2,float vx,float vy) {
        if (isDragMode) {
            return false;
        }
        final float event1X = event1.getX();
        final float event1Y = event1.getY();
        final float event2X = event2.getX();
        final float event2Y = event2.getY();
        final float distanceX = Math.abs(event1X - event2X);
        //final float distanceY = Math.abs(event1Y - event2Y);
        final float velocityX = Math.abs(vx);
        //final float velocityY = Math.abs(vy);

        if (velocityX > this.MIN_SWIPE_VELOCITY && distanceX > this.MIN_SWIPE_LENGTH) {
            if (event1X > event2X) {
                //swipe left
                if (--currentView < 0){
                    currentView = 0;
                }else {
                    viewPager.setCurrentItem(currentView,true);
                    setHighLight(currentView);
                }
            }else {
                //swipe Right
                if (++currentView > 3){
                    currentView = 3;
                }else {
                    viewPager.setCurrentItem(currentView,true);
                }
            }

        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent event){
        isDragMode = !isDragMode;
    }
    @Override
    public boolean onSingleTapUp(MotionEvent event){
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent e2,float distanceX,float distanceY){
        return false;
    }
    @Override
    public void onShowPress(MotionEvent event){

    }


    //classes define
    public class FragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fragmentManager,List<Fragment> fragmentList) {
            super(fragmentManager);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}
