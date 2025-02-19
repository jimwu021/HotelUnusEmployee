package android.com.hotelunusemployee2.delivery;

import android.app.Activity;
import android.app.Dialog;
import android.com.hotelunusemployee2.R;
import android.com.hotelunusemployee2.main.Util;
import android.com.hotelunusemployee2.task.CommonTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

class RMOMAdapter extends RecyclerView.Adapter<RMOMAdapter.MyViewHolder> {
    public static final String TAG = "RMOMAdapter";
    Activity activity;
    //    Context mContext;
    List<RoomMealOrderMasterVO> orderList;
    Dialog myDialog;
    CommonTask getOrderDetailTask;

    public RMOMAdapter(Activity activity, List<RoomMealOrderMasterVO> orderList) {
        this.activity = activity;
        this.orderList = orderList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomNo, tvMealCount;
        ConstraintLayout clItemOngoing;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomNo = itemView.findViewById(R.id.tvRoomNo);
            tvMealCount = itemView.findViewById(R.id.tvMealCount);
            clItemOngoing = itemView.findViewById(R.id.clItemOngoing);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery_ongoing, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);  //影片有加final

        //初始化Dialog
        myDialog = new Dialog(parent.getContext());  //activity、parent.getContext()
        myDialog.setContentView(R.layout.dialog_delivery_ongoing);
        myDialog.getWindow();
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.clItemOngoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViews();
                myDialog.show();
            }
        });
        return viewHolder;
    }

    private void findViews() {
        Log.d(TAG, "findViews: Enter");
        TextView tvRoomNo = myDialog.findViewById(R.id.tvRoomNo);
        TextView tvMealOrderNo = myDialog.findViewById(R.id.tvMealOrderNo);


        RecyclerView rvDialog = myDialog.findViewById(R.id.rvDialog);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        rvDialog.setLayoutManager(linearLayoutManager);

//        //加入每個item中間的分隔線
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvDialog.getContext(),
//                linearLayoutManager.getOrientation());
//        rvDialog.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RoomMealOrderMasterVO order = orderList.get(position);
        List<RoomMealOrderDetailVO> orderDetailList = null;

        holder.tvRoomNo.setText(order.getRoom_no());

        //連線Controller: RoomMealOrderServletAndroid以取得orderDetail的資料
        if (Util.networkConnected(activity)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getOrderDetail");
            jsonObject.addProperty("room_meal_order_no", order.getRoom_meal_order_no());

            getOrderDetailTask = new CommonTask(Util.URL + "RoomMealOrderServletAndroid", jsonObject.toString());

            try {
                String jsonIn = getOrderDetailTask.execute().get();
                Type listType = new TypeToken<List<RoomMealOrderDetailVO>>() {
                }.getType();
                orderDetailList = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (orderDetailList == null || orderDetailList.isEmpty()) {
                Util.showToast(activity, R.string.msg_OrderDetailNotFound);
            } else {
                int mealCount = 0;
                for (int i = 0; i < orderDetailList.size(); i++) {
                    RoomMealOrderDetailVO orderDetail = orderDetailList.get(i);
                    mealCount += orderDetail.getQuantity();
                }
                holder.tvMealCount.setText(String.valueOf(mealCount));
            }

        } else {
            Util.showToast(activity, R.string.msg_NoNetwork);
        }

//        //設定每個item被點擊後的動作
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(activity.getParent(), OngoingDetailFragment.class);
////                activity.startActivity(intent);
//            }
//        });
    }
}
