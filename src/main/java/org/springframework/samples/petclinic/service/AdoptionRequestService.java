package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.AdoptionRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionRequestService {
	
	private AdoptionRequestRepository arRepository;
	
	@Autowired
	public AdoptionRequestService(AdoptionRequestRepository arRepository) {
		this.arRepository = arRepository;
	}
	
	@Transactional(readOnly = true)
	public AdoptionRequest findAdoptionRequestById(int id) throws DataAccessException{
		return this.arRepository.findAdoptionRequestById(id);
	}
	
	@Transactional(readOnly = true)
	public Collection<AdoptionRequest> findAdoptionRequestsByAdoption(Adoption adoption) throws DataAccessException{
		return this.arRepository.findAdoptionRequestsByAdoption(adoption);
	}
	
	@Transactional(readOnly = true)
	public Collection<AdoptionRequest> findAdoptionRequestsByOwner(Owner owner) throws DataAccessException{
		return this.arRepository.findAdoptionRequestsByOwner(owner);
	}
	
	@Transactional
	public void saveAdoptionRequest(AdoptionRequest ar) throws DataAccessException{
		this.arRepository.save(ar);
	}
	
	@Transactional
	public void deleteAdoptionRequestById(int id) throws DataAccessException{
		this.arRepository.deleteById(id);
	}

}
