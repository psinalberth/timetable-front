package br.edu.ifma.csp.timetable.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;

public class TimetableValidatorFactory implements ConstraintValidatorFactory {
	
	private Repository<Entidade> repository;
	
	public TimetableValidatorFactory(Repository<Entidade> repository) {
		this.repository = repository;
	}

	@Override
	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
		
		T instance = null;
		
		try {
			 instance = key.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		if (RepositoryValidation.class.isAssignableFrom(key)) {
			
			RepositoryValidation validation = (RepositoryValidation) instance;
			validation.setRepository(repository);
		}
		
		return instance;
	}

	@Override
	public void releaseInstance(ConstraintValidator<?, ?> instance) {
		
	}

}
