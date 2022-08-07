package com.hiddu.gym.enterprise.services.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiddu.gym.enterprise.entities.GymBranch;
import com.hiddu.gym.enterprise.execptions.ResourceNotFoundException;
import com.hiddu.gym.enterprise.payloads.GymBranchDto;
import com.hiddu.gym.enterprise.repositories.GymBranchRepo;
import com.hiddu.gym.enterprise.services.GymBranchService;

@Service
public class GymBranchServiceImpl implements GymBranchService {

	@Autowired
	private GymBranchRepo gymBranchRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public GymBranchDto createGymBranch(GymBranchDto gymBranchDto) {
		GymBranch gymBranch = new GymBranch();

		gymBranch.setGymName(gymBranchDto.getGymName());
		gymBranch.setGymContact(gymBranchDto.getGymContact());
		gymBranch.setGymFullAddress(gymBranchDto.getGymFullAddress());
		gymBranch.setGymLocationLat(gymBranchDto.getGymLocationLat());
		gymBranch.setGymLocationLong(gymBranchDto.getGymLocationLong());

		String gymCode = "BLR";
		gymCode = gymCode.concat(gymBranchDto.getGymName());
		gymCode = gymCode.concat(getPinCode(gymBranchDto.getGymFullAddress()));
		gymBranch.setGymCode(gymCode);

		GymBranch savedGymBranch = this.gymBranchRepo.save(gymBranch);
		return this.gymBranchToDto(savedGymBranch);
	}

	private String getPinCode(String fullAddress) {
		Pattern zipPattern = Pattern.compile("(\\d{6})");
		//		Matcher zipMatcher = zipPattern.matcher("#16/12, 1st Main, 7th Cross, BET School Road, Bismillah Nagar, Bangalore-560029");
		Matcher zipMatcher = zipPattern.matcher(fullAddress);
		if (zipMatcher.find()) {
			String zip = zipMatcher.group(1);
			return zip;
		}

		return "";
	}

	@Override
	public GymBranchDto updateGymBranch(GymBranchDto gymBranchDto, Integer gymId) {
		GymBranch gymBranch = this.gymBranchRepo.findById(gymId)
				.orElseThrow(()-> new ResourceNotFoundException("GymBranch","gymId", gymId));

		gymBranch.setGymName(gymBranchDto.getGymName());
		gymBranch.setGymContact(gymBranchDto.getGymContact());
		gymBranch.setGymFullAddress(gymBranchDto.getGymFullAddress());
		gymBranch.setGymLocationLat(gymBranchDto.getGymLocationLat());
		gymBranch.setGymLocationLong(gymBranchDto.getGymLocationLong());

		GymBranch updatedGymBranch = this.gymBranchRepo.save(gymBranch);
		GymBranchDto updatedGymBranchDto = this.gymBranchToDto(updatedGymBranch);

		return updatedGymBranchDto;
	}

	@Override
	public GymBranchDto getGymBranchById(Integer gymId) {
		GymBranch gymBranch = this.gymBranchRepo.findById(gymId)
				.orElseThrow(()-> new ResourceNotFoundException("GymBranch","gymId", gymId));
		GymBranchDto gymBranchDto = this.gymBranchToDto(gymBranch);
		return gymBranchDto;
	}

	@Override
	public List<GymBranchDto> getAllGymBranches() {
		List<GymBranch> gymBranches = this.gymBranchRepo.findAll();
		List<GymBranchDto> gymBranchessDto = gymBranches.stream().map(gymBranch -> this.gymBranchToDto(gymBranch)).collect(Collectors.toList());
		return gymBranchessDto;
	}

	@Override
	public void deleteGymBranch(Integer gymId) {
		GymBranch gymBranch = this.gymBranchRepo.findById(gymId)
				.orElseThrow(()-> new ResourceNotFoundException("GymBranch","gymId", gymId));
		this.gymBranchRepo.delete(gymBranch);
	}

	private GymBranch dtoToGymBranchr(GymBranchDto gymBranchDto) {
		GymBranch gymBranch = this.modelMapper.map(gymBranchDto, GymBranch.class);
		return gymBranch;
	}

	private GymBranchDto gymBranchToDto(GymBranch gymBranch) {
		GymBranchDto gymBranchDto = this.modelMapper.map(gymBranch, GymBranchDto.class);
		return gymBranchDto;
	}

}
