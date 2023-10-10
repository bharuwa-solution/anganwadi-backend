package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.domains.dto.FamilyMemberDTO;
import com.anganwadi.anganwadi.domains.entity.*;
import com.anganwadi.anganwadi.exceptionHandler.CustomException;
import com.anganwadi.anganwadi.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommonMethodsService {
    private final AnganwadiCenterRepository anganwadiCenterRepository;
    private final AnganwadiChildrenRepository anganwadiChildrenRepository;
    private final PregnantAndDeliveryRepository pregnantAndDeliveryRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final AttendanceRepository attendanceRepository;
    private final FamilyRepository familyRepository;
    private final VaccinationNameRepository vaccinationNameRepository;
    private final VaccinationRepository vaccinationRepository;
    private final BabiesBirthRepository babiesBirthRepository;
    private final WeightTrackingRepository weightTrackingRepository;
    private final VisitsRepository visitsRepository;
    private final BloodTestTrackingRepository bloodTestTrackingRepository;
    private final VaccinationScheduleRepository vaccinationScheduleRepository;
    private final HouseVisitScheduleRepository houseVisitScheduleRepository;


    @Autowired
    public CommonMethodsService(AnganwadiCenterRepository anganwadiCenterRepository, PregnantAndDeliveryRepository pregnantAndDeliveryRepository,
                                FamilyMemberRepository familyMemberRepository, AttendanceRepository attendanceRepository,
                                AnganwadiChildrenRepository anganwadiChildrenRepository, FamilyRepository familyRepository,
                                VaccinationNameRepository vaccinationNameRepository, VaccinationRepository vaccinationRepository,
                                BabiesBirthRepository babiesBirthRepository, WeightTrackingRepository weightTrackingRepository,
                                VisitsRepository visitsRepository, BloodTestTrackingRepository bloodTestTrackingRepository,
                                VaccinationScheduleRepository vaccinationScheduleRepository, HouseVisitScheduleRepository houseVisitScheduleRepository
    ) {

        this.anganwadiCenterRepository = anganwadiCenterRepository;
        this.pregnantAndDeliveryRepository = pregnantAndDeliveryRepository;
        this.familyMemberRepository = familyMemberRepository;
        this.attendanceRepository = attendanceRepository;
        this.anganwadiChildrenRepository = anganwadiChildrenRepository;
        this.familyRepository = familyRepository;
        this.vaccinationNameRepository = vaccinationNameRepository;
        this.vaccinationRepository = vaccinationRepository;
        this.babiesBirthRepository = babiesBirthRepository;
        this.weightTrackingRepository = weightTrackingRepository;
        this.visitsRepository = visitsRepository;
        this.bloodTestTrackingRepository = bloodTestTrackingRepository;
        this.vaccinationScheduleRepository = vaccinationScheduleRepository;
        this.houseVisitScheduleRepository = houseVisitScheduleRepository;
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


    public String calBMI(String height, String weight) {
        String status = "";
        if (height.trim().length() > 0 && weight.trim().length() > 0) {
            float meter = Float.parseFloat(height) / 100;
            float weightInt = Float.parseFloat(weight);

            float resul = weightInt / (meter * meter);

            log.error("weight " + resul);

            if (resul >= 5 && resul <= 15) {
                status = "NORMAL";
            } else if (resul < 5) {
                status = "UNDER_WEIGHT";
            } else {
                status = "OVER_WEIGHT";
            }
        }

        return status;
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

        log.info("Date : " + date.getTime());
        long totalStudents = anganwadiChildrenRepository.countByCenterIdAndRegisteredTrue(centerId);
        long todayAttendance = attendanceRepository.countByDateAndCenterId(date.getTime(), centerId, Sort.by(Sort.Direction.DESC, "createdDate"));

        return todayAttendance + "/" + totalStudents;

    }

    public long checkAgeCriteria(long criteria) {
        LocalDateTime date = LocalDateTime.now().minusYears(criteria);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    public String getStartDate(String startMonth) throws ParseException {
        String inputMonth = startMonth == null ? "" : startMonth;
        String output = "";
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        output = "01-" + inputMonth + "-" + year;

        log.error("startDate " + output);
        return output;
    }

    public String getEndDateOfMonth(String month) {
        String endDate = "";

        if (month.equals("1") || month.equals("3") || month.equals("5") || month.equals("7") || month.equals("8") || month.equals("10") || month.equals("12")) {

            endDate = "31-" + month + "-" + LocalDate.now().getYear();
        } else if (month.equals("2")) {

            if (LocalDate.now().isLeapYear()) {
                endDate = "29-" + month + "-" + LocalDate.now().getYear();
            } else {
                endDate = "28-02-" + LocalDate.now().getYear();
            }

        } else {
            endDate = "30-" + month + "-" + LocalDate.now().getYear();
        }

        return endDate;
    }
    
    public String getStartDateOfMonth(String month) {
    	String startDate="01-"+month+"-"+LocalDate.now().getYear();
    	
    	return startDate;
    }
    
    public Boolean checkMortalityDate(String memberId) {
        boolean isDead = false;

        if (familyMemberRepository.findById(memberId).isPresent()) {
            FamilyMember member = familyMemberRepository.findById(memberId).get();
            if (member.getDateOfMortality().length() > 0) {
                isDead = true;
            }
        }
        return isDead;
    }

    public String dateChangeToString(long dob) {
        DateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
        Date res = new Date(dob);
        return obj.format(res);
    }

    public long dateChangeToLong(String dateStr) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = sdf.parse(dateStr);

        return date.getTime();
    }

    public FamilyMember findMember(String id) {
        if (!familyMemberRepository.findById(id).isPresent()) {
            throw new CustomException("Family Member Not Found");
        }
        return familyMemberRepository.findById(id).get();
    }

    public Family findFamily(String familyId) {
        return familyRepository.findByFamilyId(familyId);
    }

    public void checkAbove3Yrs(String dateForCheck) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Date date = df.parse(dateForCheck);
        long dob = date.getTime();

        LocalDateTime check3YrsCriteria = LocalDateTime.now().minusYears(3);
        ZonedDateTime zdt = ZonedDateTime.of(check3YrsCriteria, ZoneId.systemDefault());

        long convertToMills = zdt.toInstant().toEpochMilli();
        log.info("Dob : " + dob);
        log.info("Age Limit : " + convertToMills);
        if (dob >= convertToMills) {
            throw new CustomException("Children Below 3 Years, Can't be Added");
        }

    }

    public void checkBelow6yrs(String dateForCheck) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Date date = df.parse(dateForCheck);
        long dob = date.getTime();

        LocalDateTime check6YrsCriteria = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(check6YrsCriteria, ZoneId.systemDefault());

        long convertToMills = zdt.toInstant().toEpochMilli();
        log.info("Dob : " + dob);
        log.info("Age Limit : " + convertToMills);
        if (dob <= convertToMills) {
            throw new CustomException("Children Above 6 Years, Can't be Added");
        }
    }

    public String getVaccineName(String vaccineCode){
        String vaccineName = "";

        Optional<VaccinationName> checkCode = vaccinationNameRepository.findByVaccineCode(vaccineCode);

        if(checkCode.isPresent()) {
            vaccineName = checkCode.get().getVaccineName();
        }
        log.error("Vaccine Name :"+vaccineName);
        return vaccineName;

    }

    public void removeMemberFromAssociatedTable(String memberId){

        List<Vaccination> removeChild = vaccinationRepository.findByChildId(memberId,Sort.by(Sort.Direction.ASC, "createdDate"));
        List<Vaccination> removeMother = vaccinationRepository.findByMotherId(memberId,Sort.by(Sort.Direction.ASC, "createdDate"));


        if(removeChild.size()>0){
            vaccinationRepository.deleteAllByChildId(memberId);
        }

        if(removeMother.size()>0){
            vaccinationRepository.deleteAllByMotherId(memberId);
        }


        anganwadiChildrenRepository.deleteAllByChildId(memberId);
        attendanceRepository.deleteByChildId(memberId);
        babiesBirthRepository.deleteById(memberId);
        weightTrackingRepository.deleteByChildId(memberId);
        visitsRepository.deleteByMemberId(memberId);
        pregnantAndDeliveryRepository.deleteByMotherMemberId(memberId);


    }

    public void removeFromVaccinationSchedule(String id) {
        if (!StringUtils.isEmpty(id)) {
            List<VaccinationSchedule> hs = vaccinationScheduleRepository.findAllByMemberId(id);
            if (hs.size() > 0) {
                vaccinationScheduleRepository.deleteByMemberId(id);
            }
        }

    }

    public void removeFromHouseVisitSchedule(String id) {
        if (!StringUtils.isEmpty(id)) {
            List<HouseVisitSchedule> hvs = houseVisitScheduleRepository.findAllByMemberId(id);
            if (hvs.size() > 0) {
                houseVisitScheduleRepository.deleteByMemberId(id);
            }
        }
    }


    public void memberSoftDelete(FamilyMemberDTO familyMemberDTO, String DateOfMortality) {

        if (DateOfMortality.length() > 0) {

            // Updating in Anganwadi Children, If Exists

            List<AnganwadiChildren> ac = anganwadiChildrenRepository
                    .findAllByChildIdAndRegisteredTrue(familyMemberDTO.getId());

            if (ac.size() > 0) {
                for (AnganwadiChildren children : ac) {

                    children.setDeleted(true);
                    children.setActive(false);

                    anganwadiChildrenRepository.save(children);
                }
            }

            // Updating in BabiesBirth
            List<BabiesBirth> checkInBirth = babiesBirthRepository.findAllByChildId(familyMemberDTO.getId());

            for (BabiesBirth bb : checkInBirth) {
                bb.setActive(false);
                bb.setDeleted(true);
                babiesBirthRepository.save(bb);
            }

            // Updating in BloodTestTracking
            List<BloodTestTracking> checkMotherId = bloodTestTrackingRepository.findByMotherId(familyMemberDTO.getId());
            List<BloodTestTracking> checkChildId = bloodTestTrackingRepository.findByMemberId(familyMemberDTO.getId());


            if (checkMotherId.size() > 0) {

                for (BloodTestTracking motherId : checkMotherId) {
                    motherId.setActive(false);
                    motherId.setDeleted(true);
                    bloodTestTrackingRepository.save(motherId);
                }

            } else if (checkChildId.size() > 0) {

                for (BloodTestTracking childId : checkChildId) {
                    childId.setActive(false);
                    childId.setDeleted(true);
                    bloodTestTrackingRepository.save(childId);
                }
            }

            // Remove From Scheduler Tables

            removeFromVaccinationSchedule(familyMemberDTO.getId());
            removeFromHouseVisitSchedule(familyMemberDTO.getId());


            // Updating Vaccination
            List<Vaccination> checkMotherVaccine = vaccinationRepository.findByMotherId(familyMemberDTO.getId(), Sort.by(Sort.Direction.ASC, "createdDate"));
            List<Vaccination> checkChildVaccine = vaccinationRepository.findByChildId(familyMemberDTO.getId(), Sort.by(Sort.Direction.ASC, "createdDate"));

            if (checkMotherVaccine.size() > 0) {

                for (Vaccination motherId : checkMotherVaccine) {
                    motherId.setActive(false);
                    motherId.setDeleted(true);
                    vaccinationRepository.save(motherId);
                }

            } else if (checkChildVaccine.size() > 0) {

                for (Vaccination childId : checkChildVaccine) {
                    childId.setDeleted(true);
                    childId.setActive(false);
                    vaccinationRepository.save(childId);
                }

            }

            // Updating Weight Tracking

            List<WeightTracking> weightTrackings = weightTrackingRepository.findAllByChildId(familyMemberDTO.getId(), Sort.by(Sort.Direction.ASC, "createdDate"));

            for (WeightTracking wt : weightTrackings) {
                wt.setActive(false);
                wt.setDeleted(true);
                weightTrackingRepository.save(wt);
            }
        }

        // Updating Pregnant & Delivery Table

        List<PregnantAndDelivery> findMother = pregnantAndDeliveryRepository.findByMotherMemberIdAndDeliveryCriteria(familyMemberDTO.getId());

        if (findMother.size() > 0) {
            for (PregnantAndDelivery ppd : findMother) {
                ppd.setActive(false);
                ppd.setDeleted(true);
                pregnantAndDeliveryRepository.save(ppd);
            }
        }


        // Updating Visits

        List<Visits> checkMotherVisits = visitsRepository.findAllByMotherId(familyMemberDTO.getId(), Sort.by(Sort.Direction.DESC, "createdDate"));
        List<Visits> checkChildVisits = visitsRepository.findAllByMemberId(familyMemberDTO.getId());

        if (checkMotherVisits.size() > 0) {
            for (Visits motherId : checkChildVisits) {
                motherId.setActive(false);
                motherId.setDeleted(true);
                visitsRepository.save(motherId);
            }
        } else if (checkChildVisits.size() > 0) {

            for (Visits childId : checkChildVisits) {
                childId.setActive(false);
                childId.setDeleted(true);
                visitsRepository.save(childId);
            }
        }

    }

}
