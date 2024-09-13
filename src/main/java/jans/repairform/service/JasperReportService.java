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
        // Load the compiled Jasper file from the resources folder
        InputStream jasperStream = getClass().getResourceAsStream("/jasper/" + jasperFileName);
        System.out.println("/jasper/"+jasperFileName);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

        // Empty data source, use a real data source if needed
        JRDataSource dataSource = new JREmptyDataSource();

        // Fill the report with parameters and data source
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export the filled report to a PDF
        byte[] pdfReport = JasperExportManager.exportReportToPdf(jasperPrint);

        return pdfReport;
    }
}
