package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "adoptions")
public class Adoption extends BaseEntity{
	// Attributes
	@NotEmpty
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "pet_id", referencedColumnName = "id")
	private Pet pet;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "adoption")
	private Set<AdoptionRequest> adoptionRequests;
	
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

	public Set<AdoptionRequest> getAdoptionRequests() {
		return adoptionRequests;
	}

	public void setAdoptionRequests(Set<AdoptionRequest> adoptionRequests) {
		this.adoptionRequests = adoptionRequests;
	}

	@Override
	public String toString() {
		return "Adoption [description=" + description + ", owner=" + owner + ", pet=" + pet + "]";
	}

	
}
