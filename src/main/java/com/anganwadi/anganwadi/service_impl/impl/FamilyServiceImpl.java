package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.domains.entity.Family;
import com.anganwadi.anganwadi.domains.entity.FamilyMember;
import com.anganwadi.anganwadi.domains.entity.Visits;
import com.anganwadi.anganwadi.domains.entity.WeightTracking;
import com.anganwadi.anganwadi.exceptionHandler.CustomException;
import com.anganwadi.anganwadi.repositories.FamilyMemberRepository;
import com.anganwadi.anganwadi.repositories.FamilyRepository;
import com.anganwadi.anganwadi.repositories.VisitsRepository;
import com.anganwadi.anganwadi.repositories.WeightTrackingRepository;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository familyRepository;
    private final ModelMapper modelMapper;
    private final FamilyMemberRepository familyMemberRepository;
    private final VisitsRepository visitsRepository;
    private final WeightTrackingRepository weightTrackingRepository;


    @Autowired
    public FamilyServiceImpl(FamilyRepository familyRepository, ModelMapper modelMapper, FamilyMemberRepository familyMemberRepository,
                             VisitsRepository visitsRepository, WeightTrackingRepository weightTrackingRepository) {
        this.familyRepository = familyRepository;
        this.modelMapper = modelMapper;
        this.familyMemberRepository = familyMemberRepository;
        this.visitsRepository = visitsRepository;
        this.weightTrackingRepository = weightTrackingRepository;
    }


    @Override
    public List<householdsHeadList> getAllHouseholds() {
        List<householdsHeadList> addInList = new ArrayList<>();
        List<Family> familyList = familyRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));

        for (Family getHouseholds : familyList) {
            householdsHeadList householdsDTO = householdsHeadList.builder()
                    .headName(getHouseholds.getHeadName())
                    .headDob(getHouseholds.getHeadDob())
                    .religion(getHouseholds.getReligion())
                    .headGender(getHouseholds.getHeadGender())
                    .category(getHouseholds.getCategory())
                    .totalMembers(getHouseholds.getTotalMembers())
                    .headPic(getHouseholds.getHeadPic())
                    .build();
            addInList.add(householdsDTO);
        }

        return addInList;
    }

    @Override
    public HouseholdsDTO saveHouseholds(HouseholdsDTO householdsDTO) {

        String name = householdsDTO.getHeadName() == null ? "" : householdsDTO.getHeadName();
        String headDob = householdsDTO.getHeadDob() == null ? "" : householdsDTO.getHeadDob();
        String totalMembers = householdsDTO.getTotalMembers() == null ? "" : householdsDTO.getTotalMembers();
        String houseNo = householdsDTO.getHouseNo() == null ? "" : householdsDTO.getHouseNo();
        String mobileNo = householdsDTO.getMobileNumber() == null ? "" : householdsDTO.getMobileNumber();
        String headPic = householdsDTO.getHeadPic() == null ? "" : householdsDTO.getHeadPic();
        String uniqueIdType = householdsDTO.getUniqueIdType() == null ? "" : householdsDTO.getUniqueIdType();
        String uniqueId = householdsDTO.getUniqueId() == null ? "" : householdsDTO.getUniqueId();
        String category = householdsDTO.getCategory() == null ? "" : householdsDTO.getCategory();
        String religion = householdsDTO.getReligion() == null ? "" : householdsDTO.getReligion();
        String isMinority = householdsDTO.getIsMinority() == null ? "" : householdsDTO.getIsMinority();
        String icdsService = householdsDTO.getIcdsService() == null ? "" : householdsDTO.getIcdsService();
        String headGender = householdsDTO.getHeadGender() == null ? "" : householdsDTO.getHeadGender();

        Family saveFamily = Family.builder()
                .headName(name)
                .headDob(headDob)
                .totalMembers(totalMembers)
                .headPic(headPic)
                .houseNo(houseNo)
                .headGender(headGender)
                .mobileNumber(mobileNo)
                .uniqueIdType(uniqueIdType)
                .uniqueId(uniqueId)
                .category(category)
                .religion(religion)
                .isMinority(isMinority)
                .icdsService(icdsService)
                .build();
        familyRepository.save(saveFamily);

        return modelMapper.map(saveFamily, HouseholdsDTO.class);
    }

    @Override
    public FamilyMemberDTO saveFamilyMembers(FamilyMemberDTO familyMemberDTO) {

        String name = familyMemberDTO.getName() == null ? "" : familyMemberDTO.getName();
        String relationShip = familyMemberDTO.getRelationWithOwner() == null ? "" : familyMemberDTO.getRelationWithOwner();
        String gender = familyMemberDTO.getGender() == null ? "" : familyMemberDTO.getGender();
        String dob = familyMemberDTO.getDob() == null ? "" : familyMemberDTO.getDob();
        String martialStatus = familyMemberDTO.getMaritalStatus() == null ? "" : familyMemberDTO.getMaritalStatus();
        String stateCode = familyMemberDTO.getStateCode() == null ? "" : familyMemberDTO.getStateCode();
        String handicapType = familyMemberDTO.getHandicapType() == null ? "" : familyMemberDTO.getHandicapType();
        String area = familyMemberDTO.getResidentArea() == null ? "" : familyMemberDTO.getResidentArea();
        String arrivalDate = familyMemberDTO.getDateOfArrival() == null ? "" : familyMemberDTO.getDateOfArrival();
        String leavingDate = familyMemberDTO.getDateOfLeaving() == null ? "" : familyMemberDTO.getDateOfLeaving();
        String deathDate = familyMemberDTO.getDateOfMortality() == null ? "" : familyMemberDTO.getDateOfMortality();
        String photo = familyMemberDTO.getPhoto() == null ? "" : familyMemberDTO.getPhoto();

        FamilyMember saveMember = FamilyMember.builder()
                .name(name)
                .relationWithOwner(relationShip)
                .gender(gender)
                .familyId(familyMemberDTO.getFamilyId())
                .mobileNumber(familyMemberDTO.getMobileNumber() == null ? "" : familyMemberDTO.getMobileNumber())
                .idType(familyMemberDTO.getIdType() == null ? "" : familyMemberDTO.getIdType())
                .idNumber(familyMemberDTO.getIdNumber() == null ? "" : familyMemberDTO.getIdNumber())
                .dob(dob)
                .maritalStatus(martialStatus)
                .stateCode(stateCode)
                .handicapType(handicapType)
                .residentArea(area)
                .dateOfArrival(arrivalDate)
                .dateOfLeaving(leavingDate)
                .dateOfMortality(deathDate)
                .photo(photo)
                .build();

        familyMemberRepository.save(saveMember);
        return modelMapper.map(saveMember, FamilyMemberDTO.class);
    }

    @Override
    public List<FamilyMemberDTO> getFamilyMembers(String familyId) {

        if (StringUtils.isEmpty(familyId.trim())) {
            throw new CustomException("Family Id Is Missed, Please Check!!");
        }
        List<FamilyMemberDTO> addInList = new ArrayList<>();
        List<FamilyMember> getMembers = familyMemberRepository.findAllByFamilyId(familyId, Sort.by(Sort.Direction.DESC, "createdDate"));

        for (FamilyMember passDetails : getMembers) {
            FamilyMemberDTO singleList = modelMapper.map(passDetails, FamilyMemberDTO.class);
            addInList.add(singleList);
        }

        return addInList;
    }

    @Override
    public VisitsDetailsDTO saveVisitsDetails(VisitsDetailsDTO visitsDetailsDTO) {

        Visits saveVisitDetails = Visits.builder()
                .familyId(visitsDetailsDTO.getFamilyId() == null ? "" : visitsDetailsDTO.getFamilyId())
                .visitType(visitsDetailsDTO.getVisitType() == null ? "" : visitsDetailsDTO.getVisitType())
                .visitFor(visitsDetailsDTO.getVisitFor() == null ? "" : visitsDetailsDTO.getVisitFor())
                .description(visitsDetailsDTO.getDescription() == null ? "" : visitsDetailsDTO.getDescription())
                .visitRound(visitsDetailsDTO.getVisitRound() == null ? "" : visitsDetailsDTO.getVisitRound())
                .latitude(visitsDetailsDTO.getLatitude() == null ? "" : visitsDetailsDTO.getLatitude())
                .longitude(visitsDetailsDTO.getLongitude() == null ? "" : visitsDetailsDTO.getLongitude())
                .visitDateTime(visitsDetailsDTO.getVisitDateTime() == null ? "" : visitsDetailsDTO.getVisitDateTime())
                .build();
        visitsRepository.save(saveVisitDetails);

        return modelMapper.map(saveVisitDetails, VisitsDetailsDTO.class);
    }

    @Override
    public WeightRecordsDTO saveWeightRecords(WeightRecordsDTO weightRecordsDTO) {

        WeightTracking saveRecord = WeightTracking.builder()
                .familyId(weightRecordsDTO.getFamilyId() == null ? "" : weightRecordsDTO.getFamilyId())
                .childId(weightRecordsDTO.getChildId() == null ? "" : weightRecordsDTO.getChildId())
                .date(weightRecordsDTO.getDate() == null ? "" : weightRecordsDTO.getDate())
                .weight(weightRecordsDTO.getWeight() == null ? "" : weightRecordsDTO.getWeight())
                .height(weightRecordsDTO.getHeight() == null ? "" : weightRecordsDTO.getHeight())
                .build();

        weightTrackingRepository.save(saveRecord);
        return modelMapper.map(saveRecord, WeightRecordsDTO.class);
    }

    @Override
    public List<WeightRecordsDTO> getWeightRecords(String familyId, String childId) {

        if (StringUtils.isEmpty(familyId.trim())) {
            throw new CustomException("Family Id Is Missed, Please Check!!");
        }

        if (StringUtils.isEmpty(childId.trim())) {
            throw new CustomException("Child Id Is Missed, Please Check!!");
        }

        List<WeightTracking> findChildRecords = weightTrackingRepository.findAllByFamilyIdAndChildId(familyId, childId, Sort.by(Sort.Direction.ASC, "createdDate"));
        List<WeightRecordsDTO> addList = new ArrayList<>();

        for(WeightTracking passRecords : findChildRecords){
            WeightRecordsDTO getRecords =  modelMapper.map(passRecords, WeightRecordsDTO.class);
            addList.add(getRecords);
        }
        return addList;
    }

    @Override
    public List<WeightRecordsDTO> getAllChildWeightRecords(String familyId) {

        if (StringUtils.isEmpty(familyId.trim())) {
            throw new CustomException("Family Id Is Missed, Please Check!!");
        }
        List<WeightTracking> findAllChildRecords = weightTrackingRepository.findAllByFamilyId(familyId,Sort.by(Sort.Direction.ASC, "createdDate"));

        List<WeightRecordsDTO> addList = new ArrayList<>();

        return null;
    }

    @Override
    public List<MPRDTO> getMPRRecords(String familyId) {

        
        return null;
    }

}
