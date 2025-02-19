package android.com.hotelunusemployee2.room;

import android.app.Activity;
import android.com.hotelunusemployee2.R;
import android.com.hotelunusemployee2.main.Util;
import android.com.hotelunusemployee2.task.CommonTask;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class RoomUncleanedFragment extends Fragment {
    public static final String TAG = "RoomUncleanedFragment";
    private Activity activity;
    private RecyclerView rvUncleaned;
    private List<RoomVO> roomList;
    private CommonTask getRoomTask;
    private CommonTask updateCleanStatusTask;
    private RoomAdapter mAdapter;
    private SwipeController swipeController = null;  //讓RecyclerView能左右滑動

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_uncleaned, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvUncleaned = view.findViewById(R.id.rvUncleaned);

        //透過LayoutManager的設定可以輕鬆變化RecyclerView的呈現
        rvUncleaned.setLayoutManager(new LinearLayoutManager(activity));
    }

/*
問老師寫在哪裡比較好: onActivityCreated()在Activity創建完後執行，是一次性的方法，
而onStart()在畫面即將出現時會執行，所以有可能不只執行一次。
在這邊要用updateUI()進行畫面更新，寫在onStart()比較好
*/
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (Util.networkConnected(activity)) {
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("action", "getUncleanedRoom");
//            String jsonOut = jsonObject.toString();
//            updateUI(jsonOut);
//        } else {
//            Util.showToast(activity, R.string.msg_NoNetwork);
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.networkConnected(activity)) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getUncleanedRoom");
            String jsonOut = jsonObject.toString();

            updateUI(jsonOut);
        } else {
            Util.showToast(activity, R.string.msg_NoNetwork);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (getRoomTask != null) {
            getRoomTask.cancel(true);
        }
        if (updateCleanStatusTask != null) {
            updateCleanStatusTask.cancel(true);
        }
    }

    private void updateUI(String jsonOut) {
        getRoomTask = new CommonTask(Util.URL + "RoomServletAndroid", jsonOut);

        try {
            String jsonIn = getRoomTask.execute().get();
            Type listType = new TypeToken<List<RoomVO>>() {
            }.getType();
            roomList = new Gson().fromJson(jsonIn, listType);

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        if (roomList == null || roomList.isEmpty()) {
            Util.showToast(activity, R.string.msg_RoomsNotFound);
        } else {
            mAdapter = new RoomAdapter(activity, roomList);
            rvUncleaned.setAdapter(mAdapter);
        }

        //把swipe功能附加到RecyclerView上
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {  //覆寫故障按鈕
                super.onRightClicked(position);
                Log.d(TAG, "onRightClicked: OutOfOrder");
                updateCleanStatus(position, "updateCleanStatusToOutOfOrder");
            }

            @Override
            public void onLeftClicked(int position) {  //覆寫已清掃按鈕
                super.onLeftClicked(position);
                Log.d(TAG, "onLeftClicked: Cleaned");
                updateCleanStatus(position, "updateCleanStatusToCleaned");
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(rvUncleaned);

        rvUncleaned.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private void updateCleanStatus(int position, String actionValue) {  //position: 接收Adapter position of the item  actionValue: 要送去RoomServletAndroid的值
        if (Util.networkConnected(activity)) {
            RoomVO room = roomList.get(position);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", actionValue);
            jsonObject.addProperty("room_no", room.getRoom_no());

            updateCleanStatusTask = new CommonTask(Util.URL + "RoomServletAndroid", jsonObject.toString());
            updateCleanStatusTask.execute();

            //狀態更改完成後把這一個item移除掉
            mAdapter.roomList.remove(position);
            mAdapter.notifyItemRemoved(position);
            mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
        } else {
            Util.showToast(activity, R.string.msg_NoNetwork);
        }
    }
}
