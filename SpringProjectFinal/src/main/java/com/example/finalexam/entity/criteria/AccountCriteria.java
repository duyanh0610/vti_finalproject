package com.example.finalexam.entity.criteria;

import com.example.finalexam.specification.filter.DateFilter;
import com.example.finalexam.specification.filter.IntegerFilter;
import com.example.finalexam.specification.filter.StringFilter;
import lombok.Data;

@Data
public class AccountCriteria {

    private IntegerFilter id;

    private StringFilter username;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter role;

    private DateFilter date;

    private StringFilter search;

    private StringFilter departmentName;
}
