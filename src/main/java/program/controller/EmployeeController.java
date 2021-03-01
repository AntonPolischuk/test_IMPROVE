package program.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import program.dao.EmployeeDAO;

@Controller
@RequestMapping("/1")
public class EmployeeController {

    private final EmployeeDAO employeeDAO;
    @Autowired
    public EmployeeController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("employee", employeeDAO.index());
        return "view";
}

}
