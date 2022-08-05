package com.hiddu.gym.enterprise.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hiddu.gym.enterprise.enums.CustomerLifeCycleEnum;
import com.hiddu.gym.enterprise.enums.converters.CustomerLifeCycleEnumJpaConverter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="customers")
@NoArgsConstructor
@Getter
@Setter
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "customer_name", nullable = false, length = 100)
	private String customerName;
	
	@Column(name = "customer_contact_number", nullable = false, length = 20)
	private String customerPhoneNumber;
	
	@Column(name = "customer_email_id", nullable = true, length = 100)
	private String customerEmailId;
	
	@Column(name = "customer_id_type", nullable = true)
	private String customerIdType;
	
	@Column(name = "customer_id_proof", nullable = true)
	private String customerIdProof;
	
	@Column(name = "customer_enquired_date", nullable = false)
	private Date customerEnquiredDate;
	
	@Column(name = "customer_registered_date", nullable = true)
	private Date customerRegisteredDate;
	
	@Column(name = "customer_plan_activated_date", nullable = true)
	private Date customerPlanActivatedDate;
	
	@Convert(converter = CustomerLifeCycleEnumJpaConverter.class)
	@Column(name = "customer_status", nullable = false)
	private CustomerLifeCycleEnum customerStatus;
	
	private GymBranch gymBranch;
	
	private GymPlan gymPlan;
}
