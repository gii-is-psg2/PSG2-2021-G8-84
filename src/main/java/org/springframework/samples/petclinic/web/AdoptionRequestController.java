package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionRequestService;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdoptionRequestController {

	private static final String ADOPTION_REQUEST_FORM = "adoptionRequests/newAdoptionRequest";
	private static final String ADOP = "adoption";
	private static final String DUENIO = "owner";
	private static final String MY_REQUESTS = "redirect:/adoptions/my-requests";
	
	@Autowired
	private AdoptionRequestService arService;
	
	@Autowired
	private AdoptionService aService;
	
	@Autowired
	private OwnerService oService;
	
	@Autowired
	public AdoptionRequestController(AdoptionRequestService arService, AdoptionService aService, OwnerService oService) {
		this.arService = arService;
		this.aService = aService;
		this.oService = oService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/adoptions/{adoptionId}/request/new")
	public String initCreationForm(@PathVariable("adoptionId") int adoptionId, ModelMap model) {
		Integer ownerId = this.oService.getOwnerId();
		Owner owner = this.oService.findOwnerById(ownerId);
		model.addAttribute(DUENIO, owner);
		Adoption adoption = this.aService.findAdoptionById(adoptionId);
		model.addAttribute(ADOP,adoption);
		AdoptionRequest ar = new AdoptionRequest();
		ar.setClosed(false);
		model.addAttribute("adoptionRequest", ar);
		return ADOPTION_REQUEST_FORM;
	}
	
	@PostMapping(value = "/adoptions/{adoptionId}/request/new")
	public String processCreationForm(@PathVariable("adoptionId") int adoptionId, 
			@Valid AdoptionRequest adoptionRequest, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			return ADOPTION_REQUEST_FORM;
		}else {
			Adoption a = this.aService.findAdoptionById(adoptionId);
			a.getAdoptionRequests().add(adoptionRequest);
			Integer ownerId = this.oService.getOwnerId();
			Owner owner = this.oService.findOwnerById(ownerId);
			adoptionRequest.setOwner(owner);
			Adoption adoption = this.aService.findAdoptionById(adoptionId);
			adoptionRequest.setAdoption(adoption);
			this.arService.saveAdoptionRequest(adoptionRequest);
			model.addAttribute("message", "Solicitud de adopción creada correctamente");
			
			return MY_REQUESTS;
		}
	}
	
	@GetMapping(value = "/adoptions/{adoptionId}/request/{arId}/edit")
	public String initUpdateForm(@PathVariable("adoptionId") int adoptionId,
			@PathVariable("arId") int arId, ModelMap model) {
		Integer ownerId = this.oService.getOwnerId();
		Owner owner = this.oService.findOwnerById(ownerId);
		model.addAttribute(DUENIO, owner);
		Adoption adoption = this.aService.findAdoptionById(adoptionId);
		model.addAttribute(ADOP,adoption);
		AdoptionRequest ar = this.arService.findAdoptionRequestById(arId);
		model.addAttribute("adoptionRequest", ar);
		return ADOPTION_REQUEST_FORM;
	}
	
	@PostMapping(value = "/adoptions/{adoptionId}/request/{arId}/edit")
	public String processUpdateForm(@PathVariable("adoptionId") int adoptionId,
			@PathVariable("arId") int arId, @Valid AdoptionRequest ar, 
			BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			Integer ownerId = this.oService.getOwnerId();
			Owner owner = this.oService.findOwnerById(ownerId);
			model.addAttribute(DUENIO, owner);
			Adoption adoption = this.aService.findAdoptionById(adoptionId);
			model.addAttribute(ADOP,adoption);
			return ADOPTION_REQUEST_FORM;
		}else {
			ar.setId(arId);
			Integer ownerId = this.oService.getOwnerId();
			Owner owner = this.oService.findOwnerById(ownerId);
			ar.setOwner(owner);
			Adoption adoption = this.aService.findAdoptionById(adoptionId);
			ar.setAdoption(adoption);
			this.arService.saveAdoptionRequest(ar);
			model.addAttribute("message", "Solicitud de adopción creada correctamente");
			
			return MY_REQUESTS;
		}
	}
	
	@GetMapping(value = "/adoptions/{adoptionId}/request/{arId}/delete")
	public String deleteAdoptionRequest(@PathVariable("adoptionId") int adoptionId, @PathVariable("arId") int arId) {
		AdoptionRequest ar = this.arService.findAdoptionRequestById(arId);
		this.aService.findAdoptionById(adoptionId).getAdoptionRequests().remove(ar);
		this.arService.deleteAdoptionRequestById(arId);
		return MY_REQUESTS;
	}
	
	@GetMapping(value = "/adoptions/my-requests")
	public String listMyRequests(ModelMap model) {
		Integer ownerId = this.oService.getOwnerId();
		Owner owner = this.oService.findOwnerById(ownerId);
		Collection<AdoptionRequest> res = this.arService.findOpenAdoptionRequestsByOwner(owner);
		Collection<AdoptionRequest> res2 = this.arService.findClosedAdoptionRequestsByOwner(owner);
		model.addAttribute("adoptionRequests", res);
		model.addAttribute("closedRequests", res2);
		return "adoptionRequests/requestsList";
	}
	
	@GetMapping(value = "/adoptions/{adoptionId}/request/{arId}/close")
	public String closeAdoption(@PathVariable("adoptionId") int adoptionId,
			@PathVariable("arId") int arId) {
		Integer ownerId = this.oService.getOwnerId();
		Owner oldOwner = this.oService.findOwnerById(ownerId);
		AdoptionRequest ar = this.arService.findAdoptionRequestById(arId);
		Owner newOwner = ar.getOwner();
		Pet pet = ar.getAdoption().getPet();
		Set<AdoptionRequest> arList = ar.getAdoption().getAdoptionRequests();
		if(arList != null) {
			for(AdoptionRequest a : arList) {
				a.setClosed(true);
				this.arService.saveAdoptionRequest(a);
			}
		}
		oldOwner.removePet(pet);
		newOwner.addPet(pet);
		pet.setAdoption(null);
		Adoption res = this.aService.findAdoptionById(adoptionId);
		res.setClosed(true);
		this.aService.saveAdoption(res);
		
		return "redirect:/adoptions/list-mine";
	}
	
	@GetMapping(value = "/adoptions/{adoptionId}")
	public ModelAndView showAdoptionDetails(@PathVariable("adoptionId") int adoptionId) {
		ModelAndView mav = new ModelAndView("adoptions/adoptionDetails");
		mav.addObject(ADOP, this.aService.findAdoptionById(adoptionId));
		return mav;
	}
}
