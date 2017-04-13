package com.sharker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sharker.R;
import com.sharker.models.AuthUser;
import com.sharker.models.FirstHand;
import com.sharker.network.SharkerParams;

import org.xutils.common.Callback;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 */
public class SignUpActivity extends BaseActivity {

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, SignUpActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     *
     * @param activity
     * @param from
     */
    public static void open(Activity activity, int from) {
        Intent intent = new Intent(activity, SignUpActivity.class);
        intent.putExtra("from", from);
        activity.startActivity(intent);
        activity.finish();
    }

    @BindView(R.id.btn_login)
    public Button btn_login;

    @BindView(R.id.btn_rand)
    public TextView btn_rand;

    @BindView(R.id.edit_rand)
    public EditText edit_rand;

    @BindView(R.id.edit_phone)
    public EditText edit_phone;

    AuthUser authUser;

    int from = AuthUser.FROM_MAIN_ACTIVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        int from = getIntent().getIntExtra("from", AuthUser.FROM_MAIN_ACTIVITY);
    }

    @OnClick(R.id.btn_rand)
    public void login(TextView textView) {
        String number = edit_phone.getText().toString().trim();
        if (TextUtils.isEmpty(number) || number.length() != 11) {
            //TODO 手机号错误
        } else {
            userLogin(number);
        }

    }

    @OnClick(R.id.btn_login)
    public void rand(TextView textView) {
        //TODO 倒计时处理
        String rand = edit_rand.getText().toString().trim();
        if (authUser != null && !TextUtils.isEmpty(rand)) {
            userCheckRand(authUser.session, rand);
            //TODO 开始倒计时
        } else {//TODO 处理验证码错误
        }
    }

    public void userLogin(String number) {
        SharkerParams params = new SharkerParams("user_login");
        params.addBodyParameter("number", number);
        x.http().post(params, new Callback.CommonCallback<AuthUser>() {
            @Override
            public void onSuccess(AuthUser result) {
                authUser = result;

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //TODO 处理网络错误
            }

            @Override
            public void onCancelled(CancelledException cex) {
                //TODO 处理网络错误
            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void userCheckRand(String session, String rand) {
        SharkerParams params = new SharkerParams("user_check_rand");
        params.addBodyParameter("session", session);
        params.addBodyParameter("rand", rand);
        x.http().post(params, new Callback.CommonCallback<AuthUser>() {
            @Override
            public void onSuccess(AuthUser result) {
                authUser = result;
                FirstHand.saveSession(result);
                if (from == AuthUser.FROM_MAIN_ACTIVITY) {
                    MainActivity.open(SignUpActivity.this);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //TODO 处理网络错误
            }

            @Override
            public void onCancelled(CancelledException cex) {
                //TODO 处理网络错误
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
