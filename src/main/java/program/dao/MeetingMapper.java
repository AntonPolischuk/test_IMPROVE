package program.dao;

import org.springframework.jdbc.core.RowMapper;
import program.models.Employee;
import program.models.Meeting;
import program.models.Subdivision;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeetingMapper  implements  RowMapper<Meeting>{
        @Override
        public Meeting mapRow(ResultSet resultSet, int i) throws SQLException {
            Meeting meeting = new Meeting();

            meeting.setDate(resultSet.getDate("date"));
            meeting.setTime(resultSet.getTime("time"));
            meeting.setId(resultSet.getInt("id"));
            meeting.setTopic(resultSet.getString("topic"));
            meeting.setSubdivisionOrg(resultSet.getString("name"));
            meeting.setEmployeeOrg(new Employee(resultSet.getString("nameorg")));
            meeting.setCount(resultSet.getInt("count"));

            return meeting;
        }
    }


