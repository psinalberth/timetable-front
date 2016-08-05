package br.edu.ifma.csp.timetable.validator;

import java.lang.reflect.InvocationTargetException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.edu.ifma.csp.timetable.annotation.Codigo;
import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;

public class CodigoValidator implements ConstraintValidator<Codigo, Entidade> {
	
	@Override
	public void initialize(Codigo constraintAnnotation) {
		
		System.out.println("Running...");
	}
	
	@SuppressWarnings("all")
	@Override
	public boolean isValid(Entidade value, ConstraintValidatorContext context) {
		
		try {
			
			Repository<Entidade> repository = InitialContext.doLookup("java:module/" + value.getClass().getSimpleName() + "Dao");
			
			if (value.getClass().getDeclaredField("codigo") != null) {
				
				System.out.println("Boo!");
				String codigo = (String) value.getClass().getMethod("getCodigo", null).invoke(value, null);
				
				Entidade outro = repository.by("codigo", codigo);
				
				if (outro != null) {
					
					String outroCodigo = (String) value.getClass().getMethod("getCodigo", null).invoke(value, null);
					
					return outro.getId() == value.getId() && outroCodigo.equals(codigo); 
				}
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
