package jans.repairform.repository;

import jans.repairform.model.Spec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecRepository extends JpaRepository<Spec, Long> {
    Spec findByRepairFormFormId(Long formId);
} 