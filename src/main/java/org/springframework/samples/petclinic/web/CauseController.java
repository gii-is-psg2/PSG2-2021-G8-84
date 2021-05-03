package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
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

	private static final String CAUSA = "cause";
	private static final String PROPIETARIO = "owner";
	private static final String MENSAJE = "message";

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
		Collection<Cause> completedCauses = causeService.findFinishedCauses();
		model.put("causes", causes);
		model.put("completedCauses", completedCauses);
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
		model.put(CAUSA, cause);
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
		model.put(PROPIETARIO, owner);
		model.put(CAUSA, cause);
		model.put("date", date.toString());
		model.put("donation", donation);

		return "causes/newDonation";
	}

	@PostMapping(value = { "/causes/{causeId}/donation" })
	public String saveDonation(@PathVariable("causeId") int causeId, Donation donation, ModelMap model) {

		Cause cause = causeService.findCauseById(causeId);
		if (donationService.validIsNumber(donation)) {
			model.put(MENSAJE, "Por favor, indique la cantidad con números enteros positivos");
			return newDonation(causeId, model);
		} else if(donationService.validEmptyDonation(donation)) {
			model.put(MENSAJE, "La donación no puede ser vacía");
			return newDonation(causeId, model);
		}else if(donationService.validMaxDonation(donation, cause)) {
			model.put(MENSAJE, "La donación no puede ser mayor a la indicada");
			return newDonation(causeId, model);
		} else {
			Integer ownerId = ownerService.getOwnerId();
			Owner owner = ownerService.findOwnerById(ownerId);
			donation.setOwner(owner);
			donation.setCause(cause);
			donationService.saveDonation(donation);
			Integer amount = Integer.valueOf(donation.getAmount());
			cause.setGathered(cause.getGathered() + amount);
			causeService.saveCause(cause);
			model.addAttribute(MENSAJE, "Donación creada con éxito");
			model.addAttribute(PROPIETARIO, owner);
			model.addAttribute(CAUSA, cause);
			return showCauseList(model);
		}

	}

	@GetMapping(value = { "/myCauses/create" })
	public String newCause(Map<String, Object> model) {

		Integer ownerId = ownerService.getOwnerId();
		Owner owner = ownerService.findOwnerById(ownerId);
		Cause cause = new Cause();
		model.put(CAUSA, cause);
		model.put(PROPIETARIO, owner);

		return "causes/owners/editOrCreateCause";
	}

	@PostMapping(value = { "/myCauses/create" })
	public String saveCause(Cause cause, Map<String, Object> model) {
		if (causeService.validTargetIsNumber(cause)) {
			model.put(MENSAJE, "El objetivo debe ser un número");
			return newCause(model);
		} else if (causeService.validEmptyCause(cause)) {
			model.put(MENSAJE, "Ningun campo debe estar vacío");
			return newCause(model);
		} else if (causeService.validMinTarget(cause)) {
			model.put(MENSAJE, "El objetivo no puede ser menor que 200 euros");
			return newCause(model);

		} else {
			Integer ownerId = ownerService.getOwnerId();
			Owner owner = ownerService.findOwnerById(ownerId);
			cause.setOwner(owner);
			cause.setDonations(new HashSet<>());
			causeService.saveCause(cause);
			model.put(CAUSA, cause);
			model.put(PROPIETARIO, owner);
			model.put(MENSAJE, "Causa creada con éxito");

			return showCauseList(model);
		}

	}
	
	@GetMapping(value = { "/myCauses/{causeId}/edit" })
	public String editCause(@PathVariable("causeId") int causeId, Map<String, Object> model) {

		Integer ownerId = ownerService.getOwnerId();
		Owner owner = ownerService.findOwnerById(ownerId);
		Cause cause = causeService.findCauseById(causeId);
		model.put(CAUSA, cause);
		model.put(PROPIETARIO, owner);

		return "causes/owners/editOrCreateCause";
	}

	
	@PostMapping(value = { "/myCauses/{causeId}/edit" })
	public String saveEditCause(@PathVariable("causeId") int causeId, Cause cause, Map<String, Object> model) {
		
		Cause causeToUpdate = causeService.findCauseById(causeId);
		
		if (causeService.validTargetIsNumber(cause)) {
			model.put(MENSAJE, "El objetivo debe ser un número");
			return newCause(model);
		} else if (causeService.validEmptyCause(cause)) {
			model.put(MENSAJE, "Ningun campo debe estar vacío");
			return newCause(model);
		} else if (causeService.validMinTarget(cause)) {
			model.put(MENSAJE, "El objetivo no puede ser menor que 200 euros");
			return newCause(model);

		} else {
			causeToUpdate.setDescription(cause.getDescription());
			causeToUpdate.setName(cause.getName());
			causeToUpdate.setNgo(cause.getNgo());
			causeToUpdate.setTarget(cause.getTarget());
			causeService.saveCause(causeToUpdate);
			model.put(CAUSA, cause);
			model.put(MENSAJE, "Causa editada con éxito");

			return showCauseList(model);
		}

	}
	
	
	@GetMapping(value = { "/myCauses/{causeId}/delete" })
	public String deleteCause(@PathVariable("causeId") int causeId, Map<String, Object> model) {
		
		causeRepository.delete(causeService.findCauseById(causeId));
		model.put(MENSAJE, "Causa eliminada correctamente");

		return showCauseList(model);
	}


}
