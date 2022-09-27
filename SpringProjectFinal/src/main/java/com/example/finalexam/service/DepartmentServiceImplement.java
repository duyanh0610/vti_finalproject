package com.example.finalexam.service;

import com.example.finalexam.common.Constants;
import com.example.finalexam.entity.Department;
import com.example.finalexam.entity.criteria.DepartmentCriteria;
import com.example.finalexam.entity.dto.DepartmentDTO;
import com.example.finalexam.entity.form.CreatingDepartmentForm;
import com.example.finalexam.entity.form.UpdatingDepartmentForm;
import com.example.finalexam.exception.CustomError;
import com.example.finalexam.exception.CustomException;
import com.example.finalexam.repository.AccountRepository;
import com.example.finalexam.repository.DepartmentRepository;
import com.example.finalexam.specification.Expression;
import org.hibernate.annotations.SQLDelete;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentServiceImplement implements DepartmentService{
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final QueryService<Department> queryService;
    private final AccountRepository accountRepository;


    public DepartmentServiceImplement(DepartmentRepository departmentRepository, ModelMapper modelMapper, QueryService<Department> queryService, AccountRepository accountRepository) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
        this.queryService = queryService;
        this.accountRepository = accountRepository;
    }

    @Override
    public Page<DepartmentDTO> getAll(Pageable pageable, DepartmentCriteria departmentCriteria) {
        Specification<Department> where = buildWhere(departmentCriteria);

        Page<Department> departmentPage = departmentRepository.findAll(where,pageable);

        List<DepartmentDTO> departments = departmentPage
                .getContent()
                .stream()
                .map(department -> modelMapper.map(department,DepartmentDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(departments,pageable,departmentPage.getTotalElements());
    }

    @Override
    public Optional<DepartmentDTO> getOne(Integer id) {
        return departmentRepository
                .findById(id)
                .map(department -> modelMapper.map(department, DepartmentDTO.class));
    }

    @Override
    public DepartmentDTO create(CreatingDepartmentForm creatingDepartmentForm) {
        validateCreateDepartment(creatingDepartmentForm);
        Department department = modelMapper.map(creatingDepartmentForm, Department.class);
        departmentRepository.save(department);
        return modelMapper.map(department,DepartmentDTO.class);

    }

    @Override
    public DepartmentDTO update(Integer id, UpdatingDepartmentForm updatingDepartmentForm){
        validateUpdateDepartment(updatingDepartmentForm);
        return getOne(id)
                .map(departmentDTO ->  modelMapper.map(updatingDepartmentForm,Department.class))
                .map(department -> {
                    department.id(id);
                    departmentRepository.save(department);
                    return department;
                })
                .map(department -> modelMapper.map(department, DepartmentDTO.class))
                .orElseThrow(()-> new CustomException()
                        .customError(new CustomError()
                                .code("department.id.idNotExist")));
    }

    @Override
    public DepartmentDTO delete(Integer id)  {

        return  departmentRepository
                .findById(id)
                .map(dept -> {
                    departmentRepository.deleteById(id);

                    return dept;
                }).map(department -> modelMapper.map(department,DepartmentDTO.class))
                .orElseThrow(()-> new CustomException()
                        .customError(new CustomError()
                                .code("department.id.idNotExist")));
    }

    @Override
    public Optional<DepartmentDTO> findByName(String name) {
        return departmentRepository
                .findByName(name)
                .map(department -> modelMapper.map(department,DepartmentDTO.class));
    }

    @Override
    public void deleteDepartment(List<Integer> ids) {
        departmentRepository.deleteByIds(ids);
    }


    private void validateCreateDepartment(CreatingDepartmentForm creatingDepartmentForm) {
        validateName(creatingDepartmentForm.getName());
        validateType(creatingDepartmentForm.getType());
    }
    private void validateUpdateDepartment(UpdatingDepartmentForm updatingDepartmentForm) {
        validateName(updatingDepartmentForm.getName());
        validateType(updatingDepartmentForm.getType());
    }
    private void validateType(String type) {
        if( !(type.equals(Constants.TYPE.DEV) ||
                type.equals(Constants.TYPE.PM) ||
                type.equals(Constants.TYPE.SCRUM_MASTER) ||
                type.equals(Constants.TYPE.TEST)) ){
            throw new CustomException()
                    .customError(new CustomError()
                            .code("department.type.typeNotValid")
                            .param(type));

        }
    }
    private void validateName(String name) {
        departmentRepository
                .findByName(name)
                .map(account ->{
                    throw new CustomException()
                            .customError(new CustomError()
                                    .code("department.name.nameIsExist")
                                    .param(name));
                });
        if (name.trim().isEmpty()){
            throw new CustomException()
                    .customError(new CustomError()
                            .code("department.name.nameNotValid")
                            .param(name));
        }
    }

    private void validateExpression(Expression expression) throws Exception {
        validateField(expression.getField());
        validateOperator(expression.getOperator());
        validateValue(expression.getValue());
    }
    private void validateOperator(String operator) throws Exception {
        if(operator == null || operator.trim().equals("")){
            throw new Exception("Operator can be null");
        }
    }
    private void validateField(String field) throws Exception {
        if(field == null || field.trim().equals("")){
            throw new Exception("Field can be null");
        }
    }
    private void validateValue(Object value) throws Exception {
        if(value == null){
            throw new Exception("value can be null");
        }
    }

    private Specification<Department> buildWhere(DepartmentCriteria departmentCriteria){
        Specification<Department> where = Specification.where(null);

        if(departmentCriteria.getName() != null) {
            where = where.and(queryService.buildStringFilter(Constants.DEPARTMENT.NAME, departmentCriteria.getName()));
        }
        if(departmentCriteria.getType() != null) {
            where = where.and(queryService.buildStringFilter(Constants.DEPARTMENT.TYPE, departmentCriteria.getType()));
        }
        if(departmentCriteria.getTotalNumber() != null) {
            where = where.and(queryService.buildIntegerFilter(Constants.DEPARTMENT.TOTAL_NUMBER, departmentCriteria.getTotalNumber()));
        }
        if(departmentCriteria.getCreatedDate() != null) {
            where = where.and(queryService.buildDateFilter(Constants.DEPARTMENT.CREATED_DATE, departmentCriteria.getCreatedDate()));
        }
        if(departmentCriteria.getAccountUsername() != null) {
            where = where.and(queryService.buildStringFilter("accountUsername", departmentCriteria.getAccountUsername()));
        }

        if(departmentCriteria.getSearch() != null){
            Specification<Department> orSpec = Specification.where(null);

            orSpec = orSpec
                    .or(queryService.buildStringFilter(Constants.DEPARTMENT.NAME, departmentCriteria.getSearch()))
                    .or(queryService.buildStringFilter(Constants.DEPARTMENT.TYPE,departmentCriteria.getSearch()));

            where = where.and(orSpec);
        }
        return where;
    }
}
