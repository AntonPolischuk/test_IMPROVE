package program.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

import java.sql.Time;
import java.sql.Date;
import java.util.List;

public class Meeting {

    private int id;
    @NotEmpty(message = "Тема не может быть пустая")
    @Size(min = 2,max = 255, message = "Длина темы не может быть менее 2 символов")
    private String topic;
    private String subdivisionOrg;
    private Employee employeeOrg;

    //@PastOrPresent(message = "Формат даты не верный")\

   // private String date;
    //@NotNull(message = "Введите дату в корректном формате - yyyy-mm-dd hh:mm:ss[.fffffffff]")
    //@DateTimeFormat (pattern="YYYY-MM-dd HH:mm")
    //@jdk.jfr.Timestamp
    private Date date;

    private Time time;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    private List<Employee> employeeList;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {


        this.count = count;
    }

    public Meeting(int id, String topic, String subdivisionOrg, Employee employeeOrg, Date date, List<Employee> employeeList) {
        this.id = id;
        this.topic = topic;
        this.subdivisionOrg = subdivisionOrg;
        this.employeeOrg = employeeOrg;
        this.date = date;
        this.employeeList = employeeList;
    }

    public Meeting() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubdivisionOrg() {
        return subdivisionOrg;
    }

    public void setSubdivisionOrg(String subdivisionOrg) {
        this.subdivisionOrg = subdivisionOrg;
    }

    public Employee getEmployeeOrg() {
        return employeeOrg;
    }

    public void setEmployeeOrg(Employee employeeOrg) {
        this.employeeOrg = employeeOrg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
