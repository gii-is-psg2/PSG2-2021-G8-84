package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="causes")
public class Cause extends BaseEntity {

	//Attributes
	
	@NotEmpty
	private String name;

	@NotEmpty
	private String description;
	
	@Min(value = 0)
	private double budgetTarget;
	
	@NotEmpty
	private String ngo;
	
	@Min(value = 0)
	private double budgetGathered;
	
	//getters and setters 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getBudgetTarget() {
		return budgetTarget;
	}

	public void setBudgetTarget(double budgetTarget) {
		this.budgetTarget = budgetTarget;
	}

	public String getNgo() {
		return ngo;
	}

	public void setNgo(String ngo) {
		this.ngo = ngo;
	}

	public double getBudgetGathered() {
		return budgetGathered;
	}

	public void setBudgetGathered(double budgetGathered) {
		this.budgetGathered = budgetGathered;
	}

	@Override
	public String toString() {
		return "Cause [name=" + name + ", description=" + description + ", budgetTarget=" + budgetTarget + ", ngo="
				+ ngo + ", budgetGathered=" + budgetGathered + "]";
	}
}
