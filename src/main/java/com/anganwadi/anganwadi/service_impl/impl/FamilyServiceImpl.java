package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.domains.dto.HouseholdsDTO;
import com.anganwadi.anganwadi.domains.entity.Family;
import com.anganwadi.anganwadi.repositories.FamilyRepository;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    public FamilyServiceImpl(FamilyRepository familyRepository, ModelMapper modelMapper) {
        this.familyRepository = familyRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<HouseholdsDTO> getAllHouseholds() {
        List<HouseholdsDTO> addInList = new ArrayList<>();
        List<Family> childrenList = familyRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));

        for (Family getHouseholds : childrenList) {
            HouseholdsDTO householdsDTO = HouseholdsDTO.builder()
                    .dob(getHouseholds.getName())
                    .category(getHouseholds.getCategory())
                    .members(getHouseholds.getTotalMembers())
                    .headPic(getHouseholds.getHeadPic())
                    .build();
            addInList.add(householdsDTO);
        }

        return addInList;
    }

    @Override
    public HouseholdsDTO saveHouseholds(HouseholdsDTO householdsDTO) {

        String name = householdsDTO.getName() == null ? "" : householdsDTO.getName();
        String houseNo = householdsDTO.getHouseNo() == null ? "" : householdsDTO.getHouseNo();
        String mobileNo = householdsDTO.getMobileNumber() == null ? "" : householdsDTO.getMobileNumber();
        String uniqueIdType = householdsDTO.getUniqueIdType() == null ? "" : householdsDTO.getUniqueIdType();
        String uniqueId = householdsDTO.getUniqueId() == null ? "" : householdsDTO.getUniqueId();
        String category = householdsDTO.getCategory() == null ? "" : householdsDTO.getCategory();
        String religion = householdsDTO.getReligion() == null ? "" : householdsDTO.getReligion();
        String isMinority = householdsDTO.getIsMinority() == null ? "" : householdsDTO.getIsMinority();
        String icdsService = householdsDTO.getIcdsService() == null ? "" : householdsDTO.getIcdsService();

        Family saveFamily = Family.builder()
                .name(name)
                .houseNo(houseNo)
                .mobileNumber(mobileNo)
                .uniqueIdType(uniqueIdType)
                .uniqueId(uniqueId)
                .category(category)
                .religion(religion)
                .isMinority(isMinority)
                .icdsService(icdsService)
                .build();
        familyRepository.save(saveFamily);

        HouseholdsDTO getHeadDetails = modelMapper.map(saveFamily, HouseholdsDTO.class);
        return getHeadDetails;
    }

}
