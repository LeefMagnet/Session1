package com.leef.session1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LockActivity extends BaseActivity {

    @BindView(R.id.btn_failure)
    Button mBtnFailure;
    @BindView(R.id.btn_success)
    Button mBtnSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawableResource(R.drawable.ic_background);
    }

    @Override
    protected boolean needCheckGesture() {
        return false;
    }

    @OnClick(R.id.btn_failure)
    void clickFailure() {
        Toast.makeText(this, "验证失败", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_success)
    void clickSuccess() {
        Toast.makeText(this, "验证成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(BaseActivity.KEY_FROM_YIMI, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
