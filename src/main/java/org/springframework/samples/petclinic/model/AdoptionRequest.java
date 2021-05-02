package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "adoption_requests")
public class AdoptionRequest extends BaseEntity{

	// Attributes
	@NotEmpty
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@ManyToOne
	@JoinColumn(name = "adoption_id")
	private Adoption adoption;
	
	private Boolean closed;
	
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

	public Adoption getAdoption() {
		return adoption;
	}

	public void setAdoption(Adoption adoption) {
		this.adoption = adoption;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}
	
	@Override
	public String toString() {
		return "AdoptionRequest [description=" + description + ", owner=" + owner + ", adoption=" + adoption + "]";
	}
}
