package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionService {

	private AdoptionRepository adoptionRepository;
	
	@Autowired
	public AdoptionService(AdoptionRepository repository) {
		this.adoptionRepository = repository;
	}
	
	@Transactional(readOnly = true)
	public Adoption findAdoptionById(int id) throws DataAccessException{
		return this.adoptionRepository.findAdoptionById(id);
	}
	
	@Transactional(readOnly = true)
	public Collection<Adoption> findMyOpenAdoptions(Owner owner) throws DataAccessException{
		return this.adoptionRepository.findOpenAdoptionsByOwner(owner);
	}
	
	@Transactional(readOnly = true)
	public Collection<Adoption> findMyClosedAdoptions(Owner owner) throws DataAccessException{
		return this.adoptionRepository.findClosedAdoptionsByOwner(owner);
	}
	
	@Transactional(readOnly = true)
	public Collection<Adoption> findOtherOpenAdoptions(Owner owner) throws DataAccessException{
		return this.adoptionRepository.findOthersOpenAdoptionsByOwner(owner);
	}
	
	@Transactional(readOnly = true)
	public Collection<Adoption> findOtherClosedAdoptions(Owner owner) throws DataAccessException{
		return this.adoptionRepository.findOthersClosedAdoptionsByOwner(owner);
	}
	
	@Transactional
	public void saveAdoption(Adoption adoption) throws DataAccessException{
		this.adoptionRepository.save(adoption);
	}
	
	@Transactional
	public void deleteAdoptionById(int id) throws DataAccessException{
		this.adoptionRepository.deleteById(id);
	}
}
