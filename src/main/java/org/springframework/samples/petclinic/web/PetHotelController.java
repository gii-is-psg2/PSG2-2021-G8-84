package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.PetHotelRepository;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetHotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel")
public class PetHotelController {

	private static final String MENSAJE = "message";
	
	@Autowired
	private OwnerService ownerService;
	@Autowired
	private PetHotelService hotelService;
	@Autowired
	private PetHotelRepository petHotelRepo;

	@Autowired
	public PetHotelController(OwnerService ownerService, PetHotelService hotelService) {
		this.ownerService = ownerService;
		this.hotelService = hotelService;
	}

	@GetMapping()
	public String bookingList(ModelMap model) {
		List<Hotel> hotel = (List<Hotel>) petHotelRepo.findAll();
		List<Hotel> bookingsInProgress = hotel.stream().filter(x->x.getEndDate().isAfter(LocalDate.now())).collect(Collectors.toList());
		model.addAttribute("hotels", bookingsInProgress);
		return "hotel/bookingsList";
	}

	@GetMapping("/new")
	public String newBooking(ModelMap model) {
		Integer ownerId = ownerService.getOwnerId();
		Owner owner = ownerService.findOwnerById(ownerId);
		if(owner.getPets().isEmpty()) {
			model.put(MENSAJE, "No puede crear una reserva por que no tiene ninguna mascota registrada");
			return bookingList(model);
		}
		List<Pet> pets = owner.getPets();
		Hotel hotel = new Hotel();
		model.addAttribute("owner", owner);
		model.addAttribute("pets", pets);
		model.addAttribute("hotel", hotel);
		return "hotel/newBooking";
	}

	@PostMapping("/new")
	public String saveBooking(Hotel hotel, ModelMap model) {
		
		if(hotelService.notNull(hotel)) {
			model.put(MENSAJE, "Por favor, rellene todos los campos");
			return newBooking(model);
		}
		else if(hotelService.bookingOnDate(hotel, hotel.getPet().getId())) {
			model.put(MENSAJE, "Ya hay una reserva para "+hotel.getPet().getName()+" reserva en estas fechas");
			return newBooking(model);
		}
		else if(hotelService.petExistBooking(hotel) ) {
			model.put(MENSAJE, "Esta mascota ya esta registrada en el hotel");
			return newBooking(model);
		}else if(hotelService.validDates(hotel)) {
			model.put(MENSAJE, "Las fechas deben ser posterior a hoy y la fecha de fin no puede ser anterior a la fecha de inicio");
			return newBooking(model);
		}
		else{
			Integer ownerId = ownerService.getOwnerId();
			Owner owner = ownerService.findOwnerById(ownerId);
			hotel.setOwner(owner);
			petHotelRepo.save(hotel);
			model.addAttribute(MENSAJE, "Reserva creada con éxito");
			model.addAttribute("owner", owner);

			return bookingList(model);	
		}

	}

}
