
// Auto Update Pregnancy House visits
private void autoUpdatePregnantVisits(List<PregnantAndDelivery> membersList){

        Set<String> uniqueMember=new HashSet<>();
        for(PregnantAndDelivery members:membersList){

        String combinedId=members.getMotherMemberId()+members.getDob();

        if(uniqueMember.add(combinedId) && members.getLastMissedPeriodDate()>0){
        LocalDate localDate=Instant.ofEpochMilli(members.getLastMissedPeriodDate()).atZone(ZoneId.systemDefault()).toLocalDate();

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .visitType("1")
        .centerId(members.getCenterId())
        .centerName(members.getCenterName())
        .visitName("TILL_04_06_MONTHS")
        .memberId(members.getMotherMemberId())
        .dueDate(localDate.plusDays(119).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .visitType("1")
        .centerId(members.getCenterId())
        .centerName(members.getCenterName())
        .visitName("TILL_04_06_MONTHS")
        .memberId(members.getMotherMemberId())
        .dueDate(localDate.plusDays(149).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());


        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .visitType("1")
        .centerId(members.getCenterId())
        .centerName(members.getCenterName())
        .visitName("TILL_04_06_MONTHS")
        .memberId(members.getMotherMemberId())
        .dueDate(localDate.plusDays(179).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());


        // Visit Type 2. With in 7-9 Months

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .visitType("2")
        .centerId(members.getCenterId())
        .centerName(members.getCenterName())
        .visitName("TILL_07_09_MONTHS")
        .memberId(members.getMotherMemberId())
        .dueDate(localDate.plusDays(209).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .visitType("2")
        .centerId(members.getCenterId())
        .centerName(members.getCenterName())
        .visitName("TILL_07_09_MONTHS")
        .memberId(members.getMotherMemberId())
        .dueDate(localDate.plusDays(239).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .visitType("2")
        .centerId(members.getCenterId())
        .centerName(members.getCenterName())
        .visitName("TILL_07_09_MONTHS")
        .memberId(members.getMotherMemberId())
        .dueDate(localDate.plusDays(269).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        // Visit Type 3. Day Of Delivery
        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .visitType("3")
        .centerId(members.getCenterId())
        .centerName(members.getCenterName())
        .visitName("DAY_OF_DELIVERY")
        .memberId(members.getMotherMemberId())
        .dueDate(localDate.plusDays(279).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());
        }
        }
        }


    // Auto Update Pregnancy Vaccination
    private void autoUpdatePregnancyVaccination(List<PregnantAndDelivery> membersList){

        Set<String> uniqueMember=new HashSet<>();
        for(PregnantAndDelivery members:membersList){
        if(Arrays.stream(ApplicationConstants.ignoreCenters).noneMatch(members.getCenterId().trim()::equals)){

        if(uniqueMember.add(members.getMotherMemberId())){
        LocalDate localDate=Instant.ofEpochMilli(members.getLastMissedPeriodDate()).atZone(ZoneId.systemDefault()).toLocalDate();

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-1")
        .vaccinationName("TD-1")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getMotherMemberId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-2")
        .vaccinationName("TD-2")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(58).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getMotherMemberId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-3")
        .vaccinationName("TD_BOOSTER")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(72).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getMotherMemberId())
        .build());
        }
        }
        }
        }

// Auto Update After Birth House visits
private void autoUpdateBirthVisits(List<BabiesBirth> membersList){

        // Auto Update After Birth House visits

        Set<String> uniqueMemberSet=new HashSet<>();

        for(BabiesBirth members:membersList){

        if(Arrays.stream(ApplicationConstants.ignoreCenters).noneMatch(members.getCenterId().trim()::equals)){

        checkPrematureVisits(members.getMotherMemberId(),members.getDob());

        if(uniqueMember.add(members.getMotherMemberId())){
        LocalDate localDate=Instant.ofEpochMilli(members.getDob()).atZone(ZoneId.systemDefault()).toLocalDate();

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("4")
        .visitName("TILL_1_7_DAYS")
        .dueDate(localDate.plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("5")
        .visitName("TILL_8_30_DAYS")
        .dueDate(localDate.plusDays(29).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        // Visit TILL_1_5_.MONTHS

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("6")
        .visitName("TILL_1_5_MONTHS")
        .dueDate(localDate.plusDays(31).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("6")
        .visitName("TILL_1_5_MONTHS")
        .dueDate(localDate.plusDays(59).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("6")
        .visitName("TILL_1_5_MONTHS")
        .dueDate(localDate.plusDays(89).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("6")
        .visitName("TILL_1_5_MONTHS")
        .dueDate(localDate.plusDays(119).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("6")
        .visitName("TILL_1_5_MONTHS")
        .dueDate(localDate.plusDays(149).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());


        // Visit TILL_6_8_MONTHS

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("7")
        .visitName("TILL_6_8_MONTHS")
        .dueDate(localDate.plusDays(179).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("7")
        .visitName("TILL_6_8_MONTHS")
        .dueDate(localDate.plusDays(209).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("7")
        .visitName("TILL_6_8_MONTHS")
        .dueDate(localDate.plusDays(239).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());


        // Visit TILL_9_11_MONTHS

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("8")
        .visitName("TILL_9_11_MONTHS")
        .dueDate(localDate.plusDays(269).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("8")
        .visitName("TILL_9_11_MONTHS")
        .dueDate(localDate.plusDays(299).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("8")
        .visitName("TILL_9_11_MONTHS")
        .dueDate(localDate.plusDays(329).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        // Visit TILL_12_17_MONTHS

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("9")
        .visitName("TILL_12_17_MONTHS")
        .dueDate(localDate.plusDays(364).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("9")
        .visitName("TILL_12_17_MONTHS")
        .dueDate(localDate.plusDays(389).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("9")
        .visitName("TILL_12_17_MONTHS")
        .dueDate(localDate.plusDays(419).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("9")
        .visitName("TILL_12_17_MONTHS")
        .dueDate(localDate.plusDays(449).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("9")
        .visitName("TILL_12_17_MONTHS")
        .dueDate(localDate.plusDays(479).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("9")
        .visitName("TILL_12_17_MONTHS")
        .dueDate(localDate.plusDays(509).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());


        // Visit TILL_18_24_MONTHS

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("10")
        .visitName("TILL_18_24_MONTHS")
        .dueDate(localDate.plusDays(539).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("10")
        .visitName("TILL_18_24_MONTHS")
        .dueDate(localDate.plusDays(569).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("10")
        .visitName("TILL_18_24_MONTHS")
        .dueDate(localDate.plusDays(599).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("10")
        .visitName("TILL_18_24_MONTHS")
        .dueDate(localDate.plusDays(629).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("10")
        .visitName("TILL_18_24_MONTHS")
        .dueDate(localDate.plusDays(659).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("10")
        .visitName("TILL_18_24_MONTHS")
        .dueDate(localDate.plusDays(689).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());

        houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
        .memberId(members.getChildId())
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .visitType("10")
        .visitName("TILL_18_24_MONTHS")
        .dueDate(localDate.plusDays(719).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .build());
        }
        }
        }
        }


        // Auto Update After Birth Vaccination
private void autoUpdateBirthVaccination(List<BabiesBirth> membersList) {

        Set<String> uniqueMember = new HashSet<>();
        for (BabiesBirth members : membersList) {
        if (Arrays.stream(ApplicationConstants.ignoreCenters).noneMatch(members.getCenterId().trim()::equals)) {

        if (uniqueMember.add(members.getMotherMemberId())) {
        LocalDate localDate = Instant.ofEpochMilli(members.getDob()).atZone(ZoneId.systemDefault()).toLocalDate();

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-4")
        .vaccinationName("OPV-0")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(14).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-5")
        .vaccinationName("HEPATITIS-B")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(members.getDob())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-6")
        .vaccinationName("B.C.G.")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(members.getDob())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-6")
        .vaccinationName("B.C.G.")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(365).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-7")
        .vaccinationName("OPV-1,2&3")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());


        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-7")
        .vaccinationName("OPV-1,2&3")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(70).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-7")
        .vaccinationName("OPV-1,2&3")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-8")
        .vaccinationName("PENTAVALENT-1,2&3")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-8")
        .vaccinationName("PENTAVALENT-1,2&3")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(70).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-8")
        .vaccinationName("PENTAVALENT-1,2&3")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());


        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-9")
        .vaccinationName("ROTAVIRUS")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-9")
        .vaccinationName("ROTAVIRUS")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(70).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-9")
        .vaccinationName("ROTAVIRUS")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-10")
        .vaccinationName("FIPV")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-10")
        .vaccinationName("FIPV")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-11")
        .vaccinationName("PCV")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-11")
        .vaccinationName("PCV")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-12")
        .vaccinationName("MEASLES_RUBELLA_FIRST_DOSE")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(365).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-13")
        .vaccinationName("PCV_BOOSTER")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(270).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-14")
        .vaccinationName("VITAMIN_A_FIRST_DOSE")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(270).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-15")
        .vaccinationName("DPT_BOOSTER_1")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(720).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());


        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-16")
        .vaccinationName("MEASLES_RUBELLA_SECOND_DOSE")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(720).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-17")
        .vaccinationName("OPV_BOOSTER")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(720).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-18")
        .vaccinationName("VITAMIN_A")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(540).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-19")
        .vaccinationName("DPT_BOOSTER_2")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(2190).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());

        vaccinationScheduleRepository.save(VaccinationSchedule.builder()
        .code("V-20")
        .vaccinationName("TD")
        .vaccinationDate(0)
        .centerId(members.getCenterId())
        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
        .dueDate(localDate.plusDays(5840).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        .familyId(members.getFamilyId())
        .memberId(members.getChildId())
        .build());



        }
        }
        }
        }



