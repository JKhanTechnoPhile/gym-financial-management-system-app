package com.hiddu.gym.enterprise.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiddu.gym.enterprise.entities.GymBranch;
import com.hiddu.gym.enterprise.entities.GymSubscriptionPlan;
import com.hiddu.gym.enterprise.enums.GymPlanFrequencyEnum;
import com.hiddu.gym.enterprise.enums.converters.GymPlanFrequencyEnumJpaConverter;
import com.hiddu.gym.enterprise.execptions.ResourceNotFoundException;
import com.hiddu.gym.enterprise.payloads.GymSubscriptionPlanDto;
import com.hiddu.gym.enterprise.repositories.GymBranchRepo;
import com.hiddu.gym.enterprise.repositories.GymSubscriptionPlanRepo;
import com.hiddu.gym.enterprise.services.GymSubscriptionPlanService;

@Service
public class GymSubscriptionPlanServiceImpl implements GymSubscriptionPlanService {
	
	@Autowired
	private GymBranchRepo gymBranchRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private GymSubscriptionPlanRepo gymSubscriptionPlanRepo;

	@Override
	public GymSubscriptionPlanDto createGymSubscriptionPlan(GymSubscriptionPlanDto gymSubscriptionPlanDto) {
		GymBranch gymBranch = gymBranchRepo.findByGymCode(gymSubscriptionPlanDto.getGymBranchCode());
		if(gymBranch == null)
			throw new ResourceNotFoundException("GymBranch", "gymCode", gymSubscriptionPlanDto.getGymBranchCode());
		
		//TODO: Check If Subscription already added 
		GymPlanFrequencyEnum selectedFrequency = new GymPlanFrequencyEnumJpaConverter().convertToEntityAttribute(gymSubscriptionPlanDto.getGymPlanFrequency());
		
		GymSubscriptionPlan gymSubscriptionPlanAlreadyExists =  this.gymSubscriptionPlanRepo.findSubscriptionPlanBySearch(selectedFrequency, gymSubscriptionPlanDto.getGymPlanBaseFare(), gymBranch.getId());
		if(gymSubscriptionPlanAlreadyExists != null) {
			throw new ResourceNotFoundException("GymSubscriptionPlan", "Subscription already added");
		}
		
		GymSubscriptionPlan gymSubscriptionPlan = this.dtoToGymSubscriptionPlan(gymSubscriptionPlanDto);
		gymSubscriptionPlan.setGymPlanCreatedDate(new Date());
		gymSubscriptionPlan.setGymBranch(gymBranch);
		GymSubscriptionPlan savedGymSubscriptionPlan = this.gymSubscriptionPlanRepo.save(gymSubscriptionPlan);
		return this.gymSubscriptionPlanToDto(savedGymSubscriptionPlan);
	}

	@Override
	public GymSubscriptionPlanDto updateGymSubscriptionPlan(GymSubscriptionPlanDto gymSubscriptionPlanDto, String gymCode, Integer gymSubscriptionPlanId) {
		
		GymBranch gymBranch = gymBranchRepo.findByGymCode(gymCode);
		if(gymBranch == null)
			throw new ResourceNotFoundException("Gym Branch","gymCode", gymCode);
		
		GymSubscriptionPlan gymSubscriptionPlan = this.gymSubscriptionPlanRepo.findById(gymSubscriptionPlanId)
				.orElseThrow(()-> new ResourceNotFoundException("GymSubscriptionPlanId","id", gymSubscriptionPlanId));
		
		gymSubscriptionPlan.setGymPlanName(gymSubscriptionPlanDto.getGymPlanName());
		gymSubscriptionPlan.setGymPlanEditedDate(new Date());
		
		GymSubscriptionPlan updatedGymSubscriptionPlan = this.gymSubscriptionPlanRepo.save(gymSubscriptionPlan);
		GymSubscriptionPlanDto updatedGymSubscriptionPlanDto = this.gymSubscriptionPlanToDto(updatedGymSubscriptionPlan);
		return updatedGymSubscriptionPlanDto;
	}

