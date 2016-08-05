package br.edu.ifma.csp.timetable.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Documented
@Retention(RUNTIME)
@Target({TYPE})
@Constraint(validatedBy=br.edu.ifma.csp.timetable.validator.CodigoValidator.class)
public @interface Codigo {
	
	String columnName();
	String message() default "O código selecionado já está em uso.";
	Class<?>[] groups() default {};
	Class<?>[] payload() default {};
}
