package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CauseController {

//	private static final String VIEWS_CAUSE_CREATE_OR_UPDATE_FORM = "causes/createOrUpdateCauseForm";

	private final CauseService causeService;
	private final DonationService donationService;
	private final OwnerService ownerService;
	private final CauseRepository causeRepository;

	@Autowired
	public CauseController(CauseService causeService, DonationService donationService,
			OwnerService ownerService, CauseRepository causeRepository) {
		this.causeService = causeService;
		this.donationService = donationService;
		this.ownerService = ownerService;
		this.causeRepository = causeRepository;
	}

	@GetMapping(value = { "/causes" })
	public String showCauseList(Map<String, Object> model) {
		Collection<Cause> causes = causeService.findUnfinishedCauses();
		model.put("causes", causes);
		return "causes/causeList";
	}

	@GetMapping(value = { "/myCauses" })
	public String showCauseListByOwner(Map<String, Object> model) {
		Integer ownerId = ownerService.getOwnerId();
		List<Cause> causesByOwner = causeService.findCauses().stream().filter(x -> x.getOwner().getId().equals(ownerId))
				.collect(Collectors.toList());
		model.put("causesByOwner", causesByOwner);
		return "causes/owners/causesByOwner";
	}

	@GetMapping(value = { "/causes/{causeId}/details" })
	public String showDetailsCauseList(@PathVariable("causeId") int causeId, Map<String, Object> model) {
		Cause cause = causeService.findCauseById(causeId);
		List<Donation> donations = donationService.findDonationsByCauseId(causeId).stream()
				.collect(Collectors.toList());
		donations.sort(Comparator.comparing(Donation::getDate));
		model.put("donations", donations);
		model.put("cause", cause);
		return "causes/causeDetailsList";
	}

	@GetMapping(value = { "/causes/{causeId}/donation" })
	public String newDonation(@PathVariable("causeId") int causeId, Map<String, Object> model) {

		Integer ownerId = ownerService.getOwnerId();
		Owner owner = ownerService.findOwnerById(ownerId);
		Cause cause = causeService.findCauseById(causeId);
		Double max = Double.valueOf(cause.getTarget()) - cause.getGathered();
		Donation donation = new Donation();
		LocalDate date = LocalDate.now();
		model.put("max", max);
		model.put("owner", owner);
		model.put("cause", cause);
		model.put("date", date.toString());
		model.put("donation", donation);

		return "causes/newDonation";
	}

	@PostMapping(value = { "/causes/{causeId}/donation" })
	public String saveDonation(@PathVariable("causeId") int causeId, Donation donation, ModelMap model) {

		Cause cause = causeService.findCauseById(causeId);
		if (donationService.validIsNumber(donation)) {
			model.put("message", "Por favor, indique la cantidad con números enteros positivos");
			return newDonation(causeId, model);
		} else if(donationService.validEmptyDonation(donation)) {
			model.put("message", "La donación no puede ser vacía");
			return newDonation(causeId, model);
		}else if(donationService.validMaxDonation(donation, cause)) {
			model.put("message", "La donación no puede ser mayor a la indicada");
			return newDonation(causeId, model);
		} else {
			Integer ownerId = ownerService.getOwnerId();
			Owner owner = ownerService.findOwnerById(ownerId);
			donation.setOwner(owner);
			donation.setCause(cause);
			donationService.saveDonation(donation);
			Double amount = Double.valueOf(donation.getAmount());
			cause.setGathered(cause.getGathered() + amount);
			causeService.saveCause(cause);
			model.addAttribute("message", "Donación creada con éxito");
			model.addAttribute("owner", owner);
			model.addAttribute("cause", cause);
			return showCauseList(model);
		}

	}

	@GetMapping(value = { "/myCauses/create" })
	public String newCause(Map<String, Object> model) {

		Integer ownerId = ownerService.getOwnerId();
		Owner owner = ownerService.findOwnerById(ownerId);
		Cause cause = new Cause();
		model.put("cause", cause);
		model.put("owner", owner);

		return "causes/owners/editOrCreateCause";
	}

	@PostMapping(value = { "/myCauses/create" })
	public String saveCause(Cause cause, Map<String, Object> model) {
		if (causeService.validTargetIsNumber(cause)) {
			model.put("message", "El objetivo debe ser un número");
			return newCause(model);
		} else if (causeService.validEmptyCause(cause)) {
			model.put("message", "Ningun campo debe estar vacío");
			return newCause(model);
		} else if (causeService.validMinTarget(cause)) {
			model.put("message", "El objetivo no puede ser menor que 200 euros");
			return newCause(model);

		} else {
			Integer ownerId = ownerService.getOwnerId();
			Owner owner = ownerService.findOwnerById(ownerId);
			cause.setOwner(owner);
			cause.setDonations(new HashSet<Donation>());
			causeService.saveCause(cause);
			model.put("cause", cause);
			model.put("owner", owner);
			model.put("message", "Causa creada con éxito");

			return showCauseList(model);
		}

	}
	
	@GetMapping(value = { "/myCauses/{causeId}/edit" })
	public String editCause(@PathVariable("causeId") int causeId, Map<String, Object> model) {

		Integer ownerId = ownerService.getOwnerId();
		Owner owner = ownerService.findOwnerById(ownerId);
		Cause cause = causeService.findCauseById(causeId);
		model.put("cause", cause);
		model.put("owner", owner);

		return "causes/owners/editOrCreateCause";
	}

	
	@PostMapping(value = { "/myCauses/{causeId}/edit" })
	public String saveEditCause(@PathVariable("causeId") int causeId, Cause cause, Map<String, Object> model) {
		
		Cause causeToUpdate = causeService.findCauseById(causeId);
		
		if (causeService.validTargetIsNumber(cause)) {
			model.put("message", "El objetivo debe ser un número");
			return newCause(model);
		} else if (causeService.validEmptyCause(cause)) {
			model.put("message", "Ningun campo debe estar vacío");
			return newCause(model);
		} else if (causeService.validMinTarget(cause)) {
			model.put("message", "El objetivo no puede ser menor que 200 euros");
			return newCause(model);

		} else {
			causeToUpdate.setDescription(cause.getDescription());
			causeToUpdate.setName(cause.getName());
			causeToUpdate.setNgo(cause.getNgo());
			causeToUpdate.setTarget(cause.getTarget());
			causeService.saveCause(causeToUpdate);
			model.put("cause", cause);
			model.put("message", "Causa editada con éxito");

			return showCauseList(model);
		}

	}
	
	
	@GetMapping(value = { "/myCauses/{causeId}/delete" })
	public String deleteCause(@PathVariable("causeId") int causeId, Map<String, Object> model) {
		
		causeRepository.delete(causeService.findCauseById(causeId));
		model.put("message", "Causa eliminada correctamente");

		return showCauseList(model);
	}


}
