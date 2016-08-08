package br.edu.ifma.csp.timetable.validator;

import java.lang.reflect.Field;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.edu.ifma.csp.timetable.annotation.Codigo;
import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;

public class CodigoValidator implements ConstraintValidator<Codigo, Entidade> {
	
	private String codigo;
	
	@Override
	public void initialize(Codigo constraintAnnotation) {
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
			f.setAccessible(true);
			
			String codigo = (String) f.get(value);
			Entidade outro = repository.by(this.codigo, codigo);
			
			if (outro != null) {
				
				f = outro.getClass().getDeclaredField(this.codigo);
				f.setAccessible(true);
						
				String outroCodigo = (String) f.get(outro);
		
				return outro.getId() == value.getId() && outroCodigo.equals(codigo);
			}
		
		} catch (NamingException ex) {
			ex.printStackTrace();
			
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			
		} catch (SecurityException e) {
			e.printStackTrace();
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			
		}
		
		return true;
	}
}
