/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jdk.internal.jline.internal.Log;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */

@Controller
public class VetController {

	private static final String VIEWS_VET_CREATE_OR_UPDATE_FORM = "vets/createOrUpdateVetForm";
	private static final String SPECIALTIES_FORM = "vets/manageSpecialtiesForm";
	
	private final VetService vetService;
	private final SpecialtyService specialtyService;

	@Autowired
	public VetController(VetService clinicService, SpecialtyService specialtyService) {
		this.vetService = clinicService;
		this.specialtyService = specialtyService;
	}

	@GetMapping(value = { "/vets" })
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(value = { "/vets.xml"})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}
	
	
	//Crear un veterinario
	@GetMapping(value = { "vets/new" })
	public String initCreationVetForm(ModelMap model) {
		Vet vet = new Vet();
		model.put("vet", vet);
		Collection<Specialty> specs = this.specialtyService.findSpecialties();
		model.put("specs",specs);
		return VIEWS_VET_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = { "vets/new" })
	public String processCreationVetForm(@Valid Vet vet, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.put("vet", vet);
			Collection<Specialty> specs = this.specialtyService.findSpecialties();
			model.put("specs",specs);
			return VIEWS_VET_CREATE_OR_UPDATE_FORM;
		}else {
			this.vetService.saveVet(vet);
//			return "redirect:/vets/" + vet.getId() + "/newSpecialties";
			return "redirect:/vets";
		}
	}
	
//	@GetMapping(value = { "vets/{vetId}/newSpecialties" })
//	public String initCreationVetSpecialties(@PathVariable("vetId") int vetId, ModelMap model) {
//		Vet vet = this.vetService.findVetById(vetId);
//		model.put("vet",vet);
//		Collection<Specialty> specs = this.specialtyService.findSpecialties();
//		model.put("specs",specs);
//		return SPECIALTIES_FORM;
//	}
//	
//	@PostMapping(value = { "vets/{vetId}/newSpecialties" })
//	public String processCreationVetSpecialties(@Valid Set<Specialty> specialties, BindingResult result,
//			@PathVariable("vetId") int vetId, ModelMap model) {
//		if(result.hasErrors()) {
//			model.put("vet", this.vetService.findVetById(vetId));
//			Collection<Specialty> specs = this.specialtyService.findSpecialties();
//			model.put("specs",specs);
//			return SPECIALTIES_FORM;
//		}else {
//			Vet vetToUpdate=this.vetService.findVetById(vetId);
//			for (Specialty spec : specialties) {
//				vetToUpdate.addSpecialty(spec);
//			}
//			this.vetService.saveVet(vetToUpdate);
//			return "redirect:/vets";
//		}
//	}

	//Editar un veterinario
	@GetMapping(value = { "vets/{vetId}/edit" })
	public String initUpdateVetForm(@PathVariable("vetId") int vetId, ModelMap model) {
		Vet vet = this.vetService.findVetById(vetId);
		model.put("vet",vet);
		Collection<Specialty> specs = this.specialtyService.findSpecialties();
		model.put("specs",specs);
		return VIEWS_VET_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = { "vets/{vetId}/edit" })
	public String processUpdateVetForm(@Valid Vet vet, BindingResult result,
			@PathVariable("vetId") int vetId, ModelMap model) {
		if(result.hasErrors()) {
			model.put("vet", vet);
			Collection<Specialty> specs = this.specialtyService.findSpecialties();
			model.put("specs",specs);
			return VIEWS_VET_CREATE_OR_UPDATE_FORM;
		}else {
//			Vet vetToUpdate=this.vetService.findVetById(vetId);
//			BeanUtils.copyProperties(vet, vetToUpdate, "id");
			Log.info("Tamaño de la lista");
			this.vetService.saveVet(vet);
//			return "redirect:/vets/{vetId}/editSpecialties";
			return "redirect:/vets";
		}
	}
	
//	@GetMapping(value = { "vets/{vetId}/editSpecialties" })
//	public String initUpdateVetSpecialties(@PathVariable("vetId") int vetId, ModelMap model) {
//		Vet vet = this.vetService.findVetById(vetId);
//		model.put("vet",vet);
//		Collection<Specialty> specs = this.specialtyService.findSpecialties();
//		model.put("specs",specs);
//		return SPECIALTIES_FORM;
//	}
//	
//	@PostMapping(value = { "vets/{vetId}/editSpecialties" })
//	public String processUpdateVetSpecialties(Set<Specialty> specialties, BindingResult result,
//			@PathVariable("vetId") int vetId, ModelMap model) {
//		if(result.hasErrors()) {
//			model.put("vet", this.vetService.findVetById(vetId));
//			Collection<Specialty> specs = this.specialtyService.findSpecialties();
//			model.put("specs",specs);
//			return SPECIALTIES_FORM;
//		}else {
//			Vet vetToUpdate=this.vetService.findVetById(vetId);
//			for (Specialty spec : specialties) {
//				vetToUpdate.addSpecialty(spec);
//			}
//			this.vetService.saveVet(vetToUpdate);
//			return "redirect:/vets";
//		}
//	}
}
