package com.example.finalexam.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expression {
    private String field;

    private String operator;

    private Object value;
}
