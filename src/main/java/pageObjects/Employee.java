package pageObjects;

import com.google.gson.annotations.Expose;

public class Employee {
    @Expose
    private int id;
    @Expose
    private String employee_name;

    public int getId() {
        return id;
    }

    public String getEmployeeName() {
        return employee_name;
    }
}
