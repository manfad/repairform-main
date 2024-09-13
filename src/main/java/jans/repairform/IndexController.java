package jans.repairform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jans.repairform.repository.RepairFormRepository;
import jans.repairform.service.RepairFormService;


@Controller
public class IndexController {

    @Autowired RepairFormService service;
    @Autowired RepairFormRepository repo;

    
    @GetMapping("/")
    public String index(Model model) {

        return "index";
    }

    
}
