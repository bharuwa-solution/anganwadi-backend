package com.anganwadi.anganwadi.domains.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseObject {

    @Id
    private String id;
    private boolean deleted = false;
    private boolean isActive = true;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date updatedDate;
    @CreatedBy
    private String createdUserId;
    @LastModifiedBy
    private String modifiedUserId;


}
