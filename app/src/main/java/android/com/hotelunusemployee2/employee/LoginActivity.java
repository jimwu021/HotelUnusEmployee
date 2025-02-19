package android.com.hotelunusemployee2.employee;

import android.com.hotelunusemployee2.R;
import android.com.hotelunusemployee2.main.Util;
import android.com.hotelunusemployee2.task.CommonTask;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private CommonTask isEmployeeTask;

    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int VIEW_DELAY = 400;

    //為了讓神奇小按鈕能取到值，帳密輸入框設成實體變數


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageView logoImageView = findViewById(R.id.imgLogo);
        ViewGroup container = findViewById(R.id.container);
        setResult(RESULT_CANCELED);

        //動畫效果
        ViewCompat.animate(logoImageView)
                .translationY(-250)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION)
                .setInterpolator(new DecelerateInterpolator(1.2f))
                .start();

        for (int i = 0; i < 2; i++) {
            View view = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (view instanceof TextView) {
                viewAnimator = ViewCompat.animate(view)
                        .translationY(50).alpha(1)
                        .setStartDelay((VIEW_DELAY * i) + 500)
                        .setDuration(1000);
            } else {
                viewAnimator = ViewCompat.animate(view);
            }
            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }

        //以下六個為了設定URL路徑用
        final TextView tvHotelUnus = findViewById(R.id.tvTitle);
        final TextView tvEmployees = findViewById(R.id.tvTitle2);
        final EditText etUrl = findViewById(R.id.etUrl);
        final Button btnMobile = findViewById(R.id.btnMobile);
        final Button btnWifi = findViewById(R.id.btnWifi);
        final Button btnSetUrl = findViewById(R.id.btnSetUrl);

        //設定神奇小按鈕
        final EditText etEmp_id = findViewById(R.id.etEmp_id);
        final EditText etPassword = findViewById(R.id.etPassword);

        final Employee emp1 = new Employee("EMP0000001", "123456"); //餐飲部帳號
        final Employee emp2 = new Employee("EMP0000002", "123456"); //客房部帳號
        final Employee emp3 = new Employee("EMP0000003", "123456"); //休閒部帳號

        List<Employee> list = new ArrayList<>();
        list.add(emp1);
        list.add(emp2);
        list.add(emp3);

        final int[] count = {0};

        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + count[0]);

                if (count[0] == 0) {  //按第一下
                    etEmp_id.setText(emp2.getEmp_id());
                    etPassword.setText(emp2.getPassword());
                } else if (count[0] == 1) {  //按第二下
                    etEmp_id.setText(emp1.getEmp_id());
                    etPassword.setText(emp1.getPassword());
                } else if (count[0] == 2) {  //按第三下
                    etEmp_id.setText(emp3.getEmp_id());
                    etPassword.setText(emp3.getPassword());
                }
                count[0]++;
                if (count[0] == 3) {
                    count[0] = 0;
                }
            }
        });

        //以下幾個監聽器用來做隱藏式的URL設定
        tvHotelUnus.setOnClickListener(new View.OnClickListener() {  //顯示設定URL的按鈕與輸入框
            @Override
            public void onClick(View v) {
                etUrl.setVisibility(View.VISIBLE);
                btnMobile.setVisibility(View.VISIBLE);
                btnWifi.setVisibility(View.VISIBLE);
                btnSetUrl.setVisibility(View.VISIBLE);
            }
        });

        tvEmployees.setOnClickListener(new View.OnClickListener() {  //顯示當前URL
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "當前URL：" + Util.URL, Toast.LENGTH_LONG).show();
//                Util.showToast(LoginActivity.this, "當前URL：" + Util.URL);
            }
        });

        btnMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUrl.setText("http://192.168.43.20:8081/DA106G1_0501/");
            }
        });

        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUrl.setText("http://192.168.196.135:8081/DA106G1_0501/");
            }
        });

        btnSetUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.URL = etUrl.getText().toString().trim();
                Toast.makeText(LoginActivity.this, "URL已設定：" + Util.URL, Toast.LENGTH_LONG).show();

                etUrl.setVisibility(View.INVISIBLE);
                btnMobile.setVisibility(View.INVISIBLE);
                btnWifi.setVisibility(View.INVISIBLE);
                btnSetUrl.setVisibility(View.INVISIBLE);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //SharedPreferences介面的用法詳見 https://litotom.com/2017/06/27/ch7-1-sharedpreferences/
        /*
         * MODE_PRIVATE: 只允許本應用程式內存取，這是最常用參數
         * getSharedPreferences:取得物件
         * */
        SharedPreferences preferences = getSharedPreferences(Util.PREF_FILE, MODE_PRIVATE);
        boolean login = preferences.getBoolean("login", false);
        if (login) {
            String emp_id = preferences.getString("emp_id", "");
            String emp_password = preferences.getString("emp_password", "");
            if (isEmployee(emp_id, emp_password)) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isEmployeeTask != null) {
            isEmployeeTask.cancel(true);
        }
    }

    //防止按下返回鍵後回到MainActivity
    @Override
    public void onBackPressed() {  //onBackPressed(): 當返回鍵被按下時。和onKeyDown()的區別: onKeyDown不只用在返回鍵，也可以是音量鍵、電源鍵...等
        super.onBackPressed();
        finishAffinity();  // Finish this activity as well as all activities immediately below it in the current task that have the same affinity.
    }

    public void onLoginClick(View view) {
        EditText etEmp_id = findViewById(R.id.etEmp_id);
        EditText etPassword = findViewById(R.id.etPassword);
        TextInputLayout tilEmp_id = findViewById(R.id.tilEmp_id);
        TextInputLayout tilPassword = findViewById(R.id.tilPassword);

        String emp_id = etEmp_id.getText().toString().trim();
        String emp_password = etPassword.getText().toString().trim();

        if (emp_id.isEmpty()) {
            tilEmp_id.setError(getText(R.string.msg_InvalidEmpId));
            return;
        } else {
            tilEmp_id.setError(null);
        }

        if (emp_password.isEmpty()) {
            tilPassword.setError(getText(R.string.msg_InvalidPassword));
            return;
        } else {
            tilPassword.setError(null);
        }

        if (isEmployee(emp_id, emp_password)) {
            SharedPreferences preferences = getSharedPreferences(Util.PREF_FILE, MODE_PRIVATE);
            preferences.edit().putBoolean("login", true)
                    .putString("emp_id", emp_id)
                    .putString("emp_password", emp_password)
                    .apply();
            setResult(RESULT_OK);
            finish();

        } else {
            Util.showToast(this, R.string.msg_InvalidEmpIdOrPassword);
        }
    }

    private boolean isEmployee(final String emp_id, final String emp_password) {
        boolean isEmployee = false;
        if (Util.networkConnected(this)) {
            String url = Util.URL + "EmployeeServletAndroid";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "isEmployee");
            jsonObject.addProperty("emp_id", emp_id);
            jsonObject.addProperty("emp_password", emp_password);
            String jsonOut = jsonObject.toString();

            isEmployeeTask = new CommonTask(url, jsonOut);

            try {
                String result = isEmployeeTask.execute().get();
                isEmployee = Boolean.valueOf(result);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
                isEmployee = false;
            }
        } else {
            Util.showToast(this, R.string.msg_NoNetwork);
        }
        return isEmployee;
    }
}





