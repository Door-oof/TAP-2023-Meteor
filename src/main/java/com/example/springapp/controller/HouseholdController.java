package com.example.springapp.controller;

import com.example.springapp.dto.FamilyMemberCreationDTO;
import com.example.springapp.dto.HouseholdCreationDTO;
import com.example.springapp.dto.HouseholdDTO;
import com.example.springapp.entity.FamilyMember;
import com.example.springapp.entity.Household;
import com.example.springapp.service.HouseholdService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;

    @ApiOperation(value = "List all households")
    @RequestMapping(value = "/households", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<HouseholdDTO> getHouseholds() {
        return householdService.getAllHouseholds();
    }

    @ApiOperation(value = "List specified household")
    @RequestMapping(value = "/households/{householdId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 404, message = "Household does not exist"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public HouseholdDTO getSpecifiedHousehold(@ApiParam(value = "List all family members in the specified household", required = true)
                                                  @PathVariable Integer householdId) {
        return householdService.getSpecifiedHousehold(householdId);
    }

    @ApiOperation(value = "Create a new household")
    @PostMapping(value = "/households")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 409, message = "Household already exist"),
            @ApiResponse(code = 400,  message = "Invalid input")
    })
    public HouseholdCreationDTO newHousehold(@ApiParam(value = "Add a new household", required = true)@RequestBody Household household) {
        return householdService.addHousehold(household);
    }

    @ApiOperation(value = "Add a family member to a household")
    @PostMapping(value = "/family-members")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 404, message = "Household does not exist"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public FamilyMemberCreationDTO newFamilyMember(@ApiParam(value = "Add a new family member to a specified existing household", required = true)
                                                       @RequestBody FamilyMember familyMember) {
        return householdService.addFamilyMember(familyMember);
    }

    @ApiOperation(value = "List all students who are eligible for Student Encouragement Bonus (SEB) scheme",
            notes = "This scheme only applies to households with members that is/are a student of less than 16 years old " +
                    "and to households that have income of less than $200,000.\n" +
                    "Qualifying members only include members who are less than 16 years old.")
    @RequestMapping(value = "/SEB", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<HouseholdDTO> getEligibleStudents() {
        return householdService.getEligibleStudents();
    }

    @ApiOperation(value = "List all babies who are eligible for Baby Sunshine Grant (BSG) scheme",
            notes = "This scheme only applies to households with members that is/are younger than 8 months old.\n" +
                    "Qualifying members only include members who are younger than 8 months old.")
    @RequestMapping(value = "/BSG", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<HouseholdDTO> getEligibleBabies() {
        return householdService.getEligibleBabies();
    }

    @ApiOperation(value = "List all households who are eligible for Multigeneration Scheme (MS)",
            notes = "This scheme only applies to households with either members that is/are a student of less than 18 years old " +
                    "or above the age of 55, " +
                    "and to households with annual income of less than $150,000.\n" +
                    "Qualifying members includes all members of the household.")
    @RequestMapping(value = "/MS", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<HouseholdDTO> getMSEligibleHouseholds() {
        return householdService.getMSEligibleHouseholds();
    }

    @ApiOperation(value = "List all elderlies who are eligible for Elder Bonus (EB) scheme",
            notes = "This scheme only applies to HDB households with members above the age of 55.\n" +
                    "Qualifying members only include elderlies above the age of 55.")
    @RequestMapping(value = "/EB", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<HouseholdDTO> getEligibleElderlies() {
        return householdService.getEligibleElderlies();
    }

    @ApiOperation(value = "List all HDB households who are eligible for YOLO GST Grant (YGG) scheme",
            notes = "This scheme only applies to HDB households with with annual income of less than $100,000.\n" +
                    "Qualifying members includes all members of the household.")
    @RequestMapping(value = "/YGG", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<HouseholdDTO> getYGGEligibleHouseholds() {
        return householdService.getYGGEligibleHouseholds();
    }
}
