package jans.repairform.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RepairForm {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer formId;
    private String status;
    private String incidentNo;
    private LocalDate incidentDate;
    private String incidentType;
    private String quotationNo;
    private String modelPeralatan;
    private String noSiri;
    private String diserahNama;
    private String diserahBhg;
    private LocalDate diserahTarikh;
    private String diambilNama;
    private String diambilBhg;
    private LocalDate diambilTarikh;
    private String masalah;
    private String penerimaNama;
    private LocalDate penerimaTarikh;
    private String juruteknikNama;
    private LocalDate juruteknikTarikh;
    private String juruteknikMasalah;
    private String juruteknikCara;
    private String juruteknikOs;
    private String juruteknikCpu;
    private String juruteknikRam;
    private String juruteknikHdd;
    private String ipOri;
    private String ipAfter;
    private String ipDitukar;
    private String diterimaNama;
    private String diterimaSyarikat;
    private LocalDate diterimaTarikh;
    private String dikembaliNama;
    private String dikembaliSyarikat;
    private LocalDate dikembaliTarikh;
    private String pegawaiNama;
    private LocalDate pegawaiTarikh;
    

}
