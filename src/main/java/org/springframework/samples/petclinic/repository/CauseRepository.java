package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Cause;

public interface CauseRepository extends Repository<Cause, Integer>{
	
	Collection<Cause> findAll() throws DataAccessException;
	
	Cause findById(int id) throws DataAccessException;
	
	void save(Cause cause) throws DataAccessException;
	
	@Query("SELECT cause FROM Cause cause WHERE cause.target != cause.gathered ")
	Collection<Cause> findUnfinishedCauses() throws DataAccessException;

}
