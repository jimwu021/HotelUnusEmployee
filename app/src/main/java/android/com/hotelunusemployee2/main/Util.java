package android.com.hotelunusemployee2.main;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Util {

    //連Tomcat
    public static String URL = "http://192.168.43.20:8081/DA106G1_0501/";
//    public static String URL = "http://192.168.196.135:8081/DA106G1_0501/";  //III WIFI
//    public static String URL = "http://192.168.196.104:8082/DA106G1_0501/";  //宗佑電腦

    //偏好設定檔案名稱，以後SharedPreferences的資料會寫入"preference.xml"裡面
    public final static String PREF_FILE = "preference";

//    //Page功能分類(Page類尚未建立)
//    public final static Page[] PAGES = {};

    // check if the device connect to the network，
    public static boolean networkConnected(Activity activity) {
        ConnectivityManager conManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager != null ? conManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();  //考量到支援舊版還是用這個方法和類別
    }

    public static void showToast(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void setURL() {

    }
}
