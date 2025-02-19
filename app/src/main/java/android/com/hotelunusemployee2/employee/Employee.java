package android.com.hotelunusemployee2.employee;

public class Employee implements java.io.Serializable {
    private String emp_id;
    private String password;

    public Employee() {
    }

    public Employee(String emp_id, String password) {
        this.emp_id = emp_id;
        this.password = password;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
