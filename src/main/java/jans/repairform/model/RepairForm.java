package jans.repairform.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter 
@Setter
@Table(name="repairform")
public class RepairForm {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private Integer formId;

    @Column(name = "form_status", columnDefinition = "varchar(255) default ''")
    private String formStatus;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "incident_no", columnDefinition = "varchar(255) default ''")
    private String incidentNo;

    @Column(name = "incident_date")
    private LocalDate incidentDate;

    @Column(name = "incident_type", columnDefinition = "varchar(255) default ''")
    private String incidentType;

    @Column(name = "quotation_no", columnDefinition = "varchar(255) default ''")
    private String quotationNo;

    @Column(name = "model_peralatan", columnDefinition = "varchar(255) default ''")
    private String modelPeralatan;

    @Column(name = "no_siri", columnDefinition = "varchar(255) default ''")
    private String noSiri;

    @Column(name = "diserah_nama", columnDefinition = "varchar(255) default ''")
    private String diserahNama;

    @Column(name = "diserah_bhg", columnDefinition = "varchar(255) default ''")
    private String diserahBhg;

    @Column(name = "diserah_tarikh")
    private LocalDate diserahTarikh;

    @Column(name = "diambil_nama", columnDefinition = "varchar(255) default ''")
    private String diambilNama;

    @Column(name = "diambil_bhg", columnDefinition = "varchar(255) default ''")
    private String diambilBhg;

    @Column(name = "diambil_tarikh")
    private LocalDate diambilTarikh;

    @Column(name = "masalah_pemohon", columnDefinition = "varchar(255) default ''")
    private String masalahPemohon;

    @Column(name = "penerima_nama", columnDefinition = "varchar(255) default ''")
    private String penerimaNama;

    @Column(name = "penerima_tarikh")
    private LocalDate penerimaTarikh;

    @Column(name = "juruteknik_nama", columnDefinition = "varchar(255) default ''")
    private String juruteknikNama;

    @Column(name = "juruteknik_tarikh")
    private LocalDate juruteknikTarikh;

    @Column(name = "juruteknik_masalah", columnDefinition = "varchar(255) default ''")
    private String juruteknikMasalah;

    @Column(name = "juruteknik_cara", columnDefinition = "varchar(255) default ''")
    private String juruteknikCara;

    @Column(name = "juruteknik_os", columnDefinition = "varchar(255) default ''")
    private String juruteknikOs;

    @Column(name = "juruteknik_cpu", columnDefinition = "varchar(255) default ''")
    private String juruteknikCpu;

    @Column(name = "juruteknik_ram", columnDefinition = "varchar(255) default ''")
    private String juruteknikRam;

    @Column(name = "juruteknik_hdd", columnDefinition = "varchar(255) default ''")
    private String juruteknikHdd;

    @Column(name = "ip_ori", columnDefinition = "varchar(255) default ''")
    private String ipOri;

    @Column(name = "ip_after", columnDefinition = "varchar(255) default ''")
    private String ipAfter;

    @Column(name = "ip_ditukar", columnDefinition = "varchar(255) default ''")
    private String ipDitukar;

    @Column(name = "diterima_nama", columnDefinition = "varchar(255) default ''")
    private String diterimaNama;

    @Column(name = "diterima_syarikat", columnDefinition = "varchar(255) default ''")
    private String diterimaSyarikat;

    @Column(name = "diterima_tarikh")
    private LocalDate diterimaTarikh;

    @Column(name = "dikembali_nama", columnDefinition = "varchar(255) default ''")
    private String dikembaliNama;

    @Column(name = "dikembali_syarikat", columnDefinition = "varchar(255) default ''")
    private String dikembaliSyarikat;

    @Column(name = "dikembali_tarikh")
    private LocalDate dikembaliTarikh;

    @Column(name = "pegawai_nama", columnDefinition = "varchar(255) default ''")
    private String pegawaiNama;

    @Column(name = "pegawai_tarikh")
    private LocalDate pegawaiTarikh;
    
    @PrePersist
public void prePersist() {
    // Basic form fields
    if (formStatus == null) formStatus = "";
    if (incidentNo == null) incidentNo = "";
    if (incidentType == null) incidentType = "";
    if (quotationNo == null) quotationNo = "";
    if (modelPeralatan == null) modelPeralatan = "";
    if (noSiri == null) noSiri = "";
    
    // Diserah fields
    if (diserahNama == null) diserahNama = "";
    if (diserahBhg == null) diserahBhg = "";
    
    // Diambil fields
    if (diambilNama == null) diambilNama = "";
    if (diambilBhg == null) diambilBhg = "";
    
    // Masalah and Penerima fields
    if (masalahPemohon == null) masalahPemohon = "";
    if (penerimaNama == null) penerimaNama = "";
    
    // Juruteknik fields
    if (juruteknikNama == null) juruteknikNama = "";
    if (juruteknikMasalah == null) juruteknikMasalah = "";
    if (juruteknikCara == null) juruteknikCara = "";
    if (juruteknikOs == null) juruteknikOs = "";
    if (juruteknikCpu == null) juruteknikCpu = "";
    if (juruteknikRam == null) juruteknikRam = "";
    if (juruteknikHdd == null) juruteknikHdd = "";
    
    // IP fields
    if (ipOri == null) ipOri = "";
    if (ipAfter == null) ipAfter = "";
    if (ipDitukar == null) ipDitukar = "";
    
    // Diterima fields
    if (diterimaNama == null) diterimaNama = "";
    if (diterimaSyarikat == null) diterimaSyarikat = "";
    
    // Dikembali fields
    if (dikembaliNama == null) dikembaliNama = "";
    if (dikembaliSyarikat == null) dikembaliSyarikat = "";
    
    // Pegawai fields
    if (pegawaiNama == null) pegawaiNama = "";
}

}
