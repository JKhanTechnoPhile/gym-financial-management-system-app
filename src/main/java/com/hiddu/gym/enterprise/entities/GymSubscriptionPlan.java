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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hiddu.gym.enterprise.enums.GymPlanFrequencyEnum;
import com.hiddu.gym.enterprise.enums.converters.GymPlanFrequencyEnumJpaConverter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="gymSubscriptionPlans")
@NoArgsConstructor
@Getter
@Setter
public class GymSubscriptionPlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "gym_plan_name", nullable = false)
	private String gymPlanName;
	
	@Convert(converter = GymPlanFrequencyEnumJpaConverter.class)
	@Column(name = "gym_plan_frequency", nullable = false)
	private GymPlanFrequencyEnum gymPlanFrequency;
	
	@Column(name = "gym_plan_base_fare", nullable = false)
	private Float gymPlanBaseFare;
	
	@Column(name = "gym_plan_created_date", nullable = false)
	private Date gymPlanCreatedDate;
	
	@Column(name = "gym_plan_end_date", nullable = true)
	private Date gymPlanEndDate;
	
	@Column(name = "gym_plan_last_edited_date", nullable = true)
	private Date gymPlanEditedDate;
	
	@OneToMany(mappedBy = "gymPlan")
	private List<Customer> customers = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "gym_branch_id")
	private GymBranch gymBranch;

}
