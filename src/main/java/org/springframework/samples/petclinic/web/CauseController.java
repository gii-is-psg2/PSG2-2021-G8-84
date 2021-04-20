package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CauseController {

//	private static final String VIEWS_CAUSE_CREATE_OR_UPDATE_FORM = "causes/createOrUpdateCauseForm";
	
	private final CauseService causeService;
	private final DonationService donationService;
	
	@Autowired
	public CauseController(CauseService causeService, DonationService donationService) {
		this.causeService = causeService;
		this.donationService = donationService;
	}
	
	@GetMapping(value = {"/causes"})
	public String showCauseList(Map<String, Object> model) {
		Collection<Cause> causes = causeService.findUnfinishedCauses();
		model.put("causes", causes);
		return "causes/causeList";
	}
	
	@GetMapping(value = {"/causes/{causeId}/details"})
	public String showDetailsCauseList(@PathVariable("causeId") int causeId, Map<String, Object> model) {
		Cause cause = causeService.findCauseById(causeId);
		List<Donation> donations =  donationService.findDonationsByCauseId(causeId).stream().collect(Collectors.toList());
		donations.sort(Comparator.comparing(Donation::getDate));
		model.put("donations", donations);
		model.put("cause", cause);
		return "causes/causeDetailsList";
	}
}
