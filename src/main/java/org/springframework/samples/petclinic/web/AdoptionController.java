package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdoptionController {
	
	private static final String VIEWS_ADOPTION_CREATE_OR_UPDATE_FORM = "adoptions/newAdoption";
	
	@Autowired
	private AdoptionService adoptionService;
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private PetService petService;
	
	@Autowired
	public AdoptionController(AdoptionService adoptionService, OwnerService ownerService, PetService petService) {
		this.adoptionService = adoptionService;
		this.ownerService = ownerService;
		this.petService = petService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/adoptions/new")
	public String initCreationForm(Map<String, Object> model) {
		Integer ownerId = this.ownerService.getOwnerId();
		Owner owner = this.ownerService.findOwnerById(ownerId);
		List<Pet> pets = owner.getPets();
		model.put("owner", owner);
		model.put("pets", pets);
		Adoption adoption = new Adoption();
		model.put("adoption", adoption);
		return VIEWS_ADOPTION_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/adoptions/new")
	public String processCreationForm(@Valid Adoption adoption, BindingResult result, ModelMap model) throws DataAccessException, DuplicatedPetNameException {
		Integer ownerId = this.ownerService.getOwnerId();
		Owner owner = this.ownerService.findOwnerById(ownerId);
		List<Pet> pets = owner.getPets();
		
		Integer petId = adoption.getPet().getId();
		Pet pet = this.petService.findPetById(petId);
		if (result.hasErrors()) {
			model.addAttribute("pets",pets);
			return VIEWS_ADOPTION_CREATE_OR_UPDATE_FORM;
		} if(pet.getAdoption() != null){
			model.addAttribute("pets",pets);
			model.addAttribute("message", "Esta mascota ya ha sido puesta en adopción, elija otra");
			return VIEWS_ADOPTION_CREATE_OR_UPDATE_FORM;
		} else {
			adoption.setOwner(owner);
			Pet petSave = adoption.getPet();
			petSave.setAdoption(adoption);
			this.adoptionService.saveAdoption(adoption);
			model.addAttribute("message","Mascota puesta en adopción correctamente");
			
			return "redirect:/adoptions/list-mine";
		}
	}
	
	@GetMapping(value = "/adoptions/{adoptionId}/edit")
	public String initUpdateForm(@PathVariable("adoptionId") int adoptionId, Model model) {
		Integer ownerId = this.ownerService.getOwnerId();
		Owner owner = this.ownerService.findOwnerById(ownerId);
		List<Pet> pets = owner.getPets();
		model.addAttribute("owner", owner);
		model.addAttribute("pets", pets);
		Adoption adoption = this.adoptionService.findAdoptionById(adoptionId);
		model.addAttribute("adoption",adoption);
		return VIEWS_ADOPTION_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/adoptions/{adoptionId}/edit")
	public String processUpdateForm(@Valid Adoption adoption, BindingResult result,
			@PathVariable("adoptionId") int adoptionId, ModelMap model) {
		Integer ownerId = this.ownerService.getOwnerId();
		Owner owner = this.ownerService.findOwnerById(ownerId);
		List<Pet> pets = owner.getPets();
		
		Integer petId = adoption.getPet().getId();
		Pet pet = this.petService.findPetById(petId);
		if(result.hasErrors()) {
			model.addAttribute("pets", pets);
			return VIEWS_ADOPTION_CREATE_OR_UPDATE_FORM;
		}else if(pet.getAdoption() != null && !pet.equals(adoption.getPet())) {
			model.addAttribute("pets",pets);
			model.addAttribute("message", "Esta mascota ya ha sido puesta en adopción, elija otra");
			return VIEWS_ADOPTION_CREATE_OR_UPDATE_FORM;
		}else{
			adoption.setId(adoptionId);
			adoption.setOwner(owner);
			Pet petSave = adoption.getPet();
			petSave.setAdoption(adoption);
			this.adoptionService.saveAdoption(adoption);
			model.addAttribute("message","Mascota puesta en adopción correctamente");
			
			return "redirect:/adoptions/list-mine";
		}
	}
	
	@GetMapping(value = "/adoptions/{adoptionId}/delete")
	public String deleteAdoption(@PathVariable("adoptionId") int adoptionId) {
		Adoption adoption = this.adoptionService.findAdoptionById(adoptionId);
		adoption.getPet().setAdoption(null);
		adoption.setPet(null);
		this.adoptionService.deleteAdoptionById(adoptionId);
		return "redirect:/adoptions/list-mine";
	}
	
	@GetMapping(value = "/adoptions/list")
	public String adoptionsList(ModelMap model) {
		Integer ownerId = this.ownerService.getOwnerId();
		Owner owner = this.ownerService.findOwnerById(ownerId);
		Collection<Adoption> adoptions = this.adoptionService.findOtherAdoptions(owner);
		model.addAttribute("adoptions",adoptions);
		model.addAttribute("mine", false);
		return "adoptions/adoptionList";
	}
	
	@GetMapping(value = "/adoptions/list-mine")
	public String adoptionsListMine(ModelMap model) {
		Integer ownerId = this.ownerService.getOwnerId();
		Owner owner = this.ownerService.findOwnerById(ownerId);
		Collection<Adoption> adoptions = this.adoptionService.findMyAdoptions(owner);
		model.addAttribute("adoptions",adoptions);
		model.addAttribute("mine", true);
		return "adoptions/adoptionList";
	}

}
