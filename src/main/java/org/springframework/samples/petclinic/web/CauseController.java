package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CauseController {

//	private static final String VIEWS_CAUSE_CREATE_OR_UPDATE_FORM = "causes/createOrUpdateCauseForm";
	
	private final CauseService causeService;
	
	@Autowired
	public CauseController(CauseService causeService) {
		this.causeService = causeService;
	}
	
	@GetMapping(value = {"/causes"})
	public String showCauseList(Map<String, Object> model) {
		Collection<Cause> causes = causeService.findUnfinishedCauses();
		model.put("causes", causes);
		return "causes/causeList";
	}
}
