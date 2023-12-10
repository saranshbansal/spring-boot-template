package com.my.template.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents IAM user dto
 *
 * Created by Saransh Bansal on 30/06/2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalUserDto {
	private Boolean isActive;
	private String email;
	private String domain;
	private String userName;
	private String firstName;
	private String lastName;
	private String memberOf;
	private Boolean hasMultipleTeams;
}