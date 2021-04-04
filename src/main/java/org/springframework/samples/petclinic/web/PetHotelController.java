package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetHotelRepository;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetHotelService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel")
public class PetHotelController {
	
	@Autowired
	private PetService petService;
	@Autowired
    private OwnerService ownerService;
	@Autowired
    private PetHotelService hotelService;
	@Autowired 
	private PetHotelRepository petHotelRepo;
	@Autowired 
	private OwnerRepository ownerRepo;
 	
    @Autowired
	public PetHotelController(PetService petService, OwnerService ownerService, PetHotelService hotelService) {
		this.petService = petService;
        this.ownerService = ownerService;
        this.hotelService = hotelService;
	}

    @GetMapping()
	public String BookingList(ModelMap model) {		
    	List<Hotel> hotel = (List<Hotel>) petHotelRepo.findAll();
    	model.addAttribute("hotels", hotel);
		return "hotel/bookingsList";
	}
    
    @GetMapping("/new")
	public String newBooking(ModelMap model) {		
    	Integer ownerId = ownerService.getOwnerId();
    	Owner owner = ownerService.findOwnerById(ownerId);
    	List<Pet> pets = owner.getPets();
    	Hotel hotel = new Hotel();
    	model.addAttribute("owner", owner);
    	model.addAttribute("pets", pets);
    	model.addAttribute("hotel", hotel);
		return "hotel/newBooking";
	}

    @PostMapping("/new")
	public String saveBooking(Hotel hotel, ModelMap model) {	
    	if(hotelService.validBooking(hotel)){
    		Integer ownerId = ownerService.getOwnerId();
        	Owner owner = ownerService.findOwnerById(ownerId);
        	hotel.setOwner(owner);
        	petHotelRepo.save(hotel);
        	model.addAttribute("message", "Reserva creada con éxito");
        	model.addAttribute("owner", owner);
        		
    		return "welcome";	
    	}else {
    		model.addAttribute("message", "La mascota ya está en el hotel, o las fechas son incorrectas");
    		return newBooking(model);	
    	}    	
	}
   

}
