package jans.repairform.service;

import java.io.InputStream;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class JasperReportService {
    
    // Method to generate PDF from .jasper file
    public byte[] generatePdfReport(String jasperFileName, Map<String, Object> parameters) throws JRException {

        InputStream jasperStream = getClass().getResourceAsStream("/jasper/" + jasperFileName);
        System.out.println("/jasper/"+jasperFileName);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JRDataSource dataSource = new JREmptyDataSource();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        byte[] pdfReport = JasperExportManager.exportReportToPdf(jasperPrint);

        return pdfReport;
    }
}
