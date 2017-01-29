package br.edu.ifma.csp.timetable.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Lookup {
	
	@SuppressWarnings("unchecked")
	public static<T extends Object>T dao(Class<T> clazz) {
		
		try {
			
			return (T) InitialContext.doLookup("java:module/" + clazz.getSimpleName());
		   
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
