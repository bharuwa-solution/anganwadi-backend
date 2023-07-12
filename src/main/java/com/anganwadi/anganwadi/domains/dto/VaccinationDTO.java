package com.anganwadi.anganwadi.domains.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationDTO {

	private String id;
	private String vaccinationCode;
	private String vaccinationName;
	private String message;
	
}
