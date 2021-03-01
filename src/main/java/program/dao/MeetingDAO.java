package program.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import program.models.Employee;
import program.models.Meeting;
import program.models.Subdivision;

import java.util.Date;
import java.util.List;

@Component
public class MeetingDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public MeetingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Meeting> showMeeting(){

        return jdbcTemplate.query("SELECT time, meeting.id, meeting.date, meeting.topic, subdivision.name,employee.\"fullName\" as nameorg,\n" +
                "(SELECT  count(listofparticipants.employee_id) FROM listofparticipants WHERE meeting.id=listofparticipants.meeting_id) as count, count(e2.\"fullName\") as participants\n" +
                "FROM meeting\n" +
                "left join subdivision s on meeting.subdivision_id = s.id\n" +
                "left join subdivision on s.name=subdivision.name\n" +
                "left join employee e on meeting.employee_org = e.id\n" +
                "left join employee on e.\"fullName\"=employee.\"fullName\"\n" +
                "left join listofparticipants l on meeting.id = l.meeting_id\n" +
                "left join employee e2 on l.employee_id=e2.id\n" +
                "group by  time, meeting.id, meeting.date, meeting.topic, subdivision.name, nameorg, count;", new MeetingMapper());

    }

    public List<String> getSubdivisionList(){
        return jdbcTemplate.queryForList("SELECT name FROM subdivision;", String.class);
    }

    public List<Employee> getEmployeeList(){
        return jdbcTemplate.query("SELECT * FROM employee", new BeanPropertyRowMapper<>(Employee.class));
    }

    public List<Meeting> searchByFilters(String topic, String afterDate, String beforeDate, String subdivision, Employee participant){

        return jdbcTemplate.query("SELECT time , meeting.id, meeting.date, meeting.topic, subdivision.name,employee.\"fullName\" as nameorg,\n" +
                "(SELECT  count(listofparticipants.employee_id) FROM listofparticipants WHERE meeting.id=listofparticipants.meeting_id) as count, count(e2.\"fullName\") as participants\n" +
                "FROM meeting\n" +
                "left join subdivision s on meeting.subdivision_id = s.id\n" +
                "left join subdivision on s.name=subdivision.name\n" +
                "left join employee e on meeting.employee_org = e.id\n" +
                "left join employee on e.\"fullName\"=employee.\"fullName\"\n" +
                "left join listofparticipants l on meeting.id = l.meeting_id\n" +
                "left join employee e2 on l.employee_id=e2.id\n" +
                queryConstructor(topic, afterDate, beforeDate, subdivision, participant) +
                "group by time, meeting.id, meeting.date, meeting.topic, subdivision.name, nameorg, count;", new MeetingMapper());

    }

    private String queryConstructor(String topic, String afterDate, String beforeDate, String subdivision, Employee participant){
        //WHERE topic LIKE '%%' AND date>'2020/09/09' AND date<'2020/11/11' AND subdivision.name='SALES' AND e2."fullName"='Петров Петр Петрович'
        StringBuilder query = new StringBuilder();
        if(topic==null)
        query.append("WHERE topic LIKE '%%' ");
        else
            query.append("WHERE topic LIKE '%"+topic+"%' ");
        if(!afterDate.equals(""))
            query.append("AND date>'"+ afterDate +"' ");
        if(!beforeDate.equals(""))
            query.append("AND date<'"+ beforeDate +"' ");
        if(!subdivision.equals(""))
            query.append("AND subdivision.name='" + subdivision +"' ");
        if(participant.getFullName()!=null)
            query.append("AND e2.\"fullName\"='" + participant.getFullName()+"'");

        return query.toString();
    }

    private String searchParticipant(String participant){
        String[] result = participant.split(" ");
        return "'" + result[0]+"%"+result[1].charAt(0)+"%" + result[2].charAt(0)+"%" +"'";
    }

    public Meeting showById(int id) {
        return  jdbcTemplate.queryForObject("SELECT time, meeting.id, topic, date, subdivision.name as subdivisionOrg, employee.\"fullName\" as employeeOrg FROM meeting\n" +
                "left join employee e on meeting.employee_org = e.id\n" +
                "left join employee on e.\"fullName\"=employee.\"fullName\"\n" +
                "left join subdivision s on meeting.subdivision_id = s.id\n" +
                "left join subdivision on s.name=subdivision.name WHERE meeting.id="+id+";", new BeanPropertyRowMapper<>(Meeting.class));
    }

    public void update(int id, Meeting meeting) {

        jdbcTemplate.update("UPDATE meeting SET topic=?, subdivision_id=(SELECT subdivision.id from subdivision where subdivision.name=?), date=?, time=?::time WHERE id=?", meeting.getTopic(), meeting.getSubdivisionOrg(), meeting.getDate(), meeting.getTime(), id);

        //UPDATE meeting SET topic='очень важная тема', subdivision_id=(SELECT subdivision.id from subdivision where subdivision.name='SALES') WHERE id=2;

    }
}
