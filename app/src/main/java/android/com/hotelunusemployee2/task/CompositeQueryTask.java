package android.com.hotelunusemployee2.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

//太複雜，日後再嘗試
//為了傳入String和Map把輸入參數設成Object
public class CompositeQueryTask extends AsyncTask<Object, Integer, String> {

    public static final String TAG = "CompositeQueryTask";
    private String urlStr;
    private Map<String, String[]> outMap;

    public CompositeQueryTask(String urlStr, Map<String, String[]> outMap) {
        this.urlStr = urlStr;
        this.outMap = outMap;
    }

    @Override
    protected String doInBackground(Object... objects) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getSearchAndroid");
        jsonObject.addProperty("map", String.valueOf(outMap));

        return getRemoteData(jsonObject.toString());
    }

    private String getRemoteData(String jsonOut) {
        HttpURLConnection connection = null;
        StringBuilder inStr = new StringBuilder();

        try {
            connection = (HttpURLConnection) new URL(urlStr).openConnection();
            connection.setDoInput(true); // allow inputs
            connection.setDoOutput(true); // allow outputs
            connection.setUseCaches(false); // do not use a cached copy

            // 不知道請求內容大小時可以呼叫此方法將請求內容分段傳輸，設定0代表使用預設大小
            // 參考HttpURLConnection API的Posting Content部分
            connection.setChunkedStreamingMode(0);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "UTF-8");

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            bufferedWriter.write(jsonOut);
            Log.d(TAG, "output: " + jsonOut);
            bufferedWriter.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    inStr.append(line);
                }
            } else {
                Log.d(TAG, "response code: " + responseCode);
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return inStr.toString();
    }
}
