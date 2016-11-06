package br.edu.ifma.csp.timetable.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.zkoss.bind.Binder;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.WrongValuesException;

import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;
import br.edu.ifma.csp.timetable.validator.factory.TimetableValidatorFactory;

public class Validations {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void validate(Binder binder, Entidade entidade, Repository repository) {
		
		Validator validator = Validation.buildDefaultValidatorFactory().usingContext().constraintValidatorFactory(new TimetableValidatorFactory(repository, entidade)).getValidator();
		
		Set<ConstraintViolation<Entidade>> violations = validator.validate(entidade);
		List<WrongValueException> excecoes = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
		
		for (ConstraintViolation<Entidade> cv : violations) {
			
			if (!cv.getMessage().contains("#")) {
				sb.append(cv.getMessage() + "\n");
			
			} else {
				sb.append(cv.getMessage().split("#")[1] + "\n");
			}
		}
		
		if (sb.length() > 0)
			excecoes.add(new WrongValueException(sb.toString()));
		
		if (excecoes.size() != 0) {
			throw new WrongValuesException(excecoes.toArray(new WrongValueException[0]));
		}
	}
}
