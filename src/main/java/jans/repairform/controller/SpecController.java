package jans.repairform.controller;

import jans.repairform.model.Response;
import jans.repairform.model.Spec;
import jans.repairform.repository.SpecRepository;
import jans.repairform.repository.RepairFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/spec")
public class SpecController {
    
    @Autowired private SpecRepository specRepository;
    
    @Autowired private RepairFormRepository repairFormRepository;
    
    @PostMapping("/save")
    @ResponseBody
    public Response saveSpec(@RequestBody Spec param) {
        Response res = new Response();
        try {
            Spec spec = new Spec();
            spec.setSpecName(param.getSpecName());
            spec.setSpecDesc(param.getSpecDesc());
            spec.setRepairForm(repairFormRepository.findByFormId(param.getRepairForm().getFormId()));
            
            specRepository.save(spec);
            
            res.setSuccess(true); 
            res.setMessage("Spec saved successfully");
            return res;
        } catch (Exception e) {
            res.setSuccess(false);
            res.setMessage(e.getMessage());
            return res;
        }
    }

    @GetMapping("/get/{formId}")
    @ResponseBody
    public Spec getSpecsByFormId(@PathVariable Long formId) {
        return specRepository.findByRepairFormFormId(formId);
    }
} 