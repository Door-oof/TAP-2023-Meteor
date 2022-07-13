package com.example.springapp.dto;

import com.example.springapp.entity.FamilyMember;
import com.example.springapp.utils.HousingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseholdDTO {
    private Integer id;
    private HousingType housingType;
    private List<FamilyMember> familyMembers;
}
