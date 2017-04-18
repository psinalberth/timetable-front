package br.edu.ifma.csp.timetable.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Report {
	
	public static void render(String file, List<?> col) {
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/relatorios");
			
			MysqlDataSource ds = new MysqlDataSource();
			
			FileInputStream fis = new FileInputStream(Executions.getCurrent().getDesktop().getWebApp().getRealPath("/WEB-INF/classes/configuration/db.properties"));
			
			Properties properties = new Properties();
			properties.load(fis);
			
			ds.setURL(properties.getProperty("MYSQL_DB_URL"));
			ds.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
			ds.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
			
			Connection connection = ds.getConnection();
			
			params.put("SUBREPORT_DIR", path + "/");
			params.put("CONNECTION", connection);
			
			InputStream is = new FileInputStream(path + "/" + file + ".jasper");
			
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			JasperPrint print = JasperFillManager.fillReport(is, params, new JRBeanCollectionDataSource(col));
			
			JasperExportManager.exportReportToPdfStream(print, output);
			
			AMedia amedia = new AMedia("report", "pdf", "application/pdf", output.toByteArray());
			Filedownload.save(amedia);
			
		} catch (JRException | IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
