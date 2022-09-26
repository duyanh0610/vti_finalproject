package com.example.finalexam.service;

import com.example.finalexam.common.Constants.OPERATOR;
import com.example.finalexam.specification.CustomSpecification;
import com.example.finalexam.specification.Expression;
import com.example.finalexam.specification.filter.DateFilter;
import com.example.finalexam.specification.filter.IntegerFilter;
import com.example.finalexam.specification.filter.StringFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class QueryService<T> {

        public Specification<T> buildIntegerFilter(String field, IntegerFilter integerFilter) {
            if (integerFilter.getEquals() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.EQUALS, integerFilter.getEquals()));
            }
            if (integerFilter.getNotEquals() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.NOT_EQUALS, integerFilter.getNotEquals()));
            }
            if (integerFilter.getGreaterThan() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.GREATER_THAN, integerFilter.getGreaterThan()));
            }
            if (integerFilter.getLessThan() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.LESS_THAN, integerFilter.getLessThan()));
            }
            if (integerFilter.getGreaterThanOrEquals() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.GREATER_THAN_OR_EQUALS, integerFilter.getGreaterThanOrEquals()));
            }
            if (integerFilter.getLessThanOrEquals() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.LESS_THAN_OR_EQUALS, integerFilter.getLessThanOrEquals()));
            }
            return null;
        }
        public Specification<T> buildStringFilter(String field, StringFilter stringFilter) {
            if (stringFilter.getEquals() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.EQUALS, stringFilter.getEquals()));
            }
            if (stringFilter.getNotEquals() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.NOT_EQUALS, stringFilter.getNotEquals()));
            }
            if (stringFilter.getContains() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.CONTAINS, stringFilter.getContains()));
            }
            if (stringFilter.getNotContains() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.NOT_CONTAINS, stringFilter.getNotContains()));
            }
            return null;
        }
        public Specification<T> buildDateFilter(String field, DateFilter dateFilter) {
            if (dateFilter.getEquals() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.EQUALS, dateFilter.getEquals()));
            }
            if (dateFilter.getNotEquals() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.NOT_EQUALS, dateFilter.getNotEquals()));
            }
            if (dateFilter.getGreaterThan() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.GREATER_THAN, dateFilter.getGreaterThan()));
            }
            if (dateFilter.getLessThan() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.LESS_THAN, dateFilter.getLessThan()));
            }
            if (dateFilter.getGreaterThanOrEquals() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.GREATER_THAN_OR_EQUALS, dateFilter.getGreaterThanOrEquals()));
            }
            if (dateFilter.getLessThanOrEquals() != null) {
                return new CustomSpecification<>(new Expression(field, OPERATOR.LESS_THAN_OR_EQUALS, dateFilter.getLessThanOrEquals()));
            }
            return null;
        }


}
