package com.example.springapp.service;

import com.example.springapp.config.Mapper;
import com.example.springapp.dto.FamilyMemberCreationDTO;
import com.example.springapp.dto.HouseholdCreationDTO;
import com.example.springapp.dto.HouseholdDTO;
import com.example.springapp.entity.FamilyMember;
import com.example.springapp.entity.Household;
import com.example.springapp.error.AnnualIncomeLowerThanZeroException;
import com.example.springapp.error.ConflictIdException;
import com.example.springapp.error.HouseholdNotFoundException;
import com.example.springapp.repository.HouseholdRepository;
import com.example.springapp.repository.FamilyMemberRepository;
import com.example.springapp.utils.HousingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
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
            throw new ConflictIdException(household.getId());
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
        if (familyMember.getAnnualIncome().compareTo(BigDecimal.ZERO) < 0) {
            throw new AnnualIncomeLowerThanZeroException();
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

    public List<HouseholdDTO> getEligibleStudents() {
        List<Household> allHouseholds = householdRepository.findAll();
        List<HouseholdDTO> selectedHouseholds = new ArrayList<>();
        for (Household household : allHouseholds) {
            List<FamilyMember> familyMemberList = familyMemberRepository.findByFamilyId(household.getId());
            List<FamilyMember> eligibleStudentsList = new ArrayList<>();
            BigDecimal householdIncome = BigDecimal.valueOf(0);
            BigDecimal minIncome = BigDecimal.valueOf(200000);
            for (FamilyMember familyMember : familyMemberList) {
                LocalDate now = LocalDate.now();
                LocalDate dob = familyMember.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                float age = Period.between(dob, now).getYears();
                if (age < 16) {
                    eligibleStudentsList.add(familyMember);
                }
                householdIncome = householdIncome.add(familyMember.getAnnualIncome());
            }
            if (householdIncome.compareTo(minIncome) < 0 && eligibleStudentsList.size() != 0) {
                selectedHouseholds.add(new HouseholdDTO(household.getId(), household.getHousingType(), eligibleStudentsList));
            }
        }
        return selectedHouseholds;
    }

    public List<HouseholdDTO> getEligibleBabies() {
        List<Household> allHouseholds = householdRepository.findAll();
        List<HouseholdDTO> selectedHouseholds = new ArrayList<>();
        for (Household household : allHouseholds) {
            List<FamilyMember> familyMemberList = familyMemberRepository.findByFamilyId(household.getId());
            List<FamilyMember> eligibleBabiesList = new ArrayList<>();
            for (FamilyMember familyMember : familyMemberList) {
                LocalDate now = LocalDate.now();
                LocalDate dob = familyMember.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                float age = Period.between(dob, now).getYears();
                if (age < 0.8) {
                    eligibleBabiesList.add(familyMember);
                }
            }
            if (!eligibleBabiesList.isEmpty()) {
                selectedHouseholds.add(new HouseholdDTO(household.getId(), household.getHousingType(), eligibleBabiesList));
            }
        }
        return selectedHouseholds;
    }

    public List<HouseholdDTO> getMSEligibleHouseholds() {
        List<Household> allHouseholds = householdRepository.findAll();
        List<HouseholdDTO> selectedHouseholds = new ArrayList<>();
        for (Household household : allHouseholds) {
            List<FamilyMember> familyMemberList = familyMemberRepository.findByFamilyId(household.getId());
            boolean areMembersEligible = false;
            BigDecimal householdIncome = BigDecimal.valueOf(0);
            BigDecimal minIncome = BigDecimal.valueOf(150000);
            for (FamilyMember familyMember : familyMemberList) {
                LocalDate now = LocalDate.now();
                LocalDate dob = familyMember.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                float age = Period.between(dob, now).getYears();
                if (age < 18 || age > 55) {
                    areMembersEligible = true;
                }
                householdIncome = householdIncome.add(familyMember.getAnnualIncome());
            }
            if (householdIncome.compareTo(minIncome) < 0 && areMembersEligible) {
                selectedHouseholds.add(new HouseholdDTO(household.getId(), household.getHousingType(), familyMemberList));
            }
        }
        return selectedHouseholds;
    }

    public List<HouseholdDTO> getEligibleElderlies() {
        List<Household> allHouseholds = householdRepository.findAll();
        List<HouseholdDTO> selectedHouseholds = new ArrayList<>();
        for (Household household : allHouseholds) {
            List<FamilyMember> familyMemberList = familyMemberRepository.findByFamilyId(household.getId());
            List<FamilyMember> eligibleElderlies = new ArrayList<>();
            for (FamilyMember familyMember : familyMemberList) {
                LocalDate now = LocalDate.now();
                LocalDate dob = familyMember.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                float age = Period.between(dob, now).getYears();
                if (age > 55 && household.getHousingType().equals(HousingType.HDB)) {
                    eligibleElderlies.add(familyMember);
                }
            }
            if (!eligibleElderlies.isEmpty()) {
                selectedHouseholds.add(new HouseholdDTO(household.getId(), household.getHousingType(), eligibleElderlies));
            }
        }
        return selectedHouseholds;
    }

    public List<HouseholdDTO> getYGGEligibleHouseholds() {
        List<Household> allHouseholds = householdRepository.findAll();
        List<HouseholdDTO> selectedHouseholds = new ArrayList<>();
        for (Household household : allHouseholds) {
            List<FamilyMember> familyMemberList = familyMemberRepository.findByFamilyId(household.getId());
            BigDecimal householdIncome = BigDecimal.valueOf(0);
            BigDecimal minIncome = BigDecimal.valueOf(100000);
            for (FamilyMember familyMember : familyMemberList) {
                householdIncome = householdIncome.add(familyMember.getAnnualIncome());
            }
            if (householdIncome.compareTo(minIncome) < 0 && household.getHousingType().equals(HousingType.HDB)) {
                selectedHouseholds.add(new HouseholdDTO(household.getId(), household.getHousingType(), familyMemberList));
            }
        }
        return selectedHouseholds;
    }
}
