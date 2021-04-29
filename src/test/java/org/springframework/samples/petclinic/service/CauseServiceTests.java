package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CauseServiceTests {

	@Autowired
	protected CauseService causeService;
	
	@Test
	void shouldFindCauses() {
		Collection<Cause> causes = this.causeService.findCauses();
		assertThat(causes).hasSize(4);
	}
	
	@Test
	void shouldFindUnfinishedCauses() {
		Collection<Cause> causes = this.causeService.findUnfinishedCauses();
		assertThat(causes).hasSize(3);
	}
	
	@Test
	void shouldFindCauseById() {
		int id = 1;
		Cause cause = this.causeService.findCauseById(id);
		assertThat(cause.getName()).isEqualTo("Construccion de refugios");
	}

}
