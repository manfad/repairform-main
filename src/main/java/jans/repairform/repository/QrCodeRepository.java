package jans.repairform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jans.repairform.model.QrCode;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode,Long>{

    
    
}
