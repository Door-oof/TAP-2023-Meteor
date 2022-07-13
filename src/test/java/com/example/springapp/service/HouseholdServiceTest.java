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
import com.example.springapp.repository.FamilyMemberRepository;
import com.example.springapp.repository.HouseholdRepository;
import com.example.springapp.utils.Gender;
import com.example.springapp.utils.HousingType;
import com.example.springapp.utils.MaritalStatus;
import com.example.springapp.utils.OccupationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HouseholdServiceTest {
    @Mock
    private HouseholdRepository householdRepository;

    @Mock
    private FamilyMemberRepository familyMemberRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private HouseholdService householdService;

    @BeforeEach
    void testSetup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addHousehold_conflictIds() {
        Household newHousehold = createHousehold(HousingType.HDB, 1);
        when(householdRepository.existsById(any())).thenReturn(true);

        assertThrows(ConflictIdException.class, () -> householdService.addHousehold(newHousehold));
    }

    @Test
    public void addHousehold_success() {
        Household newHousehold = createHousehold(HousingType.HDB, 1);
        HouseholdCreationDTO householdCreation = householdService.addHousehold(newHousehold);

        assertNotNull(householdCreation);
        assertEquals(householdCreation.getHousingType(), HousingType.HDB);
        assertEquals(householdCreation.getId(), 1);
    }

    @Test
    public void getAllHouseholds_success() {
        when(householdRepository.findAll()).thenReturn(new ArrayList<>());
        List<HouseholdDTO> allHouseholds = householdService.getAllHouseholds();

        assertNotNull(allHouseholds);
    }

    @Test
    public void addFamilyMember_householdNotFound() {
        FamilyMember newFamilyMember = createFamilyMember("Mary Smith", Gender.F, MaritalStatus.Single, OccupationType.Student,
                BigDecimal.valueOf(24000), 1);

        assertThrows(HouseholdNotFoundException.class, () -> householdService.addFamilyMember(newFamilyMember));
    }

    @Test
    public void addFamilyMember_annualIncomeLowerThanZero() {
        FamilyMember newFamilyMember = createFamilyMember("Mary Smith", Gender.F, MaritalStatus.Single, OccupationType.Student,
                BigDecimal.valueOf(-4000), 1);
        when(householdRepository.findById(anyInt())).thenReturn(Optional.of(new Household()));

        assertThrows(AnnualIncomeLowerThanZeroException.class, () -> householdService.addFamilyMember(newFamilyMember));
    }

    @Test
    public void addFamilyMember_success() {
        FamilyMember newFamilyMember = createFamilyMember("Mary Smith", Gender.F, MaritalStatus.Single, OccupationType.Student,
                BigDecimal.valueOf(10000), 1);
        when(householdRepository.findById(anyInt())).thenReturn(Optional.of(new Household()));
        FamilyMemberCreationDTO familyMemberCreation = householdService.addFamilyMember(newFamilyMember);

        assertNotNull(familyMemberCreation.getFamilyMember());
        ArgumentCaptor<FamilyMember> familyMemberCaptor = ArgumentCaptor.forClass(FamilyMember.class);
        verify(familyMemberRepository, times(1)).save(familyMemberCaptor.capture());
        assertEquals(familyMemberCaptor.getValue(), familyMemberCreation.getFamilyMember());
    }

    @Test
    public void getSpecifiedHousehold_householdNotFound() {
        assertThrows(HouseholdNotFoundException.class, () -> householdService.getSpecifiedHousehold(anyInt()));
    }

    @Test
    public void getSpecifiedHousehold_success() {
        when(householdRepository.findById(any())).thenReturn(Optional.of(new Household()));
        when(mapper.toDto(any())).thenAnswer(inv -> {
            HouseholdDTO householdDTO = new HouseholdDTO();
            householdDTO.setId(1);
            householdDTO.setHousingType(HousingType.Landed);
            return householdDTO;
        });
        HouseholdDTO householdDTO = householdService.getSpecifiedHousehold(1);
        assertNotNull(householdDTO.getId());
    }

    private FamilyMember createFamilyMember(String name, Gender f, MaritalStatus single, OccupationType student, BigDecimal i, int familyId) {
        FamilyMember familyMember = new FamilyMember();
        familyMember.setFamilyId(familyId);
        familyMember.setOccupationType(student);
        familyMember.setMaritalStatus(single);
        familyMember.setGender(f);
        familyMember.setAnnualIncome(i);
        familyMember.setName(name);
        return familyMember;
    }

    private Household createHousehold(HousingType housingType, Integer id) {
        Household household = new Household();
        household.setId(id);
        household.setHousingType(housingType);
        return household;
    }
}
