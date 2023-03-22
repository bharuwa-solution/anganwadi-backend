package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistributionOutputList {

    private String name;
    private String profilePic;
    private String houseNo;
    private String date;
    private List<DistributionArrayList> arrayLists;

}
