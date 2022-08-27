package com.hiddu.gym.enterprise.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiddu.gym.enterprise.payloads.ApiResponse;
import com.hiddu.gym.enterprise.payloads.GymSubscriptionPlanDto;
import com.hiddu.gym.enterprise.services.GymSubscriptionPlanService;

@RestController
@RequestMapping("/api/subscriptions")
public class GymSubscriptionPlanController {

	@Autowired
	private GymSubscriptionPlanService gymSubscriptionPlanService;

	@PreAuthorize("hasRole('PLATFORM_ADMIN')")
	@PostMapping("/")
	public ResponseEntity<GymSubscriptionPlanDto> createSubscriptionPlan(@Valid @RequestBody GymSubscriptionPlanDto gymSubscriptionPlanDto) {
		GymSubscriptionPlanDto createdGymSubscriptionPlanDto = this.gymSubscriptionPlanService.createGymSubscriptionPlan(gymSubscriptionPlanDto);
		return new ResponseEntity<>(createdGymSubscriptionPlanDto, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('PLATFORM_ADMIN')")
	@PutMapping("/{gymCode}/{gymSubscriptionPlanCode}")
	public ResponseEntity<GymSubscriptionPlanDto> updateSubscriptionPlan(@Valid @RequestBody GymSubscriptionPlanDto gymSubscriptionPlanDto, @PathVariable String gymCode, @PathVariable("gymSubscriptionPlanCode") Integer gymSubscriptionPlanCode) {
		GymSubscriptionPlanDto updatedGymBranchDto = this.gymSubscriptionPlanService.updateGymSubscriptionPlan(gymSubscriptionPlanDto, gymCode, gymSubscriptionPlanCode);
		return ResponseEntity.ok(updatedGymBranchDto);
	}

	@PreAuthorize("hasRole('PLATFORM_ADMIN')")
	@DeleteMapping("/{gymCode}/{gymSubscriptionPlanCode}")
	public ResponseEntity<ApiResponse> deleteSubscriptionPlan(@PathVariable String gymCode, @PathVariable Integer gymSubscriptionPlanCode) {
		this.gymSubscriptionPlanService.deleteGymSubscriptionPlan(gymCode, gymSubscriptionPlanCode);
		return ResponseEntity.ok(new ApiResponse("Gym branch deleted successfully", true));
	}

	@GetMapping("/")
	public ResponseEntity<List<GymSubscriptionPlanDto>> getAllSubscriptionPlans() {
		return ResponseEntity.ok(this.gymSubscriptionPlanService.getAllGymSubscriptionPlans());
	}

	@GetMapping("/{gymCode}/{gymSubscriptionPlanCode}")
	public ResponseEntity<GymSubscriptionPlanDto> getSubscriptionPlansByGymCodeAndId(@PathVariable String gymCode, @PathVariable Integer gymSubscriptionPlanCode) {
		return ResponseEntity.ok(this.gymSubscriptionPlanService.getGymSubscriptionPlanById(gymCode, gymSubscriptionPlanCode));
	}
	
	@GetMapping("/{gymCode}")
	public ResponseEntity<List<GymSubscriptionPlanDto>> getAllSubscriptionPlansByGymCode(@PathVariable String gymCode) {
		return ResponseEntity.ok(this.gymSubscriptionPlanService.getGymSubscriptionPlansByBranch(gymCode));
	}

}
