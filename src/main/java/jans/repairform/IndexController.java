package jans.repairform;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jans.repairform.model.User;
import jans.repairform.repository.RepairFormRepository;
import jans.repairform.repository.UserRepository;
import jans.repairform.service.RepairFormService;
import jans.repairform.service.UserService;


@Controller
public class IndexController {

    @Autowired RepairFormService service;
    @Autowired RepairFormRepository repo;
    @Autowired UserService userService;
    @Autowired UserRepository userRepo;

    
    @GetMapping("/")
    public String index(Model model) {

        String name = userService.getCurrentUsername();
        model.addAttribute("name", name);
        List<String> diserahNama = repo.getDiSerahNama();
        model.addAttribute("diserahNama", diserahNama);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {

        return "login";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        User existingUser = userRepo.findByUsername(user.getUsername());

        if (existingUser != null) {
            model.addAttribute("error", "Username already exists!");
            return "register";  
        }
        userRepo.save(user);
        return "redirect:/register?success";
    }
    @GetMapping("newform")
    public String reqFormPage(Model model) {
        boolean user = userService.isLoggedIn();
        model.addAttribute("user", user);
        return "new_form";
    }
    
}
