package program.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import program.dao.MeetingDAO;
import program.models.Employee;
import program.models.Meeting;
import program.models.Subdivision;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/2")
public class MeetingController {

    private final MeetingDAO meetingDAO;

    @Autowired
    public MeetingController(MeetingDAO meetingDAO) {
        this.meetingDAO = meetingDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("meeting", meetingDAO.showMeeting());
        model.addAttribute("subdivision", meetingDAO.getSubdivisionList());
        model.addAttribute("employee", meetingDAO.getEmployeeList());
        model.addAttribute("map", new HashMap<>());
        return "meetingView";
    }

    @GetMapping("/filter")
    public String filterView(Model model, @RequestParam("topic") String topic, @RequestParam("afterDate") String afterDate,
                             @RequestParam("beforeDate") String beforeDate, @RequestParam("subdivision") String subdivision,
                             @ModelAttribute("employee") Employee employee) {
        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("topic", topic);
        filterMap.put("afterDate", afterDate);
        filterMap.put("beforeDate", beforeDate);
        filterMap.put("subdivision", subdivision);
        filterMap.put("employee", employee);
        model.addAttribute("meeting", meetingDAO.searchByFilters(topic, afterDate, beforeDate, subdivision, employee));
        model.addAttribute("subdivision", meetingDAO.getSubdivisionList());
        model.addAttribute("employee", meetingDAO.getEmployeeList());
        model.addAttribute("map", filterMap);

        return "meetingView";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("meeting", meetingDAO.showById(id));
        model.addAttribute("subdivision", meetingDAO.getSubdivisionList());
        return "view";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,Model model, @ModelAttribute("meeting") @Valid Meeting meeting, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            meeting.setTime(new Time(0));
            model.addAttribute("subdivision", meetingDAO.getSubdivisionList());
            return "view";

        } else {
            meetingDAO.update(id, meeting);

            return "redirect:/2/" + id;
        }
    }
}
