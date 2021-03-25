package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "hotel")
public class Hotel extends BaseEntity {
	
	 	@Column(name = "start_date")
		@DateTimeFormat(pattern = "yyyy/MM/dd")
	    private LocalDate startDate;
	   
	    @Column(name = "end_date")
		@DateTimeFormat(pattern = "yyyy/MM/dd")
	    private LocalDate endDate;
		
	    @ManyToOne
		@JoinColumn(name = "pet_id")
		private Pet pet;
	    
	    @ManyToOne
	    @JoinColumn(name = "owner_id")
	    private Owner owner;

		public LocalDate getStartDate() {
			return startDate;
		}

		public void setStartDate(LocalDate startDate) {
			this.startDate = startDate;
		}

		public LocalDate getEndDate() {
			return endDate;
		}

		public void setEndDate(LocalDate endDate) {
			this.endDate = endDate;
		}

		public Pet getPet() {
			return pet;
		}

		public void setPet(Pet pet) {
			this.pet = pet;
		}

		public Owner getOwner() {
			return owner;
		}

		public void setOwner(Owner owner) {
			this.owner = owner;
		}
	    
	    

}
