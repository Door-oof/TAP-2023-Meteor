package com.example.springapp.entity;

import com.example.springapp.utils.HousingType;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "Household")
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "household_id")
    private Integer householdId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "housing_type")
    private HousingType housingType;

    public void setId(Integer id) {
        this.householdId = id;
    }

    public Integer getId() {
        return householdId;
    }

    public void setHousingType(HousingType housingType) {
        this.housingType = housingType;
    }

    public HousingType getHousingType() {
        return housingType;
    }
}
