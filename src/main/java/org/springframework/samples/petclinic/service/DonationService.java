package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonationService {
	
private DonationRepository donationRepository;
private CauseRepository causeRepository;
	
	@Autowired
	public DonationService(DonationRepository donationRepository, CauseRepository causeRepository) {
		this.donationRepository = donationRepository;
		this.causeRepository = causeRepository;
	}
	
	@Transactional(readOnly = true)
	public Collection<Donation> findDonations() throws DataAccessException {
		return donationRepository.findAll();
	}
	
	
	@Transactional
	public void saveDonation(Donation donation) throws DataAccessException {
		donationRepository.save(donation);
	}
	
	@Transactional(readOnly = true)
	public Donation findDonationById(int id) throws DataAccessException {
		return donationRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Set<Donation> findDonationsByCauseId(int id) throws DataAccessException {
		return causeRepository.findById(id).getDonations();
	}

	@Transactional(readOnly = true)
	public Double totalDonationByCause(int causeId){
		return findDonationsByCauseId(causeId).stream().mapToDouble(x->Double.valueOf(x.getAmount())).sum();
		
	}


	public boolean validEmptyDonation(Donation donation) {
		
		return donation.getAmount().isEmpty() || Double.valueOf(donation.getAmount())==0;
	}
	

	public boolean validMaxDonation(Donation donation, Cause cause) {
		
		return Double.valueOf(donation.getAmount()) > (Double.valueOf(cause.getTarget()) - cause.getGathered());
	}
	
	public boolean validIsNumber(Donation donation) {

		return !donation.getAmount().matches("^\\d*\\.?\\d{2}$");
	}

}



