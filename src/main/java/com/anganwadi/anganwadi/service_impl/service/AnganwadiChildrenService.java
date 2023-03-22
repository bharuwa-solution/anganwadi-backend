package com.anganwadi.anganwadi.service_impl.service;

import com.anganwadi.anganwadi.domains.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface AnganwadiChildrenService {


    SaveAdmissionDTO saveChildrenRecord(SaveAdmissionDTO saveAdmissionDTO,String centerName) throws ParseException, IOException;

    List<ChildrenDTO> getTotalChildren(String centerName);

    UploadDTO uploadPic(MultipartFile file) throws IOException;

//    DashboardDetails getDashboardDetails();

    List<AttendanceDTO> getAttendanceByDate(String date,String centerName) throws ParseException;

    List<AttendanceDTO> makeAttendance(AttendanceDTO attendanceDTO) throws ParseException;

    List<AttendanceDTO> makeAndUpdateAttendance(AttendanceDTO attendanceDTO, String centerName) throws ParseException;

    List<householdsHeadList> getRegisteredHouseholdsList(String centerName);

    List<StockListDTO> getAvailableItems();

    List<StockItemsDTO> addStocks(List<StockItemsDTO> assetsStock, String centerName) throws ParseException;

    List<StockOutputItemsDTO> getStocks(String centerName);

    List<StockListDTO> getStocksLists();

    List<StockDistributionDTO> saveDistributionList(List<StockDistributionDTO> stockDistributionDTOS, String centerName) throws ParseException;

    List<DistributionOutputList> getDistributionList(String centerName);
}
