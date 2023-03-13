package com.ydh.intelligence.activitys;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydh.intelligence.R;
import com.ydh.intelligence.views.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.account_edit)
    ClearEditText accountEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.iv_password)
    ImageView ivPassword;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.tv_current_version)
    TextView tvCurrentVersion;
    private boolean isShowPassword = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unBind = ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_password, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_password:
                if (isShowPassword) {
                    passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivPassword.setImageResource(R.mipmap.close_eye);
                    isShowPassword = false;
                } else {
                    passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivPassword.setImageResource(R.mipmap.open_eye);
                    isShowPassword = true;
                }
                break;
            case R.id.login_btn:
                break;
        }
    }
}