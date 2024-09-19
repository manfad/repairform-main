package jans.repairform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jans.repairform.model.RepairForm;


@Repository
public interface RepairFormRepository extends JpaRepository<RepairForm,Integer>{
    
    RepairForm findByFormId(Integer formId);
}
