package org.rbt.swipeanimation;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.LinearLayout;

public class SwipeActivity extends AppCompatActivity {

    private LinearLayout swipeLayout, autoInvisibleLayout;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        //this is the view which should slide out automatically
        autoInvisibleLayout = (LinearLayout) findViewById(R.id.autoInvisibleLayout);

        //this is the first view, which slides on swiping
        swipeLayout = (LinearLayout) findViewById(R.id.swipeLayout);
        swipeLayout.setOnTouchListener(new OnSwipeTouchListener(this) {

            //use it if required
//            @Override
//            public void onSwipeLeft() {
//                printLog("=====Left Swipe=====");
//            }

            @Override
            public void onSwipeRight() {
                printLog("=====Right Swipe=====");
                //handler executes the runnable interface in specified duration in milliseconds
                handler = new Handler();
                handler.postDelayed(bgThread, 2000);

                //carries out the swipe functionality
                swipeLayout.animate().translationX(swipeLayout.getWidth());
                swipeLayout.animate().setDuration(1000);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //removes the handler call backs
        if (handler != null) {
            handler.removeCallbacks(bgThread);
            handler = null;
        }
    }

    private Runnable bgThread = new Runnable() {
        @Override
        public void run() {
            printLog("=====Handler called=====");
            //carries out the second auto swipe functionality
            autoInvisibleLayout.animate().translationX(autoInvisibleLayout.getWidth());
            autoInvisibleLayout.animate().setDuration(1000);

            //removes the handler call backs
            handler.removeCallbacks(this);
            handler = null;
        }
    };

    private void printLog(String msg) {
        Log.i("Swipe Animation", msg);
    }

    private float getScreenWidth() {
        DisplayMetrics metrics;
        float width = 0, height = 0;

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

//        height = metrics.heightPixels;
//        width = metrics.widthPixels;

        float density = getResources().getDisplayMetrics().density;

        width =  metrics.widthPixels/density;

        return width;
    }

//    private float pixelsToDP(int pixels) {
//        Resources r = getResources();
//        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, r.getDisplayMetrics());
//
//        return px;
//    }
}
