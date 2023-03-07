package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class householdsHeadList {

    private String headName;
    private String religion;
    private String category;
    private String headDob;
    private String totalMembers;
    private String headGender;
    private String headPic;

}
