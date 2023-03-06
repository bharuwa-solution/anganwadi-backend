package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.config.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@Slf4j
public class FileManagementService implements ApplicationConstants {


    public String uploadPic(MultipartFile file) throws IOException {
        String fileName = "";
        String fileSeparator = ApplicationConstants.fileSeparator;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss a");
        long timeInMillis = new Date().getTime();
        String date = String.valueOf(timeInMillis);
        fileName = date.trim() + "_" + file.getOriginalFilename();
        fileName = fileName.toLowerCase().replaceAll(" ", "_");


        Path path = Paths.get(localUploadPath + fileSeparator + "uploads" + fileSeparator + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build() + fileSeparator + "uploads" + fileSeparator + fileName;
        log.info("File Path " + fileUrl);

        return fileUrl;

    }

}
