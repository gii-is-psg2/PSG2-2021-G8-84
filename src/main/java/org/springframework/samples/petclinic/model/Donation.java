package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="donations")
public class Donation extends BaseEntity{
	
	//Attributes
	
	@Min(value = 0)
	private double amount;
	
	@NotNull
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@ManyToOne
	@JoinColumn(name = "cause_id")
	private Cause cause;

	//Getters and Setters
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
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
