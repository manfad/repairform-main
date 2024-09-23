package jans.repairform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jans.repairform.repository.RepairFormRepository;
import jans.repairform.service.RepairFormService;
import jans.repairform.service.UserService;


@Controller
public class IndexController {

    @Autowired RepairFormService service;
    @Autowired RepairFormRepository repo;
    @Autowired UserService userService;

    
    @GetMapping("/")
    public String index(Model model) {

        String name = userService.getCurrentUsername();
        model.addAttribute("name", name);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {

        return "login";
    }

    
}
