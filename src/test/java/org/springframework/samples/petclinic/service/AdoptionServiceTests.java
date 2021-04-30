package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class AdoptionServiceTests {
	
	@Autowired
	protected AdoptionService adoptionService;
	
	@Autowired
	protected OwnerService ownerService;
	
	@Test
	void shouldFindMyAdoptions() {
		Owner owner = this.ownerService.findOwnerById(1);
		Collection<Adoption> myAdoptions = this.adoptionService.findMyAdoptions(owner);
		assertThat(myAdoptions).hasSize(1);
	}
	
	@Test
	void shouldFindOtherAdoptions() {
		Owner owner = this.ownerService.findOwnerById(1);
		Collection<Adoption> otherAdoptions = this.adoptionService.findOtherAdoptions(owner);
		assertThat(otherAdoptions).hasSize(3);
	}
	
	@Test
	void shouldFindAdoptionById() {
		Adoption adoption = this.adoptionService.findAdoptionById(1);

		assertThat(adoption.getAdoptionRequests()).hasSize(1);
	}

}
