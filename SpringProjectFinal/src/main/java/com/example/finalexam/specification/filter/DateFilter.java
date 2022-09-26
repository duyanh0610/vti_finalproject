package com.example.finalexam.specification.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class DateFilter extends Filter<Date> {
    private Date greaterThan;
    private Date greaterThanOrEquals;
    private Date lessThan;
    private Date lessThanOrEquals;

}
