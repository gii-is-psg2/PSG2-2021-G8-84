package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;

public interface AdoptionRepository extends Repository<Adoption, Integer>{
	
	Collection<Adoption> findAll() throws DataAccessException;
	
	void save(Adoption adoption) throws DataAccessException;
	
	void deleteById(int id) throws DataAccessException;
	
	Adoption findAdoptionById(int id) throws DataAccessException;
	
	@Query("SELECT adoption FROM Adoption adoption WHERE adoption.owner = :owner")
	Collection<Adoption> findAdoptionsByOwner(@Param("owner") Owner owner) throws DataAccessException;
	
	@Query("SELECT adoption FROM Adoption adoption WHERE adoption.owner != :owner")
	Collection<Adoption> findOthersAdoptionsByOwner(@Param("owner") Owner owner) throws DataAccessException;
	
}
