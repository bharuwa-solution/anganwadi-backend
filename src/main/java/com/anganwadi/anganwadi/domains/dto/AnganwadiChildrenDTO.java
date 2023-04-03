package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnganwadiChildrenDTO {

    private long gen;
    private long obc;
    private long sc;
    private long st;
    private long minority;
    private String month;

}
