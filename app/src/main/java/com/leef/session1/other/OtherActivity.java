package com.leef.session1.other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leef.session1.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_return)
    void done() {
        setResult(RESULT_OK);
        this.finish();
    }
}
