package android.com.hotelunusemployee2.room;

import android.com.hotelunusemployee2.R;
import android.com.hotelunusemployee2.employee.LoginActivity;
import android.com.hotelunusemployee2.main.Util;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RoomActivity extends AppCompatActivity {
    public static final String TAG = "RoomActivity";
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: start");
        setContentView(R.layout.activity_room);

        bottomNav = findViewById(R.id.bottomNav);
        NavController controller = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(bottomNav, controller);
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
                new AlertDialog.Builder(RoomActivity.this)
                        .setTitle(R.string.msg_AlertLogout)
                        .setPositiveButton(R.string.text_btnLogout, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences = getSharedPreferences(Util.PREF_FILE, MODE_PRIVATE);
                                preferences.edit().putBoolean("login", false).apply();
                                Intent intent = new Intent(RoomActivity.this, LoginActivity.class);
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
}