	@Override
	public GymSubscriptionPlanDto getGymSubscriptionPlanById(String gymCode, Integer gymSubscriptionPlanId) {
		
		GymBranch gymBranch = gymBranchRepo.findByGymCode(gymCode);
		if(gymBranch == null)
			throw new ResourceNotFoundException("Gym Branch","gymCode", gymCode);
		
//		GymSubscriptionPlan gymSubscriptionPlan = this.gymSubscriptionPlanRepo.findById(gymSubscriptionPlanId)
//				.orElseThrow(()-> new ResourceNotFoundException("GymSubscriptionPlanId","id", gymSubscriptionPlanId));
		GymSubscriptionPlan gymSubscriptionPlan = this.gymSubscriptionPlanRepo.findSubscriptionPlanByGymIdAndSubscriptionId(gymSubscriptionPlanId, gymBranch.getId());
		
		if(gymSubscriptionPlan == null)
			throw new ResourceNotFoundException("Gym Subscription Plan","gymSubscriptionPlanId", gymSubscriptionPlanId);
		
		GymSubscriptionPlanDto gymSubscriptionPlanDto = this.gymSubscriptionPlanToDto(gymSubscriptionPlan);
		
		return gymSubscriptionPlanDto;
	}

	@Override
	public List<GymSubscriptionPlanDto> getAllGymSubscriptionPlans() {
		List<GymSubscriptionPlan> gymSubscriptionPlans = this.gymSubscriptionPlanRepo.findAll();
		List<GymSubscriptionPlanDto> gymSubscriptionPlanDtos = gymSubscriptionPlans.stream().map(gymSubscriptionPlan -> this.gymSubscriptionPlanToDto(gymSubscriptionPlan)).collect(Collectors.toList());
		return gymSubscriptionPlanDtos;
	}
	
	@Override
	public List<GymSubscriptionPlanDto> getGymSubscriptionPlansByBranch(String gymCode) {
		
		GymBranch gymBranch = gymBranchRepo.findByGymCode(gymCode);
		if(gymBranch == null)
			throw new ResourceNotFoundException("Gym Branch","gymCode", gymCode);
		
		List<GymSubscriptionPlan> gymSubscriptionPlans = this.gymSubscriptionPlanRepo.findByGymBranch(gymBranch);
		List<GymSubscriptionPlanDto> gymSubscriptionPlanDtos = gymSubscriptionPlans.stream().map(gymSubscriptionPlan -> this.gymSubscriptionPlanToDto(gymSubscriptionPlan)).collect(Collectors.toList());
		return gymSubscriptionPlanDtos;
	}

	@Override
	public void deleteGymSubscriptionPlan(String gymCode, Integer gymSubscriptionPlanId) {
		
		GymBranch gymBranch = gymBranchRepo.findByGymCode(gymCode);
		if(gymBranch == null)
			throw new ResourceNotFoundException("Gym Branch","gymCode", gymCode);
		
		GymSubscriptionPlan gymSubscriptionPlan = this.gymSubscriptionPlanRepo.findSubscriptionPlanByGymIdAndSubscriptionId(gymSubscriptionPlanId, gymBranch.getId());
		
		if(gymSubscriptionPlan == null)
			throw new ResourceNotFoundException("Gym Subscription Plan","gymSubscriptionPlanId", gymSubscriptionPlanId);
		
		this.gymSubscriptionPlanRepo.delete(gymSubscriptionPlan);

	}
	
	private GymSubscriptionPlan dtoToGymSubscriptionPlan(GymSubscriptionPlanDto gymSubscriptionPlanDto) {
		GymSubscriptionPlan gymSubscriptionPlan = this.modelMapper.map(gymSubscriptionPlanDto, GymSubscriptionPlan.class);
		return gymSubscriptionPlan;
	}
	
	private GymSubscriptionPlanDto gymSubscriptionPlanToDto(GymSubscriptionPlan gymSubscriptionPlan) {
		GymSubscriptionPlanDto gymSubscriptionPlanDto = this.modelMapper.map(gymSubscriptionPlan, GymSubscriptionPlanDto.class);
		return gymSubscriptionPlanDto;
	}

}
