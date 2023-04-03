package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.domains.entity.*;
import com.anganwadi.anganwadi.repositories.*;
import com.anganwadi.anganwadi.service_impl.service.AnganwadiChildrenService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class AnganwadiChildrenServiceImpl implements AnganwadiChildrenService {

    private final AnganwadiChildrenRepository anganwadiChildrenRepository;
    private final FileManagementService fileManagementService;
    private final AttendanceRepository attendanceRepository;
    private final ModelMapper modelMapper;
    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyServiceImpl familyServiceImpl;
    private final AssetsStockRepository assetsStockRepository;
    private final StockListRepository stockListRepository;
    private final StockDistributionRepository stockDistributionRepository;
    private final MealsRepository mealsRepository;
    private final WeightTrackingRepository weightTrackingRepository;

    @Autowired
    public AnganwadiChildrenServiceImpl(AnganwadiChildrenRepository anganwadiChildrenRepository, FileManagementService fileManagementService,
                                        AttendanceRepository attendanceRepository, ModelMapper modelMapper,
                                        FamilyRepository familyRepository, FamilyMemberRepository familyMemberRepository,
                                        FamilyServiceImpl familyServiceImpl, AssetsStockRepository assetsStockRepository,
                                        StockListRepository stockListRepository, StockDistributionRepository stockDistributionRepository,
                                        MealsRepository mealsRepository, WeightTrackingRepository weightTrackingRepository) {
        this.anganwadiChildrenRepository = anganwadiChildrenRepository;
        this.fileManagementService = fileManagementService;
        this.attendanceRepository = attendanceRepository;
        this.modelMapper = modelMapper;
        this.familyRepository = familyRepository;
        this.familyMemberRepository = familyMemberRepository;
        this.familyServiceImpl = familyServiceImpl;
        this.assetsStockRepository = assetsStockRepository;
        this.stockListRepository = stockListRepository;
        this.stockDistributionRepository = stockDistributionRepository;
        this.mealsRepository = mealsRepository;
        this.weightTrackingRepository = weightTrackingRepository;
    }


    @Override
    public SaveAdmissionDTO saveChildrenRecord(SaveAdmissionDTO saveAdmissionDTO, String centerName) throws ParseException {


        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));


        String finalDate = saveAdmissionDTO.getDob();
        Date dob = df2.parse(finalDate);

        AnganwadiChildren saveAdmission = AnganwadiChildren.builder()
                .name(saveAdmissionDTO.getName() == null ? "" : saveAdmissionDTO.getName())
                .familyId(saveAdmissionDTO.getFamilyId() == null ? "" : saveAdmissionDTO.getFamilyId())
                .childId(saveAdmissionDTO.getChildId() == null ? "" : saveAdmissionDTO.getChildId())
                .fatherName(saveAdmissionDTO.getFatherName() == null ? "" : saveAdmissionDTO.getFatherName())
                .motherName(saveAdmissionDTO.getMotherName() == null ? "" : saveAdmissionDTO.getMotherName())
                .centerName(centerName)
                .dob(df.format(dob))
                .gender(saveAdmissionDTO.getGender()==null?"":saveAdmissionDTO.getGender())
                .mobileNumber(saveAdmissionDTO.getMobileNumber()==null?"":saveAdmissionDTO.getMobileNumber())
                .category(saveAdmissionDTO.getCategory()==null?"":saveAdmissionDTO.getCategory())
                .minority(saveAdmissionDTO.getMinority()==null?"":saveAdmissionDTO.getMinority())
                .handicap(saveAdmissionDTO.getHandicap()==null?"":saveAdmissionDTO.getHandicap())
                .profilePic(saveAdmissionDTO.getProfilePic()==null?"":saveAdmissionDTO.getProfilePic())
                .build();

        anganwadiChildrenRepository.save(saveAdmission);

        return modelMapper.map(saveAdmission, SaveAdmissionDTO.class);
    }

    @Override
    public List<ChildrenDTO> getTotalChildren(String centerName) {


        List<ChildrenDTO> addInList = new ArrayList<>();
        List<AnganwadiChildren> childrenList = anganwadiChildrenRepository.findAllByCenterName(centerName);

        for (AnganwadiChildren getChildren : childrenList) {
            ChildrenDTO childrenDTO = ChildrenDTO.builder()
                    .dob(getChildren.getDob())
                    .category(getChildren.getCategory())
                    .name(getChildren.getName())
                    .gender(getChildren.getGender())
                    .profilePic(getChildren.getProfilePic())
                    .build();
            addInList.add(childrenDTO);
        }

        return addInList;
    }

    @Override
    public UploadDTO uploadPic(MultipartFile file) throws IOException {
        return UploadDTO.builder()
                .url(fileManagementService.uploadPic(file))
                .build();
    }

