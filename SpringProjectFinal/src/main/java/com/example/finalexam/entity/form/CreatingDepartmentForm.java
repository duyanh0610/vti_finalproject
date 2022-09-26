package com.example.finalexam.entity.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class CreatingDepartmentForm {

    @Length(max = 50 ,message = "username's max length is 50")
    private String name;

    private String totalMember;

    private String type;
}
