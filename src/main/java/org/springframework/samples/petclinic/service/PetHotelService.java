package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.repository.PetHotelRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.stereotype.Service;

@Service
public class PetHotelService {

	private PetRepository petRepository;

	private PetHotelRepository hotelRepository;

	@Autowired
	public PetHotelService(PetRepository petRepository, PetHotelRepository hotelRepository) {
		this.petRepository = petRepository;
		this.hotelRepository = hotelRepository;
	}

	public Integer numberOfBookingsForPet(int petId) {
		List<Hotel> lista = new ArrayList<Hotel>();
		lista = (List<Hotel>) hotelRepository.findAll();
		return lista.stream().filter(x -> x.getPet().getId().equals(petId)).collect(Collectors.toList()).size();
	}

	public boolean validBooking(Hotel hotel) {
		if (hotel.getStartDate() != null && hotel.getEndDate()!=null) {
			Boolean numberOfBookings = numberOfBookingsForPet(hotel.getPet().getId()) <= 1;
			Boolean startDate = hotel.getStartDate().isAfter(LocalDate.now());
			Boolean endDate = hotel.getEndDate().isAfter(hotel.getStartDate());

			return numberOfBookings && startDate && endDate;
		} else {
			return false;
		}
	}

}
