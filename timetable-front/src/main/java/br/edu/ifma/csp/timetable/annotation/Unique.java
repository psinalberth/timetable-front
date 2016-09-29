package br.edu.ifma.csp.timetable.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Retention(RUNTIME)
@Target({FIELD, METHOD})
@Constraint(validatedBy={br.edu.ifma.csp.timetable.validator.UniqueValidator.class})
public @interface Unique {
	
	String message() default "codigo#O código selecionado já está em uso.";
	Class<?>[] groups() default {};
	Class<?>[] payload() default {};
}
