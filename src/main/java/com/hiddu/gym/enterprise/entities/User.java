package com.hiddu.gym.enterprise.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hiddu.gym.enterprise.enums.UserEnum;
import com.hiddu.gym.enterprise.enums.UserIdTypeEnum;
import com.hiddu.gym.enterprise.enums.converters.UserEnumJpaConverter;
import com.hiddu.gym.enterprise.enums.converters.UserIdTypeEnumJpaConverter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@NoArgsConstructor
@Getter
@Setter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "full_name", nullable = false, length = 100)
	private String userName;
	
	@Column(name = "contact_number", nullable = false, length = 20)
	private String phoneNumber;
	
	@Column(name = "email_id", nullable = true, length = 100)
	private String emailId;
	
	@Column(name = "password", nullable = false, length = 50)
	private String password;
	
	@Convert(converter = UserIdTypeEnumJpaConverter.class)
	@Column(name = "user_id_type", nullable = true)
	private UserIdTypeEnum userIdType;
	
	@Column(name = "user_id_proof", nullable = true)
	private String userIdProof;
	
	@Convert(converter = UserEnumJpaConverter.class)
	@Column(name = "user_type", nullable = false)
	private UserEnum userType;
	
	@Column(name = "created_date", nullable = false)
	private Date userCreatedDate;
	
	@ManyToOne
	@JoinColumn(name = "gym_branch_id")
	private GymBranch gymBranch;
}
