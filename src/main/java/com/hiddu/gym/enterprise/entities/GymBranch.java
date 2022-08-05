package com.hiddu.gym.enterprise.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="gymbranches")
@NoArgsConstructor
@Getter
@Setter
public class GymBranch {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer gymBranchId;
	
	@Id
	@Column(name = "gym_code", nullable = false, length = 100)
	private String gymCode;
	
	@Column(name = "gym_name", nullable = false, length = 100)
	private String gymName;
	
	@Column(name = "gym_location_lat", nullable = false, length = 100)
	private Float gymLocationLat;
	
	@Column(name = "gym_location_long", nullable = false, length = 100)
	private Float gymLocationLong;
	
	@Column(name = "gym_full_address", nullable = false, length = 500)
	private String gymFullAddress;
	
	@Column(name = "gym_contact", nullable = false, length = 20)
	private String gymContact;
	
	@OneToMany(mappedBy = "gymBranch")
	private List<Customer> customers = new ArrayList<>();

}
