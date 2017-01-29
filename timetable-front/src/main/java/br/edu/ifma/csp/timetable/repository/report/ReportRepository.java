package br.edu.ifma.csp.timetable.repository.report;

import java.io.Serializable;
import java.util.List;

public interface ReportRepository<T extends Serializable> {
	
	public List<T> recuperarDados(); 
}
