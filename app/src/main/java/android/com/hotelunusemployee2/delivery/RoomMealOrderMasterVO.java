package android.com.hotelunusemployee2.delivery;

import java.io.Serializable;
import java.sql.Date;

public class RoomMealOrderMasterVO implements Serializable {
    private String room_meal_order_no;
    private String b_order_no;
    private String room_no;
    private String emp_id;
    private Integer total_price;
    private Date order_date;
    private Integer ro_order_status;
    private String special_requirement;

    public RoomMealOrderMasterVO() {
        super();
    }

    public RoomMealOrderMasterVO(String room_meal_order_no, String b_order_no, String room_no, String emp_id,
                                 Integer total_price, Date order_date, Integer ro_order_status, String special_requirement) {
        super();
        this.room_meal_order_no = room_meal_order_no;
        this.b_order_no = b_order_no;
        this.room_no = room_no;
        this.emp_id = emp_id;
        this.total_price = total_price;
        this.order_date = order_date;
        this.ro_order_status = ro_order_status;
        this.special_requirement = special_requirement;
    }

    public String getRoom_meal_order_no() {
        return room_meal_order_no;
    }

    public void setRoom_meal_order_no(String room_meal_order_no) {
        this.room_meal_order_no = room_meal_order_no;
    }

    public String getB_order_no() {
        return b_order_no;
    }

    public void setB_order_no(String b_order_no) {
        this.b_order_no = b_order_no;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public Integer getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Integer total_price) {
        this.total_price = total_price;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public Integer getRo_order_status() {
        return ro_order_status;
    }

    public void setRo_order_status(Integer ro_order_status) {
        this.ro_order_status = ro_order_status;
    }

    public String getSpecial_requirement() {
        return special_requirement;
    }

    public void setSpecial_requirement(String special_requirement) {
        this.special_requirement = special_requirement;
    }
}
