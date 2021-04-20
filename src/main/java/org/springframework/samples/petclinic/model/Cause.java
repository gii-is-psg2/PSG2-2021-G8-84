package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "causes")
public class Cause extends BaseEntity {

	// Attributes

	@NotEmpty
	private String name;

	@NotEmpty
	private String description;

	@Min(value = 0)
	private double target;

	@NotEmpty
	private String ngo;

	@Min(value = 0)
	private double gathered;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cause")
	private Set<Donation> donations;

	// getters and setters

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

	public double getTarget() {
		return target;
	}

	public void setTarget(double target) {
		this.target = target;
	}

	public String getNgo() {
		return ngo;
	}

	public void setNgo(String ngo) {
		this.ngo = ngo;
	}

	public double getGathered() {
		return gathered;
	}

	public void setGathered(double gathered) {
		this.gathered = gathered;
	}

	@Override
	public String toString() {
		return "Cause [name=" + name + ", description=" + description + ", target=" + target + ", ngo="
				+ ngo + ", gathered=" + gathered + "]";
	}
}
