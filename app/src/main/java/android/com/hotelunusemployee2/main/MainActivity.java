package android.com.hotelunusemployee2.main;

import android.com.hotelunusemployee2.R;
import android.com.hotelunusemployee2.delivery.DeliveryActivity;
import android.com.hotelunusemployee2.employee.LoginActivity;
import android.com.hotelunusemployee2.room.RoomActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private static final int REQUEST_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onLogin();  //進MainActivity之前先intent LoginActivity
    }

    //startActivityForResult()和 onActivityResult()的搭配詳見 https://registerboy.pixnet.net/blog/post/30315638
    private void onLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(loginIntent, REQUEST_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_LOGIN:
                    SharedPreferences preferences = getSharedPreferences(Util.PREF_FILE, MODE_PRIVATE);
                    Boolean login = preferences.getBoolean("login", false);
                    if (!login) {
                        Util.showToast(this, "login failed");
                        onLogin();
                    }
            }
        }
    }

    //做menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    //當menu被點擊時
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:

                //跳出AlertDialog視窗
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.msg_AlertLogout)
                        .setPositiveButton(R.string.text_btnLogout, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences = getSharedPreferences(Util.PREF_FILE, MODE_PRIVATE);
                                preferences.edit().putBoolean("login", false).apply();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.text_btnCancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onRoomClick(View view){
        Intent intent = new Intent(this, RoomActivity.class);
        startActivity(intent);
    }

    public void onDeliveryClick(View view){
        Intent intent = new Intent(this, DeliveryActivity.class);
        startActivity(intent);
    }
}
