package com.example.springapp.service;

import com.example.springapp.dto.HouseholdDTO;
import com.example.springapp.entity.FamilyMember;
import com.example.springapp.entity.Household;
import com.example.springapp.repository.FamilyMemberRepository;
import com.example.springapp.repository.HouseholdRepository;
import com.example.springapp.utils.HousingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class GrantService {
    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

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
                double age = ChronoUnit.DAYS.between(dob, now) / 365.0;
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
                double age = ChronoUnit.DAYS.between(dob, now) / 365.0;
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
                double age = ChronoUnit.DAYS.between(dob, now) / 365.0;
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
                double age = ChronoUnit.DAYS.between(dob, now) / 365.0;
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
            if (householdIncome.compareTo(minIncome) < 0 && household.getHousingType().equals(HousingType.HDB)
                    && !familyMemberList.isEmpty()) {
                selectedHouseholds.add(new HouseholdDTO(household.getId(), household.getHousingType(), familyMemberList));
            }
        }
        return selectedHouseholds;
    }
}
