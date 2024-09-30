package jans.repairform.controller;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jans.repairform.model.DataTable;
import jans.repairform.model.RepairForm;
import jans.repairform.model.Response;
import jans.repairform.repository.RepairFormRepository;
import jans.repairform.service.JasperReportService;
import jans.repairform.service.RepairFormService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
@RequestMapping("/repairform")
public class RepairFormController {
    
    @Autowired RepairFormService service;
    @Autowired JasperReportService jasperService;
    @Autowired RepairFormRepository repo;

    @GetMapping("fetch")
	public @ResponseBody DataTable<RepairForm> fetch(int start, int length,
     @RequestParam(value = "search[value]") String search, 
     @RequestParam("order[0][column]") Integer orderCol, 
     @RequestParam("order[0][dir]") String orderDir,
     @RequestParam String condition,
     @RequestParam(required = false) LocalDate startDate,
     @RequestParam(required = false) LocalDate endDate,
     @RequestParam(required = false) String diserahSearch){
       


        String[] cols = new String[]{"incidentNo","incidentDate","diserahNama","formStatus",""};

        DataTable<RepairForm> table = new DataTable<>();
        Page<RepairForm> list;
        if(!search.isEmpty()){
            list = repo.findBySearch(PageRequest.of(start / length, length), '%'+search+'%');
        }else{
            if (condition.equals("new")) {
                list = repo.findByCreatedDate( PageRequest.of(start / length, length, Sort.by(Direction.fromString(orderDir), cols[orderCol])),LocalDate.now());
            } else if (condition.equals("complete")) {
                list = repo.findByFormStatus( PageRequest.of(start / length, length, Sort.by(Direction.fromString(orderDir), cols[orderCol])),"C");
            } else if (condition.equals("date")) {
                list = repo.findByCreatedDateBetween( PageRequest.of(start / length, length, Sort.by(Direction.fromString(orderDir), cols[orderCol])),startDate,endDate);
            } else if (condition.equals("diserah")) {
                list = repo.findByDiserahNama( PageRequest.of(start / length, length, Sort.by(Direction.fromString(orderDir), cols[orderCol])),diserahSearch);
            } else {
                list = repo.findAll(PageRequest.of(start / length, length, Sort.by(Direction.fromString(orderDir), cols[orderCol])));
            }
        }

        table.setData(list.getContent());
        table.setRecordsFiltered(list.getTotalElements());
        table.setRecordsTotal(list.getTotalElements());
        return table;
       
	}

    @PostMapping("save")
    public @ResponseBody Response save(@RequestBody RepairForm param) {
        return service.save(param);
    }

    @GetMapping("complete")
    public @ResponseBody Response complete(@RequestParam Integer param) {
        return service.complete(param);
    }

    @GetMapping("getDetail")
    public @ResponseBody RepairForm getDetail(@RequestParam Integer param) {
        return repo.findByFormId(param);
    }

    @PostMapping("delete")
    public @ResponseBody Response delete(@RequestParam Integer param) {
        return service.delete(param);
    }

    @GetMapping("pdf")
    public String pdf(Model model,@RequestParam String formid) {
        System.out.println("formid = " +formid);
        model.addAttribute("jasperfile", "repairform");
        model.addAttribute("formid", formid);
        return "jasper/pdf";
    }

    
}
