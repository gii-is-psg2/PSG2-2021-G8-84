package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.PetHotelRepository;
import org.springframework.stereotype.Service;

@Service
public class PetHotelService {

	private PetHotelRepository hotelRepository;

	@Autowired
	public PetHotelService(PetHotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	public Integer numberOfBookingsForPet(int petId) {
		List<Hotel> lista  = (List<Hotel>) hotelRepository.findAll();
		return lista.stream().filter(x -> x.getPet().getId().equals(petId)).collect(Collectors.toList()).size();
	}
	
	public void deleteByPetId(Integer petId) {
		List<Hotel> hotel = (List<Hotel>) hotelRepository.findAll();
		Hotel pet = hotel.stream().filter(x->x.getPet().getId().equals(petId)).findFirst().get();
		hotelRepository.delete(pet);
	}

	public boolean hasBooking(Integer petId) {
		List<Hotel> hotel = (List<Hotel>) hotelRepository.findAll();
		Pet pet = hotel.stream().map(x->x.getPet()).filter(x->x.getId().equals(petId)).findFirst().orElse(null);
		return pet!=null;
	}
	
	
	public boolean petExistBooking(Hotel hotel) {
		Boolean numberOfBookings = numberOfBookingsForPet(hotel.getPet().getId()) < 1;
		return !numberOfBookings;
	}
	
	public boolean BookingOnDate(Hotel hotel, Integer petId) {
		List<Hotel> lista  = (List<Hotel>) hotelRepository.findAll();
		List<LocalDate> startDates = lista.stream().filter(x->x.getPet().getId().equals(petId)).map(x->x.getStartDate()).collect(Collectors.toList());
		List<LocalDate> endDates = lista.stream().filter(x->x.getPet().getId().equals(petId)).map(x->x.getEndDate()).collect(Collectors.toList());
		boolean existBooking = false;
		for(int i=0;i<startDates.size();i++) {
			if(hotel.getStartDate().isEqual(startDates.get(i))){
				existBooking = true;
			}
		}
		for(int i=0;i<endDates.size();i++) {
			if(hotel.getStartDate().isBefore(endDates.get(i))){
				existBooking = true;
			}
			if(hotel.getEndDate().isBefore(endDates.get(i))){
				existBooking = true;
			}
		}
		return existBooking;
	}

	public boolean validDates(Hotel hotel) {
		Boolean startDate = hotel.getStartDate().isBefore(LocalDate.now());
		Boolean endDate = hotel.getEndDate().isBefore(hotel.getStartDate());
		Boolean equal =  hotel.getEndDate().isEqual(hotel.getStartDate());
		return startDate || endDate || equal;
	}
	
	public boolean notNull(Hotel hotel) {
		boolean startDate = hotel.getStartDate() == null;
		boolean endDate = hotel.getEndDate() == null;
		
		return endDate || startDate;
	}

}
