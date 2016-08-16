package com.leef.session1.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leef.session1.BaseActivity;
import com.leef.session1.MainActivity;
import com.leef.session1.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    void done() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(BaseActivity.KEY_FROM_YIMI, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
