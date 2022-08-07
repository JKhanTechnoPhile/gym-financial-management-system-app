package com.hiddu.gym.enterprise.services;

import java.util.List;

import com.hiddu.gym.enterprise.payloads.GymBranchDto;

public interface GymBranchService {

	GymBranchDto createGymBranch(GymBranchDto gymBranch);
	
	GymBranchDto updateGymBranch(GymBranchDto gymBranch, Integer gymId);
	
	GymBranchDto getGymBranchById(Integer gymId);
	
	List<GymBranchDto> getAllGymBranches();
	
	void deleteGymBranch(Integer gymId);
}
