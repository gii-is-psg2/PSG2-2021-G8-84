package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class PetHotelServiceTests {

	@Autowired
	protected PetHotelService hotelService;
	
	@Autowired
	protected PetService petService;
	
	@Autowired
	protected OwnerService ownerService;
	
	@Test
	void shouldValidateHotel() {
		Owner owner = this.ownerService.findOwnerById(1);
		Pet pet = this.petService.findPetById(1);
		LocalDate fechaInicio = LocalDate.of(2030, 1, 1);
		LocalDate fechaFinal = LocalDate.of(2030, 1, 5);
		Hotel hotel = new Hotel();
		hotel.setId(69);
		hotel.setOwner(owner);
		hotel.setPet(pet);
		hotel.setStartDate(fechaInicio);
		hotel.setEndDate(fechaFinal);
		
		Boolean res = !hotelService.petExistBooking(hotel)&&!hotelService.notNull(hotel)&&!hotelService.bookingOnDate(hotel, hotel.getPet().getId())&&!hotelService.validDates(hotel);
		assertThat(res).isTrue();
	}
	
	@Test
	void shouldNotValidateHotel() {
		Owner owner = this.ownerService.findOwnerById(1);
		Pet pet = this.petService.findPetById(1);
		LocalDate fechaInicio = null;
		LocalDate fechaFinal = null;
		Hotel hotel = new Hotel();
		hotel.setId(69);
		hotel.setOwner(owner);
		hotel.setPet(pet);
		hotel.setStartDate(fechaInicio);
		hotel.setEndDate(fechaFinal);
		
		Boolean res = hotelService.petExistBooking(hotel)&&hotelService.notNull(hotel)&&hotelService.bookingOnDate(hotel, hotel.getPet().getId())&&hotelService.validDates(hotel);
		assertThat(res).isFalse();
	}
}
