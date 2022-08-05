package com.hiddu.gym.enterprise.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hiddu.gym.enterprise.enums.GymPlanFrequencyEnum;
import com.hiddu.gym.enterprise.enums.converters.GymPlanFrequencyEnumJpaConverter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="gymplans")
@NoArgsConstructor
@Getter
@Setter
public class GymPlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gymPlanId;
	
	@Column(name = "gym_plan_name")
	private String gymPlanName;
	
	@Convert(converter = GymPlanFrequencyEnumJpaConverter.class)
	@Column(name = "gym_plan_frequency", nullable = false)
	private GymPlanFrequencyEnum gymPlanFrequency;
	
	@Column(name = "gym_plan_base_fare")
	private Float gymPlanBaseFare;
	
	@Column(name = "gym_plan_created_date")
	private Date gymPlanCreatedDate;
	
	@Column(name = "gym_plan_end_date")
	private Date gymPlanEndDate;
	
	@OneToMany(mappedBy = "gymPlan")
	private List<Customer> customers = new ArrayList<>();

}
