package jans.repairform.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jans.repairform.model.RepairForm;
import java.time.LocalDate;
import java.util.List;





@Repository
public interface RepairFormRepository extends JpaRepository<RepairForm,Integer>{

    RepairForm findByFormId(Integer formId);

    @Query(value="SELECT * FROM repairform WHERE model_peralatan LIKE :value", nativeQuery = true)
    Page<RepairForm> findBySearch(Pageable pageable, String value);

    Page<RepairForm> findByFormStatus(Pageable pageable,String status);

    Page<RepairForm> findByDiserahNama(Pageable pageable,String diserahNama);

    Page<RepairForm> findByCreatedDate(Pageable pageable,LocalDate startDate);
    Page<RepairForm> findByCreatedDateBetween(Pageable pageable,LocalDate startDate,LocalDate endDate);

    @Query(value="SELECT diserah_nama FROM repairform ", nativeQuery = true)
    List<String> getDiSerahNama();
}
