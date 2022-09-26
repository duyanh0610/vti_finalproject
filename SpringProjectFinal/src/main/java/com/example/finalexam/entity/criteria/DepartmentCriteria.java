package com.example.finalexam.entity.criteria;


import com.example.finalexam.specification.filter.DateFilter;
import com.example.finalexam.specification.filter.IntegerFilter;
import com.example.finalexam.specification.filter.StringFilter;
import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class DepartmentCriteria {

    private StringFilter name;

    private IntegerFilter totalNumber;

    private StringFilter type;

    private DateFilter createdDate;

    private StringFilter search;

    private StringFilter accountUsername;



}
