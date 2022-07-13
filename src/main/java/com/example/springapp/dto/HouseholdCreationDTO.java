package com.example.springapp.dto;

import com.example.springapp.utils.HousingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseholdCreationDTO {
    private Integer id;
    private HousingType housingType;
}