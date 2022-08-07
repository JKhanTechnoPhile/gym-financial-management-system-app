package com.hiddu.gym.enterprise.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiddu.gym.enterprise.payloads.ApiResponse;
import com.hiddu.gym.enterprise.payloads.GymBranchDto;
import com.hiddu.gym.enterprise.services.GymBranchService;

@RestController
@RequestMapping("/api/branches")
public class GymBranchController {

	@Autowired
	private GymBranchService gymBranchService;

	@PostMapping("/")
	public ResponseEntity<GymBranchDto> createGymBranch(@Valid @RequestBody GymBranchDto gymBranchDto) {
		GymBranchDto createdGymBranchDto = this.gymBranchService.createGymBranch(gymBranchDto);
		return new ResponseEntity<>(createdGymBranchDto, HttpStatus.CREATED);
	}

	@PutMapping("/{gymCode}")
	public ResponseEntity<GymBranchDto> updateGymBranch(@Valid @RequestBody GymBranchDto gymBranchDto, @PathVariable("gymCode") Integer gymCode) {
		GymBranchDto updatedGymBranchDto = this.gymBranchService.updateGymBranch(gymBranchDto, gymCode);
		return ResponseEntity.ok(updatedGymBranchDto);
	}

	@DeleteMapping("/{gymCode}")
	public ResponseEntity<ApiResponse> deleteGymBranch(@PathVariable Integer gymCode) {
		this.gymBranchService.deleteGymBranch(gymCode);
		return ResponseEntity.ok(new ApiResponse("Gym branch deleted successfully", true));
	}

	@GetMapping("/")
	public ResponseEntity<List<GymBranchDto>> getAllGymBranches() {
		return ResponseEntity.ok(this.gymBranchService.getAllGymBranches());
	}

	@GetMapping("/{gymCode}")
	public ResponseEntity<GymBranchDto> getGymBranch(@PathVariable Integer gymCode) {
		return ResponseEntity.ok(this.gymBranchService.getGymBranchById(gymCode));
	}

}
