package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "adoptions")
public class Adoption extends BaseEntity{
	// Attributes
	@NotEmpty
	private String description;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner", referencedColumnName = "owner_id")
	private Owner owner;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pet", referencedColumnName = "pet_id")
	private Pet pet;
	
	// Getters y setters
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	@Override
	public String toString() {
		return "Adoption [description=" + description + ", owner=" + owner + ", pet=" + pet + "]";
	}

	
}
