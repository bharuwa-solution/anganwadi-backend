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

    @Autowired
    public AnganwadiChildrenServiceImpl(AnganwadiChildrenRepository anganwadiChildrenRepository, FileManagementService fileManagementService,
                                        AttendanceRepository attendanceRepository, ModelMapper modelMapper,
                                        FamilyRepository familyRepository, FamilyMemberRepository familyMemberRepository,
                                        FamilyServiceImpl familyServiceImpl, AssetsStockRepository assetsStockRepository,
                                        StockListRepository stockListRepository, StockDistributionRepository stockDistributionRepository) {
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

        markAsAbsent(centerName);

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

            AssetsStock saveStocks = AssetsStock.builder()
                    .centerName(centerName)
                    .qtyUnit(assetsList.getUnit())
                    .itemCode(assetsList.getItemCode())
                    .itemName(assetsList.getItemName())
                    .qty(assetsList.getQuantity())
                    .date(timestamp)
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

    private String sumOfItemsByDate(String centerName, String itemCode, long Millis) {
        String itemSum = "";
        List<AssetsStock> findCenterItems = assetsStockRepository.findAllByCenterNameAndItemCodeAndDate(centerName, itemCode, Millis);
        float itemInFloat = 0L;
        if (findCenterItems.size() > 0) {

            for (AssetsStock fs : findCenterItems) {

                itemInFloat = itemInFloat + Float.parseFloat(fs.getQty());

            }

        }

        itemSum = String.valueOf(itemInFloat);

        return itemSum;
    }

    private List<StockOutputArray> getStockArray(String centerName, long millis) {
        HashSet<String> uniqueCode = new HashSet<>();
        List<StockOutputArray> addInArrayList = new ArrayList<>();
        List<AssetsStock> findDetails = assetsStockRepository.findAllByCenterNameAndDateOrderByDateDesc(centerName, millis);

        for (AssetsStock findItemsWise : findDetails) {
            if (uniqueCode.add(findItemsWise.getItemCode())) {
                StockOutputArray singleEntry = StockOutputArray.builder()
                        .centerName(findItemsWise.getCenterName())
                        .itemCode(findItemsWise.getItemCode())
                        .itemName(findItemsWise.getItemName())
                        .quantity(sumOfItemsByDate(findItemsWise.getCenterName(), findItemsWise.getItemCode(), millis))
                        .unit(findItemsWise.getQtyUnit())
                        .build();
                addInArrayList.add(singleEntry);
            }
        }

        return addInArrayList;
    }


    @Override
    public List<StockOutputItemsDTO> getStocks(String centerName) {

        List<AssetsStock> findByCenterName = assetsStockRepository.findAllByCenterNameOrderByCreatedDateDesc(centerName);
        HashSet<String> captureMonth = new HashSet<>();
        List<StockOutputItemsDTO> addInList = new ArrayList<>();

        for (AssetsStock findMonth : findByCenterName) {
            long getMills = findMonth.getDate();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(getMills);

            if (captureMonth.add(df.format(date))) {
                StockOutputItemsDTO addSingle = StockOutputItemsDTO.builder()
                        .date(df.format(date))
                        .stockArrayList(getStockArray(centerName, getMills))
                        .build();

                addInList.add(addSingle);
            }
        }


        return addInList;
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

        for (StockDistributionDTO stockList : stockDistributionDTOS) {

            StockDistribution saveEntry = StockDistribution.builder()
                    .centerName(centerName)
                    .date(mills)
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

    private String getDistributionQty(String familyId, String itemCode) {

        List<StockDistribution> findItemsQty = stockDistributionRepository.findAllByFamilyIdAndItemCode(familyId, itemCode,Sort.by(Sort.Direction.ASC, "itemCode"));
        Float sum = 0F;
        String finalQty = "";
        for (StockDistribution findQty : findItemsQty) {

            sum = sum + Float.parseFloat(findQty.getQuantity());
        }
        finalQty = String.valueOf(sum);
        return finalQty;
    }


    private List<DistributionArrayList> getItemArray(String familyId) {

        HashSet<String> uniqueItem = new HashSet<>();
        List<StockDistribution> findItems = stockDistributionRepository.findAllByFamilyId(familyId, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<DistributionArrayList> itemList = new ArrayList<>();

        for (StockDistribution items : findItems) {

            if (uniqueItem.add(items.getItemCode())) {

                DistributionArrayList singeList = DistributionArrayList.builder()
                        .itemName(items.getItemName())
                        .itemCode(items.getItemCode())
                        .unit(items.getUnit())
                        .quantity(getDistributionQty(items.getFamilyId(), items.getItemCode()))
                        .build();

                itemList.add(singeList);
            }


        }

        return itemList;
    }


    @Override
    public List<DistributionOutputList> getDistributionList(String centerName) {

        List<StockDistribution> findFamily = stockDistributionRepository.findAllByCenterName(centerName);
        List<DistributionOutputList> addInList = new ArrayList<>();
        HashSet<String> uniqueFamily = new HashSet<>();

        for (StockDistribution sd : findFamily) {
            String name = "", profilePic = "", houseNo = "";

            List<Family> findHouseholds = familyRepository.findAllByFamilyId(sd.getFamilyId());

            for (Family fy : findHouseholds) {
                houseNo = fy.getHouseNo();
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
                        .profilePic(profilePic)
                        .houseNo(houseNo)
                        .date(df.format(date))
                        .arrayLists(getItemArray(sd.getFamilyId()))
                        .build();
                addInList.add(singleEntry);
            }


        }


        return addInList;
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
