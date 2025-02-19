package android.com.hotelunusemployee2.delivery;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RMODAdapter extends RecyclerView.Adapter<RMODAdapter.MyViewHolder> {
    Activity activity;
    List<RoomMealOrderDetailVO> orderList;
    List<MealVO> mealList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvMealName, tvQuantity;

        public MyViewHolder(@NonNull View itemView, TextView tvMealName, TextView tvQuantity) {
            super(itemView);
            this.tvMealName = tvMealName;
            this.tvQuantity = tvQuantity;
        }
    }
}
