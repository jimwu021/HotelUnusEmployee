package android.com.hotelunusemployee2.delivery;

import java.io.Serializable;

public class RoomMealOrderDetailVO implements Serializable {
    private String room_meal_order_no;
    private String meal_no;
    private Integer quantity;
    private Integer price;

    public RoomMealOrderDetailVO() {
        super();
    }

    public RoomMealOrderDetailVO(String room_meal_order_no, String meal_no, Integer quantity, Integer price,
                                 Integer meal_status) {
        super();
        this.room_meal_order_no = room_meal_order_no;
        this.meal_no = meal_no;
        this.quantity = quantity;
        this.price = price;
    }

    public String getRoom_meal_order_no() {
        return room_meal_order_no;
    }

    public void setRoom_meal_order_no(String room_meal_order_no) {
        this.room_meal_order_no = room_meal_order_no;
    }

    public String getMeal_no() {
        return meal_no;
    }

    public void setMeal_no(String meal_no) {
        this.meal_no = meal_no;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
