package com.leef.session1;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.leef.session1.other.LoginActivity;

import java.util.Random;

/**
 * Created by IWALL on 2016/8/16.
 * 是否需要验证手势密码的逻辑控制
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private static final int BACK_TIMESTAMP_INITIAL = 0;//需要验证数字密码
    private static final int BACK_TIMESTAMP_CLEAR = -1;//不需要验证数字密码

    public static final int BACK_TIME_OUT = 10 * 1000;//超时间隔
    public static final String KEY_FROM_YIMI = "from_yimi";

    protected boolean mFromYiMi;//是否来自一密内部的跳转
    protected boolean mNeedCheckGesture;//是否需要验证数字密码

    protected static long mLastBackTimeStamp = BACK_TIMESTAMP_INITIAL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNeedCheckGesture = true;
        mFromYiMi = getIntent().getBooleanExtra(KEY_FROM_YIMI, false);
        boolean mFromHistory = (getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0;
        Log.d(TAG, "onCreate: mLastBackTimeStamp -> " + mLastBackTimeStamp + " | from history -> " + mFromHistory);
        if ((mLastBackTimeStamp != BACK_TIMESTAMP_INITIAL) && !isFromYiMi() && mFromHistory) {
            mLastBackTimeStamp = BACK_TIMESTAMP_CLEAR;
            Log.e(TAG, "onCreate: reset timestamp");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (needCheckGesture()) {
            Log.d(TAG, "onResume: NO SKIP");
            if (checkBackTimeOut()) {
                if (isGestureAvailable()) {
                    Intent intent = new Intent(this, LockActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra(BaseActivity.KEY_FROM_YIMI, true);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
            overridePendingTransition(0, 0);
        } else {
            Log.d(TAG, "onResume: SKIP");
        }
        mLastBackTimeStamp = BACK_TIMESTAMP_CLEAR;
    }

    @Override
    protected void onPause() {
        if (needCheckGesture()) {
            if (this instanceof LockActivity) {
                mLastBackTimeStamp = BACK_TIMESTAMP_CLEAR;
            } else {
                mLastBackTimeStamp = SystemClock.elapsedRealtime();
            }
        }
//        mNeedCheckGesture = true;
        super.onPause();
    }

    //  是否来自应用内部的跳转
    public boolean isFromYiMi() {
        return mFromYiMi;
    }

    static Random random = new Random();

    //  是否设置了数字密码
    private boolean isGestureAvailable() {
        int r = random.nextInt(2);
        return r != 2;
    }

    //  是否需要验证数字密码
    protected boolean needCheckGesture() {
        return mNeedCheckGesture;
    }

    //  检查是否进入后台时间是否超过设置的默认超时时间
    private boolean checkBackTimeOut() {
        double diff = Math.abs(SystemClock.elapsedRealtime() - mLastBackTimeStamp);
        Log.d(TAG, "checkBackTimeOut: mLastBackTimeStamp -> " + mLastBackTimeStamp + " | isFromYimi -> " + isFromYiMi());
        boolean satisfy = diff > BACK_TIME_OUT;
        Log.d(TAG, "checkBackTimeOut: satisfy -> " + satisfy);
        return (mLastBackTimeStamp != BACK_TIMESTAMP_CLEAR) && satisfy;
    }
}
