package android.com.hotelunusemployee2.room;

import java.io.Serializable;

public class RoomVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String room_id;
    private String room_type_no;
    private String room_no;
    private Integer room_status;
    private Integer clean_status;
    private String cus_id;
    private String tenant_name;
    private String tenant_phone;
    private String b_order_no;


    public RoomVO() {
        super();
    }

    public RoomVO(String room_id, String room_type_no, String room_no, Integer room_status, Integer clean_status,
                  String cus_id, String tenant_name, String tenant_phone, String b_order_no) {
        super();
        this.room_id = room_id;
        this.room_type_no = room_type_no;
        this.room_no = room_no;
        this.room_status = room_status;
        this.clean_status = clean_status;
        this.cus_id = cus_id;
        this.tenant_name = tenant_name;
        this.tenant_phone = tenant_phone;
        this.b_order_no = b_order_no;
    }


    @Override
    public String toString() {
        return "RoomVO [room_id=" + room_id + ", room_type_no=" + room_type_no + ", room_no=" + room_no
                + ", room_status=" + room_status + ", clean_status=" + clean_status + ", cus_id=" + cus_id
                + ", tenant_name=" + tenant_name + ", tenant_phone=" + tenant_phone + ", b_order_no=" + b_order_no
                + "]";
    }


    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_type_no() {
        return room_type_no;
    }

    public void setRoom_type_no(String room_type_no) {
        this.room_type_no = room_type_no;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public Integer getRoom_status() {
        return room_status;
    }

    public void setRoom_status(Integer room_status) {
        this.room_status = room_status;
    }

    public Integer getClean_status() {
        return clean_status;
    }

    public void setClean_status(Integer clean_status) {
        this.clean_status = clean_status;
    }

    public String getCus_id() {
        return cus_id;
    }

    public void setCus_id(String cus_id) {
        this.cus_id = cus_id;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getTenant_phone() {
        return tenant_phone;
    }

    public void setTenant_phone(String tenant_phone) {
        this.tenant_phone = tenant_phone;
    }

    public String getB_order_no() {
        return b_order_no;
    }

    public void setB_order_no(String b_order_no) {
        this.b_order_no = b_order_no;
    }


}
