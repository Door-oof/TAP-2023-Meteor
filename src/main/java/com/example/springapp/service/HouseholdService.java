package com.example.springapp.service;

import com.example.springapp.config.Mapper;
import com.example.springapp.dto.FamilyMemberCreationDTO;
import com.example.springapp.dto.HouseholdCreationDTO;
import com.example.springapp.dto.HouseholdDTO;
import com.example.springapp.entity.FamilyMember;
import com.example.springapp.entity.Household;
import com.example.springapp.error.AnnualIncomeLowerThanZeroException;
import com.example.springapp.error.ConflictIdException;
import com.example.springapp.error.FutureDateException;
import com.example.springapp.error.HouseholdNotFoundException;
import com.example.springapp.repository.HouseholdRepository;
import com.example.springapp.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseholdService {
    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Autowired
    private Mapper mapper;

    public List<HouseholdDTO> getAllHouseholds() {
        List<Household> allHouseholds = householdRepository.findAll();
        return allHouseholds.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public HouseholdDTO getSpecifiedHousehold(Integer id) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new HouseholdNotFoundException(id));
        return mapper.toDto(household);
    }

    public HouseholdCreationDTO addHousehold(Household household) {
        if (householdRepository.existsById(household.getId())) {
            throw new ConflictIdException(household.getId(), "Household");
        }
        Household newHousehold = createHousehold(household);
        householdRepository.save(newHousehold);
        return new HouseholdCreationDTO(newHousehold.getId(), household.getHousingType());
    }

    private Household createHousehold(Household household) {
        Household newHousehold = new Household();
        newHousehold.setHousingType(household.getHousingType());
        return household;
    }

    public FamilyMemberCreationDTO addFamilyMember(FamilyMember familyMember) {
        Household household = householdRepository.findById(familyMember.getFamilyId())
                .orElseThrow(() -> new HouseholdNotFoundException(familyMember.getFamilyId()));
        if (familyMemberRepository.existsById(familyMember.getId())) {
            throw new ConflictIdException(household.getId(), "Family member");
        }
        if (familyMember.getAnnualIncome().compareTo(BigDecimal.ZERO) < 0) {
            throw new AnnualIncomeLowerThanZeroException();
        }
        LocalDate dob = familyMember.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (dob.isAfter(LocalDate.now())) {
            throw new FutureDateException(dob);
        }
        FamilyMember newFamilyMember = createFamilyMember(familyMember, household);
        familyMemberRepository.save(newFamilyMember);
        return new FamilyMemberCreationDTO(newFamilyMember);
    }

    private FamilyMember createFamilyMember(FamilyMember familyMember, Household household) {
        FamilyMember newFamilyMember = new FamilyMember();
        newFamilyMember.setFamilyId(household.getId());
        newFamilyMember.setOccupationType(familyMember.getOccupationType());
        newFamilyMember.setName(familyMember.getName());
        newFamilyMember.setGender(familyMember.getGender());
        newFamilyMember.setMaritalStatus(familyMember.getMaritalStatus());
        newFamilyMember.setSpouse(familyMember.getSpouse());
        newFamilyMember.setDob(familyMember.getDob());
        newFamilyMember.setAnnualIncome(familyMember.getAnnualIncome());
        return newFamilyMember;
    }
}
