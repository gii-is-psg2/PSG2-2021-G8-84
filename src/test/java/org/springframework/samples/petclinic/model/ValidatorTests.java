package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 * when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class ValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenDescriptionEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		AdoptionRequest ar = new AdoptionRequest();
		ar.setDescription("");
		ar.setOwner(null);
		ar.setAdoption(null);

		Validator validator = createValidator();
		Set<ConstraintViolation<AdoptionRequest>> constraintViolations = validator.validate(ar);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<AdoptionRequest> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).hasToString("description");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

}