//    @Override
//    public DashboardDetails getDashboardDetails() {
//        return null;
//    }


    @Override
    public List<AttendanceDTO> getAttendanceByDate(String date, String centerName) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date formatToTime = df.parse(date);
        long timestamp = formatToTime.getTime();

//        markAsAbsent(centerName);

        List<Attendance> findRecords = attendanceRepository.findAllByDateAndCenterName(timestamp, centerName, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<AttendanceDTO> addList = new ArrayList<>();

        for (Attendance singleRecord : findRecords) {
            AttendanceDTO dailyRecord = AttendanceDTO.builder()
                    .childId(singleRecord.getChildId())
                    .name(singleRecord.getName())
                    .gender(singleRecord.getGender())
                    .dob(singleRecord.getDob())
                    .photo(singleRecord.getPhoto())
                    .attendance(singleRecord.getAttendance())
                    .date(singleRecord.getDate())
                    .build();

            addList.add(dailyRecord);
        }

        return addList;

    }

    private String checkAttendanceOnDay(String childId, long date, String centerName) {
        String attendance = "";
        List<Attendance> findChild = attendanceRepository.findAllByChildIdAndDateAndCenterName(childId, date, centerName);

        if (findChild.size() > 0) {
            for (Attendance checkAttendance : findChild) {
                attendance = checkAttendance.getAttendance();
            }
        }

        return attendance;

    }

    private void markAsAbsent(String centerName) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date currentTime = new Date();
        String formatToString = df.format(currentTime.getTime());
        Date formatToTime = df.parse(formatToString);
        long timestamp = formatToTime.getTime();

        List<AnganwadiChildren> findChildren = anganwadiChildrenRepository.findAllByCenterName(centerName);
        String attendance = "A";


        for (AnganwadiChildren getId : findChildren) {
            List<Attendance> lastVerify = attendanceRepository.findAllByChildIdAndDateAndCenterName(getId.getChildId(), timestamp, centerName);
           String  verifyAttend = checkAttendanceOnDay(getId.getChildId(), timestamp, centerName);
            if (lastVerify.size() <= 0) {

                Attendance saveAttendance = Attendance.builder()
                        .childId(getId.getChildId())
                        .dob(getId.getDob())
                        .centerName(centerName)
                        .name(getId.getName())
                        .photo(getId.getProfilePic())
                        .gender(getId.getGender())
                        .date(timestamp)
                        .attendance(verifyAttend.equals("") ? attendance : verifyAttend)
                        .build();
                attendanceRepository.save(saveAttendance);

            }

        }

    }


    private void markPresent(String childId, String latitude, String longitude, long timestamp) {

        String[] splitString = childId.split(",");

        for (String getChildId : splitString) {
            List<Attendance> findChildInRecord = attendanceRepository.updateAttendance(getChildId.trim(), timestamp);

            if (findChildInRecord.size() > 0) {

                for (Attendance markAttendance : findChildInRecord) {
                    markAttendance.setLatitude(latitude);
                    markAttendance.setLongitude(longitude);
                    markAttendance.setAttendance("P");
                    attendanceRepository.save(markAttendance);
                }
            }
        }

    }

    @Override
    public List<AttendanceDTO> makeAndUpdateAttendance(AttendanceDTO attendanceDTO, String centerName) throws ParseException {

        // convert date to millis

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date currentTime = new Date();
        String formatToString = df.format(currentTime.getTime());
        Date formatToTime = df.parse(formatToString);
        long timestamp = formatToTime.getTime();

        // set default value
        boolean isMarked = false;

        List<AttendanceDTO> addList = new ArrayList<>();

        // Checking if record exists for current date
        List<Attendance> checkDailyAttendance = attendanceRepository.findAllByDate(timestamp, Sort.by(Sort.Direction.DESC, "createdDate"));

        // updating if not exists
        markAsAbsent(centerName);

        // After updating Above fields

        markPresent(attendanceDTO.getChildId(), attendanceDTO.getLatitude(), attendanceDTO.getLongitude(), timestamp);

        List<Attendance> getDetails = attendanceRepository.findAllByDateAndCenterName(timestamp, centerName, Sort.by(Sort.Direction.DESC, "createdDate"));


        for (Attendance fetchDetails : getDetails) {

            AttendanceDTO singleEntry = AttendanceDTO
                    .builder()
                    .centerName(centerName)
                    .childId(fetchDetails.getChildId())
                    .dob(fetchDetails.getDob())
                    .name(fetchDetails.getName())
                    .latitude(attendanceDTO.getLatitude())
                    .longitude(attendanceDTO.getLongitude())
                    .photo(fetchDetails.getPhoto())
                    .gender(fetchDetails.getGender())
                    .date(timestamp)
                    .attendance(fetchDetails.getAttendance())
                    .build();

            addList.add(singleEntry);
        }
        return addList;

    }

    @Override
    public List<householdsHeadList> getRegisteredHouseholdsList(String centerName) {
        List<householdsHeadList> addInList = new ArrayList<>();
        List<AnganwadiChildren> findFamilyIds = anganwadiChildrenRepository.findAllByCenterName(centerName);
        HashSet<String> uniqueFamilyIds = new HashSet<>();

        if (findFamilyIds.size() > 0) {
            String familyId = "";
            for (AnganwadiChildren ac : findFamilyIds) {

                if (uniqueFamilyIds.add(ac.getFamilyId())) {

                    List<Family> checkHouseDetails = familyRepository.findAllByFamilyIdIn(ac.getFamilyId());
                    if (checkHouseDetails.size() > 0) {
                        for (Family familyDetails : checkHouseDetails) {

                            String headName = "", dob = "", pic = "", gender = "", houseNo = "", religion = "", category = "";


                            houseNo = familyDetails.getHouseNo();
                            religion = familyDetails.getReligion();
                            category = familyDetails.getCategory();


                            List<FamilyMember> findHouseholds = familyMemberRepository.findAllByFamilyId(ac.getFamilyId(), Sort.by(Sort.Direction.DESC, "createdDate"));

                            for (FamilyMember findHeadDetails : findHouseholds) {
                                long millis = findHeadDetails.getDob();
                                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = new Date(millis);

                                if (findHeadDetails.getRelationWithOwner().equalsIgnoreCase("0")) {
                                    headName = findHeadDetails.getName();
                                    dob = df.format(date);
                                    pic = findHeadDetails.getPhoto();
                                    gender = findHeadDetails.getGender();
                                    break;
                                } else {
                                    headName = findHeadDetails.getName();
                                    dob = df.format(date);
                                    pic = findHeadDetails.getPhoto();
                                    gender = findHeadDetails.getGender();
                                }

                            }


                            long countMembers = familyMemberRepository.countByFamilyId(ac.getFamilyId());

                            householdsHeadList householdsDTO = householdsHeadList.builder()
                                    .headName(headName)
                                    .headDob(dob)
                                    .houseNo(houseNo)
                                    .familyId(ac.getFamilyId())
                                    .religion(religion)
                                    .headGender(gender)
                                    .totalMale(familyServiceImpl.totalHouseholdsMale(ac.getFamilyId()))
                                    .totalFemale(familyServiceImpl.totalHouseholdsFemale(ac.getFamilyId()))
                                    .totalChildren(familyServiceImpl.totalHouseholdsChildren(ac.getFamilyId()))
                                    .category(category)
                                    .totalMembers(String.valueOf(countMembers))
                                    .headPic(pic)
                                    .build();
                            addInList.add(householdsDTO);
                        }
                    }
                }
            }
        }


        return addInList;
    }

    private String sumOfItems(String centerName, String itemCode) {
        String itemSum = "";
        List<AssetsStock> findCenterItems = assetsStockRepository.findAllByCenterNameAndItemCode(centerName, itemCode);
        float itemInFloat = 0L;
        if (findCenterItems.size() > 0) {

            for (AssetsStock fs : findCenterItems) {

                itemInFloat = itemInFloat + Float.parseFloat(fs.getQty());

            }

        }

        itemSum = String.valueOf(itemInFloat);

        return itemSum;
    }

    @Override
    public List<StockListDTO> getAvailableItems() {
        List<StockList> stockLists = stockListRepository.findAll(Sort.by(Sort.Direction.ASC, "itemCode"));
        List<StockListDTO> addInList = new ArrayList<>();
        HashSet<String> uniqueStockCode = new HashSet<>();

        for (StockList mapItems : stockLists) {
            if (uniqueStockCode.add(mapItems.getItemCode())) {
                StockListDTO assets = StockListDTO.builder()
                        .itemCode(mapItems.getItemCode())
                        .quantity(mapItems.getQuantity())
                        .unit(mapItems.getUnit())
                        .itemName(mapItems.getItemName())
                        .build();
                addInList.add(assets);
            }
        }


        return addInList;
    }

    @Override
    public List<StockItemsDTO> addStocks(List<StockItemsDTO> assetsStock, String centerName) throws ParseException {

        List<StockItemsDTO> addInList = new ArrayList<>();


        for (StockItemsDTO assetsList : assetsStock) {


            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date currentTime = new Date();
            String formatToString = df.format(currentTime.getTime());
            Date formatToTime = df.parse(formatToString);
            long timestamp = formatToTime.getTime();
            String[] spiltMonth = formatToString.split("-");
            String currentMonth = spiltMonth[1].replace("0", "");

            AssetsStock saveStocks = AssetsStock.builder()
                    .centerName(centerName)
                    .qtyUnit(assetsList.getUnit())
                    .itemCode(assetsList.getItemCode())
                    .itemName(assetsList.getItemName())
                    .qty(assetsList.getQuantity())
                    .date(timestamp)
                    .month(currentMonth)
                    .build();

            assetsStockRepository.save(saveStocks);


            StockItemsDTO addSingleItem = StockItemsDTO.builder()
                    .centerName(centerName)
                    .unit(assetsList.getUnit())
                    .itemCode(assetsList.getItemCode())
                    .itemName(assetsList.getItemName())
                    .quantity(assetsList.getQuantity())
                    .date(formatToString)
                    .build();

            addInList.add(addSingleItem);
        }
        return addInList;
    }

    private String sumOfItemsByDate(String centerName, String itemCode, String selectedMonth) {
        String itemSum = "";
        List<AssetsStock> findCenterItems = assetsStockRepository.findAllByCenterNameAndItemCodeAndMonth(centerName, itemCode, selectedMonth);
        float itemInFloat = 0L;
        if (findCenterItems.size() > 0) {

            for (AssetsStock fs : findCenterItems) {
                itemInFloat = itemInFloat + Float.parseFloat(fs.getQty());

            }

        }

        itemSum = String.valueOf(itemInFloat);

        return itemSum;
    }

    private List<StockOutputArray> getStockArray(String centerName, String selectedMonth) {
        HashSet<String> uniqueCode = new HashSet<>();
        List<StockOutputArray> addInArrayList = new ArrayList<>();
        List<AssetsStock> findDetails = assetsStockRepository.findAllByCenterNameAndMonthOrderByDateDesc(centerName, selectedMonth);

        for (AssetsStock findItemsWise : findDetails) {
            long getMills = findItemsWise.getDate();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(getMills);

            if (uniqueCode.add(findItemsWise.getItemCode())) {
                StockOutputArray singleEntry = StockOutputArray.builder()
                        .centerName(findItemsWise.getCenterName())
                        .itemCode(findItemsWise.getItemCode())
                        .itemName(findItemsWise.getItemName())
                        .quantity(sumOfItemsByDate(findItemsWise.getCenterName(), findItemsWise.getItemCode(), selectedMonth))
                        .unit(findItemsWise.getQtyUnit())
                        .build();
                addInArrayList.add(singleEntry);
            }

        }

        return addInArrayList;
    }


    @Override
    public StockOutputItemsDTO getStocks(String centerName, String selectedMonth) {

        List<AssetsStock> findByCenterName = assetsStockRepository.findAllByCenterNameAndMonthOrderByCreatedDateAsc(centerName, selectedMonth);
        HashSet<String> captureMonth = new HashSet<>();
        List<StockOutputItemsDTO> addInList = new ArrayList<>();
        StockOutputItemsDTO addSingle = new StockOutputItemsDTO();
        if (findByCenterName.size() > 0) {
            for (AssetsStock findMonth : findByCenterName) {
                long getMills = findMonth.getDate();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date(getMills);
                if (captureMonth.add(df.format(date))) {
                    log.info("date " + df.format(date));
                    addSingle = StockOutputItemsDTO.builder()
                            .date(df.format(date))
                            .stockArrayList(getStockArray(centerName, selectedMonth))
                            .build();
                }

            }
        } else {
            addSingle = StockOutputItemsDTO.builder()
                    .date("")
                    .stockArrayList(Collections.EMPTY_LIST)
                    .build();
        }

        return addSingle;
    }

    @Override
    public List<StockListDTO> getStocksLists() {

        List<StockList> stockLists = stockListRepository.findAll();
        List<StockListDTO> addInList = new ArrayList<>();
        for (StockList loopStocks : stockLists) {
            StockListDTO addSingle = modelMapper.map(loopStocks, StockListDTO.class);
            addInList.add(addSingle);

        }
        return addInList;

    }

    @Override
    public List<StockDistributionDTO> saveDistributionList(List<StockDistributionDTO> stockDistributionDTOS, String centerName) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        long mills = date.getTime();
        List<StockDistributionDTO> addInList = new ArrayList<>();
        String[] spiltMonth = df.format(date).split("-");
        String currentMonth = spiltMonth[1].replace("0", "");

        for (StockDistributionDTO stockList : stockDistributionDTOS) {

            StockDistribution saveEntry = StockDistribution.builder()
                    .centerName(centerName)
                    .date(mills)
                    .month(currentMonth)
                    .familyId(stockList.getFamilyId())
                    .itemCode(stockList.getItemCode())
                    .itemName(stockList.getItemName())
                    .quantity(stockList.getQuantity())
                    .unit(stockList.getUnit())
                    .build();

            stockDistributionRepository.save(saveEntry);

            StockDistributionDTO singleEntry = StockDistributionDTO.builder()
                    .centerName(centerName)
                    .date(df.format(date))
                    .familyId(stockList.getFamilyId())
                    .itemCode(stockList.getItemCode())
                    .itemName(stockList.getItemName())
                    .quantity(stockList.getQuantity())
                    .unit(stockList.getUnit())
                    .build();


            addInList.add(singleEntry);

        }

        return addInList;
    }

    private String getDistributionQty(String familyId, String itemCode, String selectedMonth) {

        List<StockDistribution> findItemsQty = stockDistributionRepository.findAllByFamilyIdAndItemCodeAndMonth(familyId, itemCode, selectedMonth, Sort.by(Sort.Direction.ASC, "itemCode"));
        Float sum = 0F;
        String finalQty = "";
        for (StockDistribution findQty : findItemsQty) {

            sum = sum + Float.parseFloat(findQty.getQuantity());
        }
        finalQty = String.valueOf(sum);
        return finalQty;
    }


    private List<DistributionArrayList> getItemArray(String familyId, String selectedMonth) {

        HashSet<String> uniqueItem = new HashSet<>();
        List<StockDistribution> findItems = stockDistributionRepository.findAllByFamilyIdAndMonth(familyId, selectedMonth);
        List<DistributionArrayList> itemList = new ArrayList<>();

        for (StockDistribution items : findItems) {

            if (uniqueItem.add(items.getItemCode())) {

                DistributionArrayList singeList = DistributionArrayList.builder()
                        .itemName(items.getItemName())
                        .itemCode(items.getItemCode())
                        .unit(items.getUnit())
                        .quantity(getDistributionQty(items.getFamilyId(), items.getItemCode(), selectedMonth))
                        .build();

                itemList.add(singeList);
            }
        }

        return itemList;
    }


    @Override
    public List<DistributionOutputList> getDistributionList(String centerName, String selectedMonth) {

        List<StockDistribution> findFamily = stockDistributionRepository.findAllByCenterNameAndMonth(centerName, selectedMonth);
        List<DistributionOutputList> addInList = new ArrayList<>();
        HashSet<String> uniqueFamily = new HashSet<>();


        for (StockDistribution sd : findFamily) {
            String name = "", profilePic = "", houseNo = "", familyId = "";

            List<Family> findHouseholds = familyRepository.findAllByFamilyId(sd.getFamilyId());

            for (Family fy : findHouseholds) {
                houseNo = fy.getHouseNo();
                familyId = fy.getFamilyId();
            }

            List<FamilyMember> findHeadDetails = familyMemberRepository.findAllByFamilyId(sd.getFamilyId(), Sort.by(Sort.Direction.DESC, "createdDate"));

            for (FamilyMember fm : findHeadDetails) {
                if (fm.getRelationWithOwner().trim().equals("0")) {
                    name = fm.getName();
                    profilePic = fm.getPhoto();
                    break;
                } else {
                    name = fm.getName();
                    profilePic = fm.getPhoto();
                }
            }


            if (uniqueFamily.add(sd.getFamilyId())) {

                long getMills = sd.getDate();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date(getMills);


                DistributionOutputList singleEntry = DistributionOutputList.builder()
                        .name(name)
                        .familyId(familyId)
                        .profilePic(profilePic)
                        .houseNo(houseNo)
                        .date(df.format(date))
                        .arrayLists(getItemArray(sd.getFamilyId(), selectedMonth))
                        .build();
                addInList.add(singleEntry);
            }
        }


        return addInList;
    }

    @Override
    public List<AnganwadiAahaarData> getAnganwadiAahaarData(String month) {
        String selectedMonth = month == null ? "" : month;
        List<Meals> findMeals = mealsRepository.findAllBYMonth(selectedMonth, Sort.by(Sort.Direction.DESC, "date"));
        List<AnganwadiAahaarData> dataList = new ArrayList<>();

        for (Meals meals : findMeals) {

            long getMills = meals.getDate();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(getMills);


            AnganwadiAahaarData singleMeals = AnganwadiAahaarData.builder()
                    .foodName(meals.getFoodName())
                    .quantity(meals.getQuantity())
                    .quantityUnit(meals.getQuantityUnit())
                    .mealType(meals.getMealType())
                    .date(df.format(date))
                    .build();
            dataList.add(singleMeals);

        }

        return dataList;
    }

    @Override
    public List<WeightTrackingDTO> getChildrenWeightData(String month) {

        String selectedMonth = month == null ? "" : month;

        List<WeightTracking> findChildren = weightTrackingRepository.findAllByMonth(month);
        List<WeightTrackingDTO> addInList = new ArrayList<>();

        for (WeightTracking tracking : findChildren) {

            long getMills = tracking.getDate();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(getMills);


            WeightTrackingDTO addSingle = WeightTrackingDTO.builder()
                    .familyId(tracking.getFamilyId())
                    .childId(tracking.getChildId())
                    .date(df.format(date))
                    .height(tracking.getHeight())
                    .weight(tracking.getWeight())
                    .build();
            addInList.add(addSingle);
        }

        return addInList;
    }

    @Override
    public List<DashboardAttendanceDTO> getAttendanceData(String month) throws ParseException {

        long startDayMillis = 0, lastDayMillis = 0;
        List<DashboardAttendanceDTO> addInList = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Date startDate = df.parse(month);
        startDayMillis = startDate.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(df.parse(month));

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        Date lastDay = calendar.getTime();
        lastDayMillis = lastDay.getTime();

        List<Attendance> findAllList = attendanceRepository.findAllByDateRange(startDayMillis, lastDayMillis);

        for (Attendance attend : findAllList) {
            Date attendanceDate = new Date(attend.getDate());
            DashboardAttendanceDTO singleEntry = DashboardAttendanceDTO.builder()
                    .centerName(attend.getCenterName())
                    .childId(attend.getChildId())
                    .date(df.format(attendanceDate))
                    .attendance(attend.getAttendance())
                    .build();

            addInList.add(singleEntry);
        }

        return addInList;

    }

    @Override
    public AnganwadiChildrenDTO getAnganwadiChildrenData(String month) throws ParseException {

        long startDayMillis = 0, lastDayMillis = 0;
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        long gen = 0, obc = 0, sc = 0, st = 0, minority = 0;

        Date startDate = df.parse(month);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(df.parse(month));

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        Date lastDay = calendar.getTime();

        List<AnganwadiChildren> findChildren = anganwadiChildrenRepository.findAllByCreatedDate(startDate, lastDay);

        for (AnganwadiChildren childrenList : findChildren) {

            switch (childrenList.getCategory().trim()) {
                case "1":
                    gen++;
                    break;

                case "2":
                    obc++;
                    break;

                case "3":
                    sc++;
                    break;

                case "4":
                    st++;
                    break;
            }


            if (childrenList.getMinority().trim().equals("1")) {
                minority++;
            }
        }


        return AnganwadiChildrenDTO.builder()
                .gen(gen)
                .obc(obc)
                .sc(sc)
                .st(st)
                .minority(minority)
                .month(month)
                .build();
    }

    @Override
    public List<AnganwadiChildrenList> getAnganwadiChildrenDetails(String startDate, String endDate) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");


        // List<AnganwadiChildren> childrenList = anganwadiChildrenRepository.findAllByCreatedDate();

        return null;
    }


    @Override
    public List<AttendanceDTO> makeAttendance(AttendanceDTO attendanceDTO) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date currentTime = new Date();
        String formatToString = df.format(currentTime.getTime());
        Date formatToTime = df.parse(formatToString);
        long timestamp = formatToTime.getTime();

        String[] spiltComma = attendanceDTO.getChildId().trim().split(",");
        List<AttendanceDTO> addList = new ArrayList<>();
        String attendance = "A";
        long currentDate = 0L;

        List<AnganwadiChildren> findChildren = anganwadiChildrenRepository.findAll();
        String ids = "", angwandiId = "";
        for (AnganwadiChildren getId : findChildren) {
            angwandiId = getId.getId();

            Attendance saveAttendance = Attendance.builder()
                    .childId(getId.getChildId())
                    .dob(getId.getDob())
                    .name(getId.getName())
                    .latitude(attendanceDTO.getLatitude())
                    .longitude(attendanceDTO.getLongitude())
                    .photo(getId.getProfilePic())
                    .gender(getId.getGender())
                    .date(timestamp)
                    .attendance(attendance)
                    .build();
            attendanceRepository.save(saveAttendance);

            currentDate = saveAttendance.getDate();
            ids = saveAttendance.getId();
        }

            for (String attend : spiltComma) {

                List<AnganwadiChildren> checkChild = anganwadiChildrenRepository.findAllByChildId(attend.trim());
                try {
                    if (checkChild.size() > 0) {
                        for (AnganwadiChildren children : checkChild) {
                            List<Attendance> updateAttendance = attendanceRepository.findAllByChildId(children.getChildId());

                            for (Attendance updateAtt : updateAttendance) {
                                updateAtt.setAttendance("P");
                                attendanceRepository.save(updateAtt);
                                log.info("Date " + formatToTime);
                            }
                        }
                    }
                } catch (NoSuchElementException | NullPointerException e) {
                    log.info("Id Not Found");
                }


            }

        List<Attendance> getDetails = attendanceRepository.findAllByDate(currentDate, Sort.by(Sort.Direction.DESC, "createdDate"));

        for (Attendance fetchDetails : getDetails) {

            AttendanceDTO singleEntry = AttendanceDTO
                    .builder()
                    .childId(fetchDetails.getChildId())
                    .dob(fetchDetails.getDob())
                    .name(fetchDetails.getName())
                    .latitude(attendanceDTO.getLatitude())
                    .longitude(attendanceDTO.getLongitude())
                    .photo(fetchDetails.getPhoto())
                    .gender(fetchDetails.getGender())
                    .date(currentDate)
                    .attendance(fetchDetails.getAttendance())
                    .build();

            addList.add(singleEntry);
        }
        return addList;


    }


}
