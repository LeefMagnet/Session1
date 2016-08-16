package com.leef.session1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.leef.session1.other.OtherActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_OTHER = 387;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OTHER) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: RESULT OK");
            }
            mNeedCheckGesture = true;
        }
    }

    @OnClick(R.id.fab)
    void doSomeThings() {
        mNeedCheckGesture = false;
        Intent intent = new Intent(this, OtherActivity.class);
        startActivityForResult(intent, REQUEST_CODE_OTHER);
    }
}
