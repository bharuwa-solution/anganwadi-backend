package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.domains.entity.AnganwadiCenter;
import com.anganwadi.anganwadi.repositories.AnganwadiCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class CommonMethodsService {
    private final AnganwadiCenterRepository anganwadiCenterRepository;


    @Autowired
    public CommonMethodsService(AnganwadiCenterRepository anganwadiCenterRepository) {
        this.anganwadiCenterRepository = anganwadiCenterRepository;
    }

    public String findCenterName(String centerId) {
        AnganwadiCenter centers = anganwadiCenterRepository.findById(centerId).get();
        return centers.getCenterName().trim();

    }


    public String startDateOfMonth() {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return formatters.format(LocalDate.now().withDayOfMonth(1));

    }

    public String endDateOfMonth() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        long millis = new Date().getTime();
        return df.format(new Date(millis));
    }


}
