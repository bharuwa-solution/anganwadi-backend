package com.anganwadi.anganwadi.service_impl.service;

import com.anganwadi.anganwadi.domains.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface AnganwadiChildrenService {


    SaveAdmissionDTO saveChildrenRecord(SaveAdmissionDTO saveAdmissionDTO, String centerId, String centerName) throws ParseException, IOException;

    List<ChildrenDTO> getTotalChildren(String centerName);

    UploadDTO uploadPic(MultipartFile file) throws IOException;

//    DashboardDetails getDashboardDetails();

    List<AttendanceDTO> getAttendanceByDate(String date,String centerName) throws ParseException;

    List<AttendanceDTO> makeAttendance(AttendanceDTO attendanceDTO) throws ParseException;

    List<AttendanceDTO> makeAndUpdateAttendance(AttendanceDTO attendanceDTO, String centerName) throws ParseException;

    List<householdsHeadList> getRegisteredHouseholdsList(String centerName);

    List<StockListDTO> getAvailableItems();

    List<StockItemsDTO> addStocks(List<StockItemsDTO> assetsStock, String centerId, String centerName) throws ParseException;

    StockOutputItemsDTO getStocks(String centerName, String selectedMonth);

    List<StockListDTO> getStocksLists();

    List<StockDistributionDTO> saveDistributionList(List<StockDistributionDTO> stockDistributionDTOS, String centerName) throws ParseException;

    List<DistributionOutputList> getDistributionList(String centerName, String selectedMonth);

    List<AnganwadiAahaarData> getAnganwadiAahaarData(String startDate, String endDate) throws ParseException;

    List<WeightTrackingDTO> getChildrenWeightData(String startDate, String endDate) throws ParseException;

    List<DashboardAttendanceDTO> getAttendanceData(String month) throws ParseException;

    AnganwadiChildrenDTO getAnganwadiChildrenData(String month) throws ParseException;

    List<AnganwadiChildrenList> getAnganwadiChildrenDetails(String startDate, String endDate, String search) throws ParseException;

    SaveAdmissionDTO updateRegisteredValue(String id, boolean isRegistered);

    List<AttendanceDTO> makeAttendanceManual(List<AttendanceDTO> attendanceDTO, String centerId) throws ParseException;
}
