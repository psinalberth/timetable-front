package br.edu.ifma.csp.timetable.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.util.Clients;

import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;
import br.edu.ifma.csp.timetable.validator.factory.TimetableValidatorFactory;

public class Validations {
	
	private static StringBuilder builder;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void validate(Entidade entidade, Repository repository) throws WrongValuesException {
		
		Validator validator = Validation.buildDefaultValidatorFactory().usingContext().constraintValidatorFactory(new TimetableValidatorFactory(repository, entidade)).getValidator();
		
		Set<ConstraintViolation<Entidade>> violations = validator.validate(entidade);
		List<WrongValueException> excecoes = new ArrayList<>();
		
		builder = new StringBuilder();
		
		for (ConstraintViolation<Entidade> cv : violations) {
			
			if (!cv.getMessage().contains("#")) {
				builder.append(cv.getMessage()).append("<br>");
			
			} else {
				builder.append(cv.getMessage().split("#")[1]).append("<br>");
			}
		}
		
		if (builder.length() > 0)
			excecoes.add(new WrongValueException(builder.toString()));
		
		if (excecoes.size() != 0) {
			throw new WrongValuesException(excecoes.toArray(new WrongValueException[0]));
		}
	}
	
	public static void showValidationErrors() {
		Clients.showNotification(builder.toString(), Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
	}
}
