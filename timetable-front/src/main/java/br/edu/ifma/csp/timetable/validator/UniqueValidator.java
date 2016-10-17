package br.edu.ifma.csp.timetable.validator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.edu.ifma.csp.timetable.annotation.Unique;
import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;

public class UniqueValidator implements ConstraintValidator<Unique, Object>, BeanConstraintValidation {
	
	private Repository<Entidade> repository;
	private Entidade entidade;
	
	@Override
	public void initialize(Unique constraintAnnotation) {
		
	}
	
	public List<Field> getAnnotatedFields() {
		
		List<Field> fields = new ArrayList<Field>();
		
		for (Field field : getEntidade().getClass().getDeclaredFields()) {
			
			if (field.isAnnotationPresent(Unique.class)) {
				fields.add(field);
			}
		}
		
		return fields;
	}
	
	public List<Method> getAnnotatedMethods() {
		
		List<Method> methods = new ArrayList<Method>();
		
		for (Method method : getEntidade().getClass().getDeclaredMethods()) {
			
			if (method.isAnnotationPresent(Unique.class)) {
				methods.add(method);
			}
		}
		
		return methods;
	}
	
	@SuppressWarnings("all")
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		if (entidade == null || repository == null || value == null)
			return true;
			
		try {
			
			List<Field> fields = getAnnotatedFields();
			
			if (fields.size() > 0) {
				
				for (Field field : fields) {
					
					field.setAccessible(true);
					
					Object fieldValue = field.get(entidade);
					
					Entidade outro = repository.by(field.getName(), fieldValue);
					
					if (outro == null)
						continue;
					
					if (outro != null) {
						
						Field outroField = outro.getClass().getDeclaredField(field.getName());
						outroField.setAccessible(true);
						
						Object outroValue = outroField.get(outro);
						
						if (fieldValue instanceof Integer) {
							return outro.getId() == entidade.getId() && ((Integer)fieldValue).equals(((Integer)outroValue)); 
						}
						
						if (fieldValue instanceof String) {
							return outro.getId() == entidade.getId() && ((String)fieldValue).equalsIgnoreCase(((String)outroValue));
						}
				
						return (outro.getId() == entidade.getId() && outroValue.equals(fieldValue));
					}
				}
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

	@Override
	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}
	
	public Entidade getEntidade() {
		return entidade;
	}
}
