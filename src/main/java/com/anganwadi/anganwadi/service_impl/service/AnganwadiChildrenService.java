package com.anganwadi.anganwadi.service_impl.service;

import com.anganwadi.anganwadi.domains.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface AnganwadiChildrenService {

    SaveAdmissionDTO saveChildrenRecord(SaveAdmissionDTO saveAdmissionDTO, String centerId) throws ParseException, IOException;

    List<ChildrenDTO> getTotalChildren(String centerName) throws ParseException;

    UploadDTO uploadPic(MultipartFile file) throws IOException;

//    DashboardDetails getDashboardDetails();

    List<AttendanceDTO> getAttendanceByDate(String date, String centerId) throws ParseException;

    List<AttendanceDTO> makeAttendance(AttendanceDTO attendanceDTO) throws ParseException;

    List<AttendanceDTO> makeAndUpdateAttendance(AttendanceDTO attendanceDTO, String centerId) throws ParseException;

    List<householdsHeadList> getRegisteredHouseholdsList(String centerId);

    List<StockListDTO> getAvailableItems();

    List<StockItemsDTO> addStocks(List<StockItemsDTO> assetsStock, String centerId) throws ParseException;

    StockOutputItemsDTO getStocks(String centerId, String selectedMonth) throws ParseException;

//    List<StockListDTO> getStocksLists();

    List<StockDistributionDTO> saveDistributionList(List<StockDistributionDTO> stockDistributionDTOS, String centerId) throws ParseException;

    List<DistributionOutputList> getDistributionList(String centerId, String selectedMonth) throws ParseException;

    List<AnganwadiAahaarData> getAnganwadiAahaarData(DashboardFilter dashboardFilter) throws ParseException;

    List<WeightTrackingDTO> getChildrenWeightData(DashboardFilter dashboardFilter) throws ParseException;

    List<DashboardAttendanceDTO> getAttendanceData(DashboardFilter dashboardFilter) throws ParseException;

//    List<AnganwadiChildrenDTO> getAnganwadiChildrenData(DashboardFilter dashboardFilter) throws ParseException;

    List<AnganwadiChildrenList> getAnganwadiChildrenDetails(DashboardFilter dashboardFilter) throws ParseException;

    SaveAdmissionDTO updateRegisteredValue(String id, boolean isRegistered);

    List<AttendanceDTO> makeAttendanceManual(List<AttendanceDTO> attendanceDTO, String centerId) throws ParseException;

    List<FamilyMemberConverted> convertUnixToDate();

    List<AttendanceConverted> convertAttendanceUnixToDate();

    AttendancePhotoDTO saveAttendancePhoto(AttendancePhotoDTO attendancePhotoDTO);

    List<DashboardMaster> getDashboardMasterDetails() throws ParseException;

    UpdateStudentDTO updateStudentDetails(UpdateStudentDTO updateStudentDTO) throws ParseException;

    UpdateStudentDTO deleteStudentDetails(String id);

    List<PartialStudentList> getStudentListByChildId(PartialStudentList partialStudentList);

    List<RationDistribution> getRationDistributionData(DashboardFilter dashboardFilter) throws ParseException;

    List<SaveActivitiesDTO> saveActivity(AnganwadiActivitiesDTO anganwadiActivitiesDTO, String centerId) throws ParseException;

    List<AnganwadiActivitiesDTO> getAllActivity(AnganwadiActivitiesDTO anganwadiActivitiesDTO, String centerId) throws ParseException;

    MealTypeDTO getMealsItems();

    List<SaveMeals> saveMeals(List<SaveMeals> saveMeals, String centerId) throws ParseException;

    List<MealsResponseDTO> getMonthlyDistributedMeals(DashboardFilter dashboardFilter, String centerId) throws ParseException;


//	List<DistributionOutputList> getDistributionList(String centerId, String selectedMonth, String centerName);
}
