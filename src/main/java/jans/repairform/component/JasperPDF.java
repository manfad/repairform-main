package jans.repairform.component;

import java.util.Map;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

@Component("jasper/pdf")
public class JasperPDF extends AbstractView {

    @Autowired DataSource dataSource;
	
	@SuppressWarnings("null")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		try (var cn = dataSource.getConnection())
		{
			model.put(JRParameter.REPORT_TIME_ZONE, TimeZone.getTimeZone("Asia/Kuala_Lumpur"));
			
            var jasperfile = model.get("jasperfile").toString();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename="+jasperfile+".pdf");

			var inputStream = getClass().getResourceAsStream("/jasper/"+jasperfile+".jasper");

			
			var jasperReport = (JasperReport)JRLoader.loadObject(inputStream);
			var jasperPrint = JasperFillManager.fillReport(jasperReport, model, cn);
			
			var exporter = new JRPdfExporter();
			
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
	
			SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
			reportConfig.setSizePageToContent(true);
			reportConfig.setForceLineBreakPolicy(false);
			
			
			var exporterConfig = new SimplePdfExporterConfiguration();
		
			exporter.setConfiguration(reportConfig);
			exporter.setConfiguration(exporterConfig);
			
			exporter.exportReport();
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
    
}