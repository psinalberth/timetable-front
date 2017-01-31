package br.edu.ifma.csp.timetable.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Report {
	
	public static void render(String file, List<?> col) {
		
		try {
			
			String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/relatorios");
			
			InputStream is = new FileInputStream(path + "/" + file + ".jrxml");
			
			JasperReport jasperReport = JasperCompileManager.compileReport(is);
			
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			JasperPrint print = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(col));
			
			JasperExportManager.exportReportToPdfStream(print, output);
			
			AMedia amedia = new AMedia("report", "pdf", "application/pdf", output.toByteArray());
			Filedownload.save(amedia);
			
			
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}
}
