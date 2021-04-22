package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdoptionController {
	
	private static final String VIEWS_ADOPTION_CREATE_OR_UPDATE_FORM = "adoptions/newAdoption";
	
	@Autowired
	private AdoptionService adoptionService;
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	public AdoptionController(AdoptionService adoptionService, OwnerService ownerService) {
		this.adoptionService = adoptionService;
		this.ownerService = ownerService;
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
	public String processCreationForm(@Valid Adoption adoption, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return VIEWS_ADOPTION_CREATE_OR_UPDATE_FORM;
		} else {
			Integer ownerId = this.ownerService.getOwnerId();
			Owner owner = this.ownerService.findOwnerById(ownerId);
			adoption.setOwner(owner);
			this.adoptionService.saveAdoption(adoption);
			model.addAttribute("message","Mascota puesta en adopción correctamente");
			
			return "redirect:/adoptions/list-mine";
		}
	}
	
	@GetMapping(value = "/adoptions/list")
	public String adoptionsList(ModelMap model) {
		Integer ownerId = this.ownerService.getOwnerId();
		Owner owner = this.ownerService.findOwnerById(ownerId);
		Collection<Adoption> adoptions = this.adoptionService.findOtherAdoptions(owner);
		model.addAttribute("adoptions",adoptions);
		return "adoptions/adoptionList";
	}
	
	@GetMapping(value = "/adoptions/list-mine")
	public String adoptionsListMine(ModelMap model) {
		Integer ownerId = this.ownerService.getOwnerId();
		Owner owner = this.ownerService.findOwnerById(ownerId);
		Collection<Adoption> adoptions = this.adoptionService.findMyAdoptions(owner);
		model.addAttribute("adoptions",adoptions);
		return "adoptions/adoptionList";
	}

}
