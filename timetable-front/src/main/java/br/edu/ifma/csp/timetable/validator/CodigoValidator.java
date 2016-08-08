package br.edu.ifma.csp.timetable.validator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.edu.ifma.csp.timetable.annotation.Codigo;
import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;

public class CodigoValidator implements ConstraintValidator<Codigo, Entidade> {
	
	Repository<Entidade> repository;
	private String codigo;
	
	@Override
	public void initialize(Codigo constraintAnnotation) {
		
		System.out.println("Running... " + constraintAnnotation.getClass());
		this.codigo = constraintAnnotation.columnName();
	}
	
	@SuppressWarnings("all")
	@Override
	public boolean isValid(Entidade value, ConstraintValidatorContext context) {
		
		if (value == null)
			return true;
			
		try {
			
			Repository<Entidade> repository = InitialContext.doLookup("java:module/" + value.getClass().getSimpleName() + "Dao");
			
			Field f = value.getClass().getDeclaredField(this.codigo);
			
			System.out.println(f.isAccessible());
			
			f.setAccessible(true);
			
			String codigo = (String) f.get(value);
			
			/*List<Entidade> re = repository.all();
			
			if (repository.getSession().isOpen())
				repository.getSession().close();*/
			
			
			Entidade outro = repository.by(this.codigo, codigo);
			
			if (outro != null) {
				
				f = outro.getClass().getDeclaredField(this.codigo);
				f.setAccessible(true);
				
				
				
				String outroCodigo = (String) f.get(outro);
				
				/*if (outroCodigo == null)
					return true;
				
				if (outro.getId() == value.getId() && outroCodigo.equals(codigo))
					return true;
				
				return false; */
				return outro.getId() == value.getId() && outroCodigo.equals(codigo);
			}
		
		} catch (NamingException ex) {
			ex.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
}
