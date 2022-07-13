package com.example.springapp.config;

import com.example.springapp.dto.HouseholdDTO;
import com.example.springapp.entity.FamilyMember;
import com.example.springapp.entity.Household;
import com.example.springapp.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapper {

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    public HouseholdDTO toDto(Household household) {
         List<FamilyMember> familyMemberList = familyMemberRepository.findByFamilyId(household.getId());
         return new HouseholdDTO(household.getId(), household.getHousingType(), familyMemberList);
    }
}
