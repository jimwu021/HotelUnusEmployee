package android.com.hotelunusemployee2.room;

import android.app.Activity;
import android.com.hotelunusemployee2.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder> {
    Activity activity;  //這邊是為了能透過Activity getResource()取得環境，以便取用顏色資源檔才接activity
    List<RoomVO> roomList;

    public RoomAdapter(Activity activity, List<RoomVO> roomList) {
        this.activity = activity;
        this.roomList = roomList;
    }


    //Adapter必備三方法: getItemCount()、onCreateViewHolder()、onBindViewHolder()
    @Override
    public int getItemCount() {  //returns how many items we want to display
        return roomList.size();
    }

    //creates viewholder and inflate the view, it’s only called when the RecyclerView needs to create the new view
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_room, parent, false);
        return new MyViewHolder(itemView);
    }

    //binding data into a viewholder, it’s called when the RecyclerView needs to fill proper data into a view
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //將資料注入到View裡
        RoomVO room = roomList.get(position);
        holder.tvRoomNo.setText(room.getRoom_no());
        holder.tvRoomNo.setTextColor(0xFF0E487B);  //深藍(Pantone color of the year 2020: Classic Blue)

        // 房間狀態碼的對應文字
        Map<Integer, String> roomStatus = new HashMap<>();
        roomStatus.put(0, "空房");
        roomStatus.put(1, "有房客");
        roomStatus.put(2, "已排房");
        roomStatus.put(3, "將離館/已排房");
        roomStatus.put(4, "將離館");
        roomStatus.put(5, "不再使用");

        //room.getRoom_status()取得int狀態碼後，透過roomStatus的get()取得Map裡相應的value
        String strRoomStatus = roomStatus.get((room.getRoom_status()));

        //接Integer記得轉成字串！！！
        holder.tvRoomStatus.setText(strRoomStatus);

        if (room.getClean_status() == 0) { //Clean Status 0 代表故障

            //在MyViewHolder新增實體變數cardView以便操控。setCardBackgroundColor()是設在cardView上就不會發生圓角被蓋掉的問題
            holder.cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.colorError));  //前綴0xFF，後面加色碼  //紅
//            holder.itemView.setBackgroundColor(0xFFFF6666); //舊的寫法直接在itemView設顏色會造成cardView的圓角設定被蓋掉
        }
        if (room.getClean_status() == 1) { //Clean Status 1 代表已清掃
            holder.cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.colorCardGrey));
        }

//        // itemView為ViewHolder內建屬性(指的就是每一列)
//        // 為itemView設定點擊後的動作
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v)
//                        .navigate(R.id.action_roomUncleanedFragment_to_roomUncleanedDetailFragment);
//            }
//        });
    }

    //內部類別繼承ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomNo, tvRoomStatus;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomNo = itemView.findViewById(R.id.tvRoomNo);
            tvRoomStatus = itemView.findViewById(R.id.tvRoomStatus);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
