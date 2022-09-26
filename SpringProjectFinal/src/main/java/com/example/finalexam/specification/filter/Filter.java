package com.example.finalexam.specification.filter;

import lombok.Data;

import java.io.Serializable;

@Data
public class Filter<Type> implements Serializable {
    private Type equals;
    private Type notEquals;
}
