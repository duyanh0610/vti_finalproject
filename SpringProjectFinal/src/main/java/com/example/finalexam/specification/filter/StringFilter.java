package com.example.finalexam.specification.filter;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StringFilter extends Filter<String> {

    private String contains;

    private String notContains;
}
