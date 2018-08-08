package com.mj_ol.demo;

import android.content.res.ColorStateList;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GestureDetector.OnGestureListener {
    private TextView home_tab;
    private TextView feed_tab;
    private TextView shop_tab;
    private TextView me_tab;
    private FrameLayout page_content;

    private PageFragment p1,p2,p3,p4;
    private FragmentManager manager;

    //set swiping element
    final private int MIN_SWIPE_LENGTH = 100;
    final private int MIN_SWIPE_VELOCITY = 100;
    private ViewFlipper fliper = null;
    private GestureDetector gestureDetector = null;
    private Vibrator vibrator = null;
    private boolean isDragMode = false;
    //set animation
    private Animation leftin = null;
    private Animation leftout = null;
    private Animation rightin = null;
    private Animation rightout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        fliper = new ViewFlipper(this);
        gestureDetector = new GestureDetector(this,this);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        fliper.setInAnimation(leftin);
        fliper.setOutAnimation(leftout);
        fliper.setFlipInterval(3000);
        fliper.setAnimateFirstView(true);

        start_View();
    }



    private void start_View() {
        home_tab = (TextView)this.findViewById(R.id.tab_home);
        feed_tab = (TextView)this.findViewById(R.id.tab_feed);
        shop_tab = (TextView)this.findViewById(R.id.tab_shop);
        me_tab = (TextView)this.findViewById(R.id.tab_me);
        page_content = (FrameLayout)findViewById(R.id.content_container);

        home_tab.setOnClickListener(this);
        feed_tab.setOnClickListener(this);
        shop_tab.setOnClickListener(this);
        me_tab.setOnClickListener(this);

        //Initiallty home page
        this.onClick(home_tab);

    }
    public void selected(){
        home_tab.setSelected(false);
        home_tab.setTextColor(getResources().getColor(R.color.custom_gray));
        feed_tab.setSelected(false);
        feed_tab.setTextColor(getResources().getColor(R.color.custom_gray));
        shop_tab.setSelected(false);
        shop_tab.setTextColor(getResources().getColor(R.color.custom_gray));
        me_tab.setSelected(false);
        me_tab.setTextColor(getResources().getColor(R.color.custom_gray));
    }
    public void hideFragment(FragmentTransaction transaction){
        if (p1 != null)
            transaction.hide(p1);
        if (p2 != null)
            transaction.hide(p2);
        if (p3 != null)
            transaction.hide(p3);
        if (p4 != null)
            transaction.hide(p4);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch (view.getId()){
            case R.id.tab_home:
                selected();
                home_tab.setSelected(true);
                home_tab.setTextColor(getResources().getColor(R.color.tea));
                if (p1 == null){
                    p1 = new PageFragment("Home Page");
                    transaction.add(R.id.content_container,p1);
                }
                else {
                    transaction.show(p1);
                }
                break;
            case R.id.tab_feed:
                selected();
                feed_tab.setSelected(true);
                feed_tab.setTextColor(getResources().getColor(R.color.tea));
                if (p2 == null){
                    p2 = new PageFragment("Feed Page");
                    transaction.add(R.id.content_container,p2);
                }
                else {
                    transaction.show(p2);
                }
                break;
            case R.id.tab_shop:
                selected();
                shop_tab.setSelected(true);
                shop_tab.setTextColor(getResources().getColor(R.color.tea));
                if (p3 == null){
                    p3 = new PageFragment("Shop Page");
                    transaction.add(R.id.content_container,p3);
                }
                else {
                    transaction.show(p3);
                }
                break;
            case R.id.tab_me:
                selected();
                me_tab.setSelected(true);
                me_tab.setTextColor(getResources().getColor(R.color.tea));
                if (p4 == null){
                    p4 = new PageFragment("Me Page");
                    transaction.add(R.id.content_container,p4);
                }
                else {
                    transaction.show(p4);
                }
                break;
        }
        transaction.commit();
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
        if (isDragMode)
            return false;
        final float event1X = event1.getX();
        final float event1Y = event1.getY();
        final float event2X = event2.getX();
        final float event2Y = event2.getY();
        final float distanceX = Math.abs(event1X - event2X);
        final float distanceY = Math.abs(event1Y - event2Y);
        final float velocityx = Math.abs(vx);
        final float velocityy = Math.abs(vy);

        if (velocityx > this.MIN_SWIPE_VELOCITY && distanceX > this.MIN_SWIPE_LENGTH) {
            if (event1X > event2X) {
                //swipe left

            }
        }
    }

}
