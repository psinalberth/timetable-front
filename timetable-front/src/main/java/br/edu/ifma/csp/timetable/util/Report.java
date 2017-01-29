package br.edu.ifma.csp.timetable.util;

import java.util.List;

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
			
			String path = "/usr/local/eclipse/wildfly-8.2.1.Final/standalone/deployments/timetable-front.war/relatorios/";
			
			JasperReport report = JasperCompileManager.compileReport(path + file + ".jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(col));
			JasperExportManager.exportReportToPdfFile(print, path + file + ".pdf");
			
		} catch (JRException e) {
			e.printStackTrace();
		}
		
	}
}
