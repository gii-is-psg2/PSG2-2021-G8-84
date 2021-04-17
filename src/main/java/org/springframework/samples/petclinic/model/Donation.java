package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name="donations")
public class Donation extends BaseEntity{
	
	//Attributes
	
	@Min(value = 0)
	private double amount;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@ManyToOne
	@JoinColumn(name = "donation_id")
	private Donation donation;
	
	//Getters and Setters

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Donation getDonation() {
		return donation;
	}

	public void setDonation(Donation donation) {
		this.donation = donation;
	}

	@Override
	public String toString() {
		return "Donation [amount=" + amount + ", owner=" + owner + ", donation=" + donation + "]";
	}

}
