package program.models;

import java.util.Date;

public class Employee {
    private  int id;
    private String fullName;

    private Date dob;

    public Employee(int id, String fullName,Date dob) {
        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
    }

    public Employee(String fullName) {
        this.fullName = fullName;
    }

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSurnameAndInitials(){
        String[] result = null;
        if(fullName != null) {
            result = fullName.split(" ");
            return result[0]+" " + result[1].charAt(0)+". " +result[2].charAt(0)+".";
        }
        else return "";
    }

    @Override
    public String toString() {
        return fullName;
    }
}
