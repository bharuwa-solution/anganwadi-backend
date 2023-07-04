package com.anganwadi.anganwadi.domains.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;




@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection="VaccinationName")
public class VaccinationName {
	
	@Id
	private String id;
	@Indexed
	private String vaccineCode;
	private String vaccineName;
}
