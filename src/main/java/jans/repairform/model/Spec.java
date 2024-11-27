package jans.repairform.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Spec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spec_id")
    private Long specId;
    
    @Column(name = "spec_name")
    private String specName;
    
    @Column(name = "spec_desc", columnDefinition = "TEXT")
    private String specDesc;
    
    @ManyToOne
    @JoinColumn(name = "repair_form_id")
    private RepairForm repairForm;
} 