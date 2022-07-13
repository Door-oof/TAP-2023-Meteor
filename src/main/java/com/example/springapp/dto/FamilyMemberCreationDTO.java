package com.example.springapp.dto;

import com.example.springapp.entity.FamilyMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyMemberCreationDTO {
    private FamilyMember familyMember;
}
