package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "causes")
public class Cause extends BaseEntity {

	// Attributes

	@NotEmpty
	private String name;

	@NotEmpty
	private String description;


	private String target;

	@NotEmpty
	private String ngo;

	@Min(value = 0)
	private Double gathered;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cause")
	private Set<Donation> donations;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getNgo() {
		return ngo;
	}

	public void setNgo(String ngo) {
		this.ngo = ngo;
	}

	public Double getGathered() {
		return gathered;
	}

	public void setGathered(Double gathered) {
		this.gathered = gathered;
	}
	
	public Set<Donation> getDonations() {
		return donations;
	}

	public void setDonations(Set<Donation> donations) {
		this.donations = donations;
		
	}
	
	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Cause [name=" + name + ", description=" + description + ", target=" + target + ", ngo="
				+ ngo + ", gathered=" + gathered + "]";
	}
}
