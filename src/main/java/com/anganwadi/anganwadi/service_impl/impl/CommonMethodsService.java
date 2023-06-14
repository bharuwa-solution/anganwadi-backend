package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.domains.entity.AnganwadiCenter;
import com.anganwadi.anganwadi.domains.entity.FamilyMember;
import com.anganwadi.anganwadi.exceptionHandler.CustomException;
import com.anganwadi.anganwadi.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@Slf4j
public class CommonMethodsService {
    private final AnganwadiCenterRepository anganwadiCenterRepository;
    private final AnganwadiChildrenRepository anganwadiChildrenRepository;
    private final PregnantAndDeliveryRepository pregnantAndDeliveryRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final AttendanceRepository attendanceRepository;

    @Autowired
    public CommonMethodsService(AnganwadiCenterRepository anganwadiCenterRepository, PregnantAndDeliveryRepository pregnantAndDeliveryRepository,
                                FamilyMemberRepository familyMemberRepository,AttendanceRepository attendanceRepository,
                                AnganwadiChildrenRepository anganwadiChildrenRepository) {
        this.anganwadiCenterRepository = anganwadiCenterRepository;
        this.pregnantAndDeliveryRepository = pregnantAndDeliveryRepository;
        this.familyMemberRepository=familyMemberRepository;
        this.attendanceRepository=attendanceRepository;
        this.anganwadiChildrenRepository=anganwadiChildrenRepository;
    }

    public String findCenterName(String centerId) {
        try {
            AnganwadiCenter centers = anganwadiCenterRepository.findById(centerId).get();
            return centers.getCenterName().trim();
        } catch (Exception e) {
            throw new CustomException("Error With CenterId, Please Contact Support Team");
        }
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

    public long pregnantWomenCount(String centerId) {
        return pregnantAndDeliveryRepository.countPregnantWomenByCenterId(centerId);
    }

    public long dhartiWomenCount(String centerId) {
        LocalDateTime date = LocalDateTime.now().minusMonths(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();
        System.out.println();
        return pregnantAndDeliveryRepository.countDhartiWomenByCenterId(convertToMills, centerId);
    }
    public long childrenCount(String centerId) {
        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();

        return familyMemberRepository.countChildrenByCenterId(convertToMills, centerId);
    }

    public String todayAttendance(String centerId) throws ParseException {

       DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
       long currentDate = new Date().getTime();
       String convertToString = df.format(currentDate);
       Date date = df.parse(convertToString);

       log.info("Date : "+date.getTime());
       long totalStudents = anganwadiChildrenRepository.countByCenterNameAndRegisteredTrue(findCenterName(centerId).trim());
       long todayAttendance = attendanceRepository.countByDateAndCenterName(date.getTime(),findCenterName(centerId).trim(), Sort.by(Sort.Direction.DESC, "createdDate"));

       return todayAttendance+"/"+totalStudents;

    }

    public long checkAgeCriteria(long criteria){
        LocalDateTime date = LocalDateTime.now().minusYears(criteria);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }



    public String getEndDateOfMonth(String month){
        String endDate = "";

        if(month.equals("1") || month.equals("3")  || month.equals("5") || month.equals("7") || month.equals("8") || month.equals("10") || month.equals("12")){

            endDate = "31-" + month + "-" + LocalDate.now().getYear();
        }
        else if (month.equals("2")) {

            if (LocalDate.now().isLeapYear()) {
                endDate = "29-" + month + "-" + LocalDate.now().getYear();
            } else {
                endDate = "28-03" + LocalDate.now().getYear();
            }

        } else {
            endDate = "30-" + month + "-" + LocalDate.now().getYear();
        }

        return endDate;
    }

    public Boolean checkMortalityDate(String memberId) {
        boolean isDead = false;

        if (familyMemberRepository.findById(memberId).isPresent()) {
            FamilyMember member = familyMemberRepository.findById(memberId).get();
            if (member.getDateOfMortality().length() > 0) {
                isDead = true;
            }        }
        return isDead;
    }


}
