package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {

	private CauseRepository causeRepository;

	@Autowired
	public CauseService(CauseRepository causeRepository) {
		this.causeRepository = causeRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Cause> findCauses() throws DataAccessException {
		return causeRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Collection<Cause> findUnfinishedCauses() throws DataAccessException {
		return causeRepository.findUnfinishedCauses();
	}
	
	@Transactional(readOnly = true)
	public Collection<Cause> findFinishedCauses() throws DataAccessException {
		return causeRepository.findFinishedCauses();
	}

	@Transactional
	public void saveCause(Cause cause) throws DataAccessException {
		causeRepository.save(cause);
	}

	@Transactional(readOnly = true)
	public Cause findCauseById(int id) throws DataAccessException {
		return causeRepository.findById(id);
	}

	public boolean validEmptyCause(Cause cause) {

		Boolean nameEmpty = cause.getName().isEmpty();
		Boolean descriptionEmpty = cause.getDescription().isEmpty();
		Boolean targetEmpty = cause.getTarget().isEmpty();
		Boolean ngoEmpty = cause.getNgo().isEmpty();

		return nameEmpty || descriptionEmpty || targetEmpty || ngoEmpty;
	}

	public boolean validMinTarget(Cause cause) {

		return Double.valueOf(cause.getTarget()) < 200;
	}

	public boolean validTargetIsNumber(Cause cause) {

		return !cause.getTarget().matches("[0-9]*");
	}

}
