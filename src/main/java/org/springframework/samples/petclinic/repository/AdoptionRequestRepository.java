package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.AdoptionRequest;
import org.springframework.samples.petclinic.model.Owner;

public interface AdoptionRequestRepository extends Repository<AdoptionRequest, Integer>{
	
	void save(AdoptionRequest adoptionRequest) throws DataAccessException;
	
	void deleteById(int id) throws DataAccessException;
	
	AdoptionRequest findAdoptionRequestById(int id) throws DataAccessException;
	
	@Query("SELECT ar FROM AdoptionRequest ar WHERE ar.owner = :owner AND ar.closed = false")
	Collection<AdoptionRequest> findOpenAdoptionRequestsByOwner(@Param("owner") Owner owner) throws DataAccessException;
	
	@Query("SELECT ar FROM AdoptionRequest ar WHERE ar.owner = :owner AND ar.closed = true")
	Collection<AdoptionRequest> findClosedAdoptionRequestsByOwner(@Param("owner") Owner owner) throws DataAccessException;

	@Query("SELECT ar FROM AdoptionRequest ar WHERE ar.adoption = :adoption")
	Collection<AdoptionRequest> findAdoptionRequestsByAdoption(@Param("adoption") Adoption adoption) throws DataAccessException;
}
