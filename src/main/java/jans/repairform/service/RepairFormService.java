package jans.repairform.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jans.repairform.model.RepairForm;
import jans.repairform.model.Response;  
import jans.repairform.repository.RepairFormRepository;

@Service
public class RepairFormService {

    @Autowired RepairFormRepository repo;

    public List<RepairForm> findAll(){
        return repo.findAll();      
    }

   
    public Response save(RepairForm form){

        Response res = new Response();
        RepairForm newData ;

        if(form.getDiserahBhg() == null || form.getDiserahBhg().trim().isEmpty()) {
            res.setMessage("Missing input");
            return res;
        }
        
        if(form.getDiserahNama() == null || form.getDiserahNama().trim().isEmpty()) {
            res.setMessage("Missing input");
            return res;
        }
        
        if(form.getModelPeralatan() == null || form.getModelPeralatan().trim().isEmpty()) {
            res.setMessage("Missing input");
            return res;
        }

        if(form.getFormId() != null){
            newData = repo.findByFormId(form.getFormId());
            
        }else{
            newData = new RepairForm();
            newData.setCreatedDate(LocalDate.now());
        }

        newData.setFormStatus("N");
        newData.setIncidentNo(form.getIncidentNo());
        newData.setIncidentDate(form.getIncidentDate());
        newData.setIncidentType(form.getIncidentType());
        newData.setQuotationNo(form.getQuotationNo());
        newData.setModelPeralatan(form.getModelPeralatan());
        newData.setNoSiri(form.getNoSiri());
        newData.setDiserahNama(form.getDiserahNama());
        newData.setDiserahBhg(form.getDiserahBhg());
        newData.setDiserahTarikh(form.getDiserahTarikh());
        newData.setDiambilNama(form.getDiambilNama());
        newData.setDiambilBhg(form.getDiambilBhg());
        newData.setDiambilTarikh(form.getDiambilTarikh());
        newData.setMasalahPemohon(form.getMasalahPemohon());
        newData.setPenerimaNama(form.getPenerimaNama());
        newData.setPenerimaTarikh(form.getPenerimaTarikh());
        newData.setJuruteknikNama(form.getJuruteknikNama());
        newData.setJuruteknikTarikh(form.getJuruteknikTarikh());
        newData.setJuruteknikMasalah(form.getJuruteknikMasalah());
        newData.setJuruteknikCara(form.getJuruteknikCara());
        newData.setJuruteknikOs(form.getJuruteknikOs());
        newData.setJuruteknikCpu(form.getJuruteknikCpu());
        newData.setJuruteknikRam(form.getJuruteknikRam());
        newData.setJuruteknikHdd(form.getJuruteknikHdd());
        newData.setIpOri(form.getIpOri());
        newData.setIpAfter(form.getIpAfter());
        newData.setIpDitukar(form.getIpDitukar());
        newData.setDiterimaNama(form.getDiterimaNama());
        newData.setDiterimaSyarikat(form.getDiterimaSyarikat());
        newData.setDiterimaTarikh(form.getDiterimaTarikh());
        newData.setDikembaliNama(form.getDikembaliNama());
        newData.setDikembaliSyarikat(form.getDikembaliSyarikat());
        newData.setDikembaliTarikh(form.getDikembaliTarikh());
        newData.setPegawaiNama(form.getPegawaiNama());
        newData.setPegawaiTarikh(form.getPegawaiTarikh());

        repo.save(newData);
        res.setMessage("Succesfully");
        res.setSuccess(true);
        return res;

    }
    public Response complete(Integer param){

        Response res = new Response();
        RepairForm form = repo.findByFormId(param);
        if(form != null){
            form.setFormStatus("C");
            repo.save(form);
            res.setMessage("Succesfully");
            res.setSuccess(true);
        }else{
            res.setMessage("Form is not found");
        }
        return res;
    }

    public Response delete(Integer param){

        Response res = new Response();
        RepairForm form = repo.findByFormId(param);
        if(form != null){
            repo.delete(form);
            res.setMessage("Succesfully");
            res.setSuccess(true);
        }else{
            res.setMessage("Form is not found");
        }
        return res;
    }
    
}
