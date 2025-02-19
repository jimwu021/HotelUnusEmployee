package android.com.hotelunusemployee2.delivery;

import android.com.hotelunusemployee2.R;
import android.com.hotelunusemployee2.employee.LoginActivity;
import android.com.hotelunusemployee2.main.Util;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class DeliveryActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SectionsPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        getSupportActionBar().setElevation(0);  //去除ActionBar底下的陰影

        //將SectionsPagerAdapter指派給ViewPager(讓下面的兩個fragment可以滑動)
        pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);

        //讓tab能切換畫面
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
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
                new AlertDialog.Builder(DeliveryActivity.this)
                        .setTitle(R.string.msg_AlertLogout)
                        .setPositiveButton(R.string.text_btnLogout, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences = getSharedPreferences(Util.PREF_FILE, MODE_PRIVATE);
                                preferences.edit().putBoolean("login", false).apply();
                                Intent intent = new Intent(DeliveryActivity.this, LoginActivity.class);
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

    //用來把Fragment加入viewPager的Adapter
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        //必備兩方法：getCount()、getItem()
        @Override
        public int getCount() {  //用來指定viewPager裡面的頁數
            return 2;  //讓viewPager有2頁
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {  //指定每一頁應顯示哪個Fragment。position是頁數，從0開始
            switch (position) {
                case 0:
                    return new OngoingFragment();
                case 1:
                    return new DoneFragment();
            }
            return null;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.text_tabItemOngoing);
                case 1:
                    return getResources().getText(R.string.text_tabItemDone);
            }
            return null;
        }
    }
}
