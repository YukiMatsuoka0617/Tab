package com.example.taboriginal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView mBaseBar;
    ImageView mBar;

    TextView mTextView;

    private int targetLocalX;
    private int targetLocalY;

    private int firstPositionX;
    private int firstPositionY;

    private int screenX;

    int diffX;
    int mThreshold;

    double ratio = 0.9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBaseBar = findViewById(R.id.base_bar);
        mBar = findViewById(R.id.bar);

        mTextView = findViewById(R.id.text_view);

        mBar.layout(targetLocalX,
                firstPositionY,
                targetLocalX + mBar.getWidth(),
                firstPositionY + mBar.getHeight());

//        TranslateAnimation translate = new TranslateAnimation(0, -100, 0, 0);
//        // 1000ms間
//        translate.setDuration(500);
//        // 2回繰り返す
////        translate.setInterpolator(new CycleInterpolator(1));
//        translate.setFillAfter(true);
//        // アニメーションスタート
//        mBar.startAnimation(translate);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getRawX();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("test","///DOWN///");
                mThreshold = (int)(mBaseBar.getRight()*ratio);
                Log.d("test", "mThreshold = " + mThreshold);
                firstPositionX = mBar.getLeft();
                firstPositionY = mBar.getTop();
                targetLocalX = mBar.getLeft();
                Log.d("test", "firstPositionX = " + firstPositionX);
                Log.d("test", "firstPositionY = " + firstPositionY);
                screenX = x;

                targetLocalX += 100;
                mBar.layout(targetLocalX,
                        firstPositionY,
                        targetLocalX + mBar.getWidth(),
                        firstPositionY + mBar.getHeight());

                TranslateAnimation translate = new TranslateAnimation(0, -100, 0, 0);
                translate.setDuration(200);
                translate.setFillAfter(true);
                mBar.startAnimation(translate);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("test", "///MOVE///");
                Log.d("test", "mBaseBar.getLeft() = " + mBaseBar.getLeft());
                Log.d("test", "mBaseBar.getRight() = " + mBaseBar.getRight());

                diffX = screenX - x;
                Log.d("test", "diffX = " + diffX);

                Log.d("test", "mBar.getRight() = " + mBar.getRight());

                if ((mBar.getLeft() - diffX) >= firstPositionX && (mBar.getRight() - diffX) <= mThreshold) {
                    Log.d("test", "if");
                    targetLocalX -= diffX;
                } else if ((mBar.getLeft() - diffX) < firstPositionX) {
                    Log.d("test", "else if ((mBar.getLeft() - diffX) < firstPositionX)");
                    targetLocalX = firstPositionX;
                } else {
                    Log.d("test", "else");
                    targetLocalX = (int)(mThreshold) - mBar.getWidth();
                    mTextView.setText("Change");
                }
//                mBar.layout(targetLocalX,
//                        firstPositionY,
//                        targetLocalX + mBar.getWidth(),
//                        firstPositionY + mBar.getHeight());
                screenX = x;
                break;
            case MotionEvent.ACTION_UP:
                Log.d("test","///UP///");
                mBar.layout(firstPositionX,
                        firstPositionY,
                        firstPositionX + mBar.getWidth(),
                        firstPositionY + mBar.getHeight());
                mTextView.setText(" ");
                break;
        }
        return super.onTouchEvent(event);
    }
}