package com.example.springapp.config;

import com.example.springapp.dto.HouseholdDTO;
import com.example.springapp.entity.FamilyMember;
import com.example.springapp.entity.Household;
import com.example.springapp.repository.FamilyMemberRepository;
import com.example.springapp.utils.HousingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MapperTest {
    @Mock
    private FamilyMemberRepository familyMemberRepository;

    @InjectMocks
    private Mapper mapper;

    @Test
    public void mapperTest_success() {
        Household newHousehold = createHousehold(HousingType.HDB, 1);
        List<FamilyMember> familyMemberList = new ArrayList<>();
        when(familyMemberRepository.findByFamilyId(anyInt())).thenReturn(familyMemberList);

        HouseholdDTO householdDTO = mapper.toDto(newHousehold);

        assertNotNull(householdDTO.getId());
        assertNotNull(householdDTO.getFamilyMembers());
    }

    private Household createHousehold(HousingType housingType, Integer id) {
        Household household = new Household();
        household.setId(id);
        household.setHousingType(housingType);
        return household;
    }
}
