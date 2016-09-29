package br.edu.ifma.csp.timetable.validator.factory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;
import br.edu.ifma.csp.timetable.validator.BeanConstraintValidation;

public class TimetableValidatorFactory implements ConstraintValidatorFactory {
	
	private Repository<Entidade> repository;
	private Entidade entidade;
	
	public TimetableValidatorFactory(Repository<Entidade> repository, Entidade entidade) {
		this.repository = repository;
		this.entidade = entidade;
	}

	@Override
	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
		
		T instance = null;
		
		try {
			 instance = key.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		if (BeanConstraintValidation.class.isAssignableFrom(key)) {
			
			BeanConstraintValidation validation = (BeanConstraintValidation) instance;
			validation.setRepository(repository);
			validation.setEntidade(entidade);
		}
		
		return instance;
	}

	@Override
	public void releaseInstance(ConstraintValidator<?, ?> instance) {
		
	}

}
