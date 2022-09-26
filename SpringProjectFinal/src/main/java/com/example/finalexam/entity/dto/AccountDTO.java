package com.example.finalexam.entity.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
public class AccountDTO extends RepresentationModel<AccountDTO>  {

    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    private String firstName;

    private String lastName;

    private String fullName;

    private String role;

    private Integer departmentId;

    private String departmentName;



}
