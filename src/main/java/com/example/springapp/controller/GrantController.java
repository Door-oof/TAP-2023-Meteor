package com.example.springapp.controller;

import com.example.springapp.dto.HouseholdDTO;
import com.example.springapp.service.GrantService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrantController {

    @Autowired
    private GrantService grantService;

    @ApiOperation(value = "List all students who are eligible for Student Encouragement Bonus (SEB) scheme",
            notes = "This scheme only applies to households with members that is/are a student of less than 16 years old " +
                    "and to households that have income of less than $200,000.\n" +
                    "Qualifying members only include members who are less than 16 years old.")
    @RequestMapping(value = "/grants/SEB", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<HouseholdDTO> getEligibleStudents() {
        return grantService.getEligibleStudents();
    }

    @ApiOperation(value = "List all babies who are eligible for Baby Sunshine Grant (BSG) scheme",
            notes = "This scheme only applies to households with members that is/are younger than 8 months old.\n" +
                    "Qualifying members only include members who are younger than 8 months old.")
    @RequestMapping(value = "/grants/BSG", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<HouseholdDTO> getEligibleBabies() {
        return grantService.getEligibleBabies();
    }

    @ApiOperation(value = "List all households who are eligible for Multigeneration Scheme (MS)",
            notes = "This scheme only applies to households with either members that is/are a student of less than 18 years old " +
                    "or above the age of 55, " +
                    "and to households with annual income of less than $150,000.\n" +
                    "Qualifying members includes all members of the household.")
    @RequestMapping(value = "/grants/MS", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<HouseholdDTO> getMSEligibleHouseholds() {
        return grantService.getMSEligibleHouseholds();
    }

    @ApiOperation(value = "List all elderlies who are eligible for Elder Bonus (EB) scheme",
            notes = "This scheme only applies to HDB households with members above the age of 55.\n" +
                    "Qualifying members only include elderlies above the age of 55.")
    @RequestMapping(value = "/grants/EB", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<HouseholdDTO> getEligibleElderlies() {
        return grantService.getEligibleElderlies();
    }

    @ApiOperation(value = "List all HDB households who are eligible for YOLO GST Grant (YGG) scheme",
            notes = "This scheme only applies to HDB households with with annual income of less than $100,000.\n" +
                    "Qualifying members includes all members of the household.")
    @RequestMapping(value = "/grants/YGG", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<HouseholdDTO> getYGGEligibleHouseholds() {
        return grantService.getYGGEligibleHouseholds();
    }
}
