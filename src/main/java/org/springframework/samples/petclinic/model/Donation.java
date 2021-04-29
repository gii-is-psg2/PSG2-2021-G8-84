package org.springframework.samples.petclinic.model;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "donations")
public class Donation extends BaseEntity {

	// Attributes


	
	private String amount;

	@NotNull
	private String date;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@ManyToOne
	@JoinColumn(name = "cause_id")
	private Cause cause;

	// Getters and Setters

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Cause getCause() {
		return cause;
	}

	public void setCause(Cause cause) {
		this.cause = cause;
	}

	@Override
	public String toString() {
		return "Donation [amount=" + amount + ", date=" + date + ", owner=" + owner + ", cause=" + cause + "]";
	}

}
