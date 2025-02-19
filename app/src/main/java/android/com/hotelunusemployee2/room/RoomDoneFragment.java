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

public class RoomDoneFragment extends Fragment {
    public static final String TAG = "RoomDoneFragment";
    private Activity activity;
    private RecyclerView rvDone;
    private List<RoomVO> roomList;
    private CommonTask getRoomTask;
    private CommonTask updateCleanStatusTask;
    private RoomAdapter mAdapter;
    private DoneSwipeController swipeController = null;  //讓CardView能往左滑動

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_done, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDone = view.findViewById(R.id.rvDone);
        rvDone.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.networkConnected(activity)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getDoneRoom");
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
        if(updateCleanStatusTask != null) {
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
            rvDone.setAdapter(mAdapter);
        }

        //把swipe功能附加到RecyclerView上
        swipeController = new DoneSwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {  //覆寫未清掃按鈕
                super.onRightClicked(position);
                Log.d(TAG, "onRightClicked: Uncleaned");
                updateCleanStatus(position, "updateCleanStatusToUncleaned");
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(rvDone);

        rvDone.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private void updateCleanStatus(int position, String actionValue) {
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