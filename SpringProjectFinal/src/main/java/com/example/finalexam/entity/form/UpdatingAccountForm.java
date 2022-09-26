package com.example.finalexam.entity.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UpdatingAccountForm {
    @NotBlank(message = "username can not be null}")
    @Length(max = 50 ,message = "username's max length is 50")
    private String username;

    @Length(max = 100 ,message = "password's max length is 50")
    private String password;

    @NotBlank(message = "firstName can not be null}")
    @Length(max = 50 ,message = "firstName's max length is 50")
    private String firstName;

    @NotBlank(message = "lastName can not be null}")
    @Length(max = 50 ,message = "lastName's max length is 50")
    private String lastName;

    private String role;

    private Integer departmentId;

}
