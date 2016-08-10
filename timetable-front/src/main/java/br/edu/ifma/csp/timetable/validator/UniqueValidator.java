package br.edu.ifma.csp.timetable.validator;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.edu.ifma.csp.timetable.annotation.Unique;
import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;

public class UniqueValidator implements ConstraintValidator<Unique, Entidade>, RepositoryValidation {
	
	private String codigo;
	private Repository<Entidade> repository;
	
	@Override
	public void initialize(Unique constraintAnnotation) {
		this.codigo = constraintAnnotation.columnName();
	}
	
	@SuppressWarnings("all")
	@Override
	public boolean isValid(Entidade value, ConstraintValidatorContext context) {
		
		if (value == null || repository == null)
			return true;
			
		try {
			
			Field f = value.getClass().getDeclaredField(this.codigo);
			f.setAccessible(true);
			
			String codigo = (String) f.get(value);
			
			if (repository == null)
				return true;
			
			Entidade outro = repository.by(this.codigo, codigo);
			
			if (outro == null)
				return true;
			
			if (outro != null) {
				
				f = outro.getClass().getDeclaredField(this.codigo);
				f.setAccessible(true);
						
				String outroCodigo = (String) f.get(outro);
		
				return outro.getId() == value.getId() && outroCodigo.equals(codigo);
			} else {
				
				return true;
			}
					
		} catch (NoSuchFieldException e) {
			return true;
			
		} catch (SecurityException e) {
			e.printStackTrace();
			
		} catch (IllegalArgumentException e) {
			return true;
			
		} catch (IllegalAccessException e) {
			return true;
			
		}
		
		return true;
	}

	@Override
	public void setRepository(Repository<Entidade> repository) {
		this.repository = repository;
	}
}
