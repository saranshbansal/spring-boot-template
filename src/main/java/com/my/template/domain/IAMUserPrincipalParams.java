package com.my.template.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for IAM parameters
 *
 * Created by Saransh Bansal on 03/07/2023
 */
@Getter
@AllArgsConstructor
public enum IAMUserPrincipalParams {
	ACTIVE_KEY("active"),
	USERNAME_KEY("clientstaff_wiam02_atm_username"),
	DOMAIN_KEY("clientstaff_wiam02_atm_domain"),
	EMAIL_KEY("clientstaff_wiam02_atm_email"),
	FIRSTNAME_KEY("clientstaff_wiam02_atm_firstname"),
	LASTNAME_KEY("clientstaff_wiam02_atm_lastname"),
	MEMBEROF_KEY("clientstaff_wiam02_atm_memberof");

	private final String value;
}
