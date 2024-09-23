package jans.repairform.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
     @RequestParam(required = false) LocalDate dateSearch){
       


        String[] cols = new String[]{"formId","incidentNo","incidentDate","diterimaNama","formStatus",""};

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
                System.out.println("dateSearch: " + dateSearch);
                list = repo.findByCreatedDate( PageRequest.of(start / length, length, Sort.by(Direction.fromString(orderDir), cols[orderCol])),dateSearch);
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
    public String pdf(Model model) {
        model.addAttribute("jasperfile", "repairform");
        return "jasper/pdf";
    }

    @GetMapping("preview")
    public ResponseEntity<Map<String, String>> preview() {
        Map<String, String> response = new HashMap<>();
        response.put("pdfUrl", "/repairform/report");
        return ResponseEntity.ok(response);
    }

    @GetMapping("report")
    public ResponseEntity<byte[]> generatePdf() {
        try {
            String reportName = "repairform";
            Map<String, Object> parameters = new HashMap<>(); 

            byte[] pdfReport = jasperService.generatePdfReport(reportName + ".jasper", parameters);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline().filename(reportName + ".pdf").build());
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            headers.setPragma("public");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfReport);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
