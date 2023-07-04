package com.anganwadi.anganwadi.domains.dto;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
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
