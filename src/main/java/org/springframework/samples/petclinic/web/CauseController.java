package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
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

	@Autowired
	public CauseController(CauseService causeService, DonationService donationService, OwnerService ownerService) {
		this.causeService = causeService;
		this.donationService = donationService;
		this.ownerService = ownerService;
	}

	@GetMapping(value = { "/causes" })
	public String showCauseList(Map<String, Object> model) {
		Collection<Cause> causes = causeService.findUnfinishedCauses();
		model.put("causes", causes);
		return "causes/causeList";
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
		Double max = cause.getTarget() - cause.getGathered();
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
		if (donationService.validDonation(donation, cause)) {
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

		} else if(donationService.validIsZero(donation)){
			model.put("message", "La donación no puede ser de 0 euros");
			return newDonation(causeId, model);
		}else if(donationService.validNegativeDonation(donation)) {
			model.put("message", "La donación no puede ser negativa");
			return newDonation(causeId, model);
		}else if(donationService.validMaxDonation(donation, cause)){
			model.put("message", "La donación no puede ser superior al máximo");
			return newDonation(causeId, model);
		}else {
			model.put("message", "Porfavor, indique la cantidad con números enteros");
			return newDonation(causeId, model);
		}

	}

}
