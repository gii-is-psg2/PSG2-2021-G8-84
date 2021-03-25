package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.PetHotelRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;

@Service
public class PetHotelService {
	
	private PetRepository petRepository;
	
	private PetHotelRepository hotelRepository;
	
	@Autowired
	public PetHotelService(PetRepository petRepository,
			PetHotelRepository hotelRepository) {
		this.petRepository = petRepository;
		this.hotelRepository = hotelRepository;
	}

}
