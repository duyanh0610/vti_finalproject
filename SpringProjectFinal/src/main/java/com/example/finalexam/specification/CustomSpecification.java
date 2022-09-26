package com.example.finalexam.specification;

import com.example.finalexam.common.Constants.OPERATOR;
import com.example.finalexam.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomSpecification<T> implements Specification<T> {

    private Expression expression;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = null;

        String field = expression.getField();
        String operator = expression.getOperator();
        Object value = expression.getValue();

        switch (operator){

            case OPERATOR.EQUALS:
                if(value instanceof Integer){
                    predicate = criteriaBuilder.equal(root.get(field), Integer.valueOf(String.valueOf(value)));
                }
                if(value instanceof String){
                    if(field.equals("departmentName")) {
                        predicate = criteriaBuilder.equal(root.get("department").get("name"), String.valueOf(value));
                        break;
                    }
                    if(field.equals("accountUsername")) {
                        Join join = root.join("accountList", JoinType.LEFT);
                        predicate = criteriaBuilder.equal(join.get("username"), String.valueOf(value));
                        break;
                    }

                    predicate = criteriaBuilder.equal(root.get(field), String.valueOf(value));
                }
                if(value instanceof Date){
                    predicate = criteriaBuilder.equal(root.get(field).as(java.util.Date.class), (Date) value);
                }
                break;

            case OPERATOR.NOT_EQUALS:
                if(value instanceof Integer){
                    predicate = criteriaBuilder.notEqual(root.get(field), Integer.valueOf(String.valueOf(value)));
                }
                if(value instanceof String){
                    predicate = criteriaBuilder.notEqual(root.get(field), String.valueOf(value));
                }
                if(value instanceof Date){
                    predicate = criteriaBuilder.notEqual(root.get(field), (Date) value);
                }
                break;

            case OPERATOR.CONTAINS:
                predicate = criteriaBuilder.like(root.get(field), "%" + String.valueOf(value) +"%");
                break;

            case OPERATOR.NOT_CONTAINS:
                predicate = criteriaBuilder.notLike(root.get(field), "%" + String.valueOf(value) +"%");
                break;

            case OPERATOR.GREATER_THAN:
                if(value instanceof Integer) {
                    predicate = criteriaBuilder.greaterThan(root.get(field), Integer.valueOf(String.valueOf(value)));
                }
                if(value instanceof Date){
                    predicate = criteriaBuilder.greaterThan(root.get(field), (Date) value);
                }
                break;

            case OPERATOR.LESS_THAN:
                if(value instanceof Integer) {
                    predicate = criteriaBuilder.lessThan(root.get(field), Integer.valueOf(String.valueOf(value)));
                }
                if (value instanceof Date) {
                    predicate = criteriaBuilder.lessThan(root.get(field), (Date) value);
                }
                break;
            case OPERATOR.GREATER_THAN_OR_EQUALS:
                if(value instanceof Integer) {
                    predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(field), Integer.valueOf(String.valueOf(value)));
                }
                if(value instanceof Date){
                    predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(field), (Date) value);
                }
                break;

            case OPERATOR.LESS_THAN_OR_EQUALS:
                if(value instanceof Integer) {
                    predicate = criteriaBuilder.lessThanOrEqualTo(root.get(field), Integer.valueOf(String.valueOf(value)));
                }
                if (value instanceof Date) {
                    predicate = criteriaBuilder.lessThanOrEqualTo(root.get(field), (Date) value);
                }
                break;

        }
        
        return predicate;
    }
}
