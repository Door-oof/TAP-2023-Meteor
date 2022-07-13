package com.example.springapp.service;

import com.example.springapp.config.Mapper;
import com.example.springapp.dto.HouseholdDTO;
import com.example.springapp.repository.FamilyMemberRepository;
import com.example.springapp.repository.HouseholdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GrantServiceTest {
    @Mock
    private HouseholdRepository householdRepository;

    @Mock
    private FamilyMemberRepository familyMemberRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private GrantService grantService;

    @BeforeEach
    void testSetup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getEligibleStudents_success() {
        when(householdRepository.findAll()).thenReturn(new ArrayList<>());
        List<HouseholdDTO> householdDTOList = grantService.getEligibleStudents();

        assertNotNull(householdDTOList);
    }

    @Test
    public void getEligibleBabies_success() {
        when(householdRepository.findAll()).thenReturn(new ArrayList<>());
        List<HouseholdDTO> householdDTOList = grantService.getEligibleBabies();

        assertNotNull(householdDTOList);
    }

    @Test
    public void getMSEligibleHouseholds_success() {
        when(householdRepository.findAll()).thenReturn(new ArrayList<>());
        List<HouseholdDTO> householdDTOList = grantService.getMSEligibleHouseholds();

        assertNotNull(householdDTOList);
    }

    @Test
    public void getEligibleElderlies_success() {
        when(householdRepository.findAll()).thenReturn(new ArrayList<>());
        List<HouseholdDTO> householdDTOList = grantService.getEligibleElderlies();

        assertNotNull(householdDTOList);
    }

    @Test
    public void getYGGEligibleHouseholds() {
        when(householdRepository.findAll()).thenReturn(new ArrayList<>());
        List<HouseholdDTO> householdDTOList = grantService.getYGGEligibleHouseholds();

        assertNotNull(householdDTOList);
    }
}
