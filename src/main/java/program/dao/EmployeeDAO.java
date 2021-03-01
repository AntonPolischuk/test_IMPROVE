package program.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import program.models.Employee;

import java.util.List;

@Component
public class EmployeeDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public EmployeeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> index(){

        return jdbcTemplate.query("SELECT * FROM Employee", new BeanPropertyRowMapper<>(Employee.class));
    }


}
