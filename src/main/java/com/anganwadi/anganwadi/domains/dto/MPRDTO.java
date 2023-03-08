package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MPRDTO {

    private long male;
    private long female;
    private long dharti;
    private long pregnant;
    private long mortality;
    private long birth;

}
