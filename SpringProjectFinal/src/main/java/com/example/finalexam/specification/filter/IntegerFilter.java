package com.example.finalexam.specification.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
    public class IntegerFilter extends Filter<Integer> {
        private Integer greaterThan;
        private Integer greaterThanOrEquals;
        private Integer lessThan;
        private Integer lessThanOrEquals;
}
