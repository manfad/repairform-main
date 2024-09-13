package jans.repairform.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "status")
    private String status;

    @Column(name = "incident_no")
    private String incidentNo;

    @Column(name = "incident_date")
    private LocalDate incidentDate;

    @Column(name = "incident_type")
    private String incidentType;

    @Column(name = "quotation_no")
    private String quotationNo;

    @Column(name = "model_peralatan")
    private String modelPeralatan;

    @Column(name = "no_siri")
    private String noSiri;

    @Column(name = "diserah_nama")
    private String diserahNama;

    @Column(name = "diserah_bhg")
    private String diserahBhg;

    @Column(name = "diserah_tarikh")
    private LocalDate diserahTarikh;

    @Column(name = "diambil_nama")
    private String diambilNama;

    @Column(name = "diambil_bhg")
    private String diambilBhg;

    @Column(name = "diambil_tarikh")
    private LocalDate diambilTarikh;

    @Column(name = "masalah_pemohon")
    private String masalahPemohon;

    @Column(name = "penerima_nama")
    private String penerimaNama;

    @Column(name = "penerima_tarikh")
    private LocalDate penerimaTarikh;

    @Column(name = "juruteknik_nama")
    private String juruteknikNama;

    @Column(name = "juruteknik_tarikh")
    private LocalDate juruteknikTarikh;

    @Column(name = "juruteknik_masalah")
    private String juruteknikMasalah;

    @Column(name = "juruteknik_cara")
    private String juruteknikCara;

    @Column(name = "juruteknik_os")
    private String juruteknikOs;

    @Column(name = "juruteknik_cpu")
    private String juruteknikCpu;

    @Column(name = "juruteknik_ram")
    private String juruteknikRam;

    @Column(name = "juruteknik_hdd")
    private String juruteknikHdd;

    @Column(name = "ip_ori")
    private String ipOri;

    @Column(name = "ip_after")
    private String ipAfter;

    @Column(name = "ip_ditukar")
    private String ipDitukar;

    @Column(name = "diterima_nama")
    private String diterimaNama;

    @Column(name = "diterima_syarikat")
    private String diterimaSyarikat;

    @Column(name = "diterima_tarikh")
    private LocalDate diterimaTarikh;

    @Column(name = "dikembali_nama")
    private String dikembaliNama;

    @Column(name = "dikembali_syarikat")
    private String dikembaliSyarikat;

    @Column(name = "dikembali_tarikh")
    private LocalDate dikembaliTarikh;

    @Column(name = "pegawai_nama")
    private String pegawaiNama;

    @Column(name = "pegawai_tarikh")
    private LocalDate pegawaiTarikh;
    
    

}
