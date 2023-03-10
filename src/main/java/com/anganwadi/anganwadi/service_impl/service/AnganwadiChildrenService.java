package com.anganwadi.anganwadi.service_impl.service;

import com.anganwadi.anganwadi.domains.dto.ChildrenDTO;
import com.anganwadi.anganwadi.domains.dto.DashboardDetails;
import com.anganwadi.anganwadi.domains.dto.UploadDTO;
import com.anganwadi.anganwadi.domains.entity.AnganwadiChildren;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface AnganwadiChildrenService {


    AnganwadiChildren saveChildrenRecord(AnganwadiChildren anganwadiChildren) throws ParseException, IOException;

    List<ChildrenDTO> getTotalChildren();

    UploadDTO uploadPic(MultipartFile file) throws IOException;

    DashboardDetails getDashboardDetails();

    DashboardDetails getAttendance();
}
