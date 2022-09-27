package com.example.finalexam.service;


import com.example.finalexam.common.Constants;
import com.example.finalexam.entity.Account;
import com.example.finalexam.entity.Department;
import com.example.finalexam.entity.criteria.AccountCriteria;
import com.example.finalexam.entity.dto.AccountDTO;
import com.example.finalexam.entity.form.CreatingAccountForm;
import com.example.finalexam.entity.form.UpdatingAccountForm;
import com.example.finalexam.exception.CustomError;
import com.example.finalexam.exception.CustomException;
import com.example.finalexam.repository.AccountRepository;
import com.example.finalexam.repository.DepartmentRepository;
import com.example.finalexam.specification.Expression;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImplement implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final QueryService<Account> queryService;
    private final DepartmentRepository departmentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public AccountServiceImplement(AccountRepository accountRepository, ModelMapper modelMapper, QueryService<Account> queryService, DepartmentRepository departmentRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        this.queryService = queryService;
        this.departmentRepository = departmentRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Page<AccountDTO> getAll(Pageable pageable, AccountCriteria accountCriteria) {
        Specification<Account> where = buildWhere(accountCriteria);

        Page<Account> accountPage = accountRepository.findAll(where, pageable);

        List<AccountDTO> accounts = accountPage
                .getContent()
                .stream()
                .map(account -> modelMapper.map(account, AccountDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(accounts, pageable, accountPage.getTotalElements());
    }
    @Override
    public Optional<AccountDTO> getOne(Integer id) {
        return accountRepository.findById(id).map(account -> modelMapper.map(account, AccountDTO.class));
    }
    @Override
    public AccountDTO create(CreatingAccountForm creatingAccountForm) {
        validateCreate(creatingAccountForm);
        return departmentRepository.findById(creatingAccountForm.getDepartmentId())
                .map(department ->{
                    Account account = modelMapper.map(creatingAccountForm, Account.class)
                            .id(null)
                            .password(bCryptPasswordEncoder.encode(creatingAccountForm.getPassword()))
                            .department(modelMapper.map(department, Department.class));
                    return accountRepository.save(account);
                }).map(account -> modelMapper.map(account, AccountDTO.class))
                .orElse(null );
    }
    @Override
    public AccountDTO update(Integer id, UpdatingAccountForm updatingAccountForm)  {
        validateUpdate(updatingAccountForm);
        return getOne(id)
                .map(account ->modelMapper.map(updatingAccountForm, Account.class))
                .map(account -> {
                    Optional.ofNullable(updatingAccountForm.getDepartmentId())
                            .flatMap(departmentId -> departmentRepository.findById(departmentId)
                                    .map(department -> {
                                        account.id(id).department(department).password(bCryptPasswordEncoder.encode(updatingAccountForm.getPassword()));
                                        return account;
                                    })).orElseGet(() -> account.id(id).department(null));
                    return account;
                })
                .map(accountRepository::save)
                .map(account -> modelMapper.map(account,AccountDTO.class))
                .orElseThrow(() -> new CustomException()
                                    .customError(new CustomError()
                                            .code("account.id.idNotExist")
                                            .param(id)));

    }
    @Override
    public AccountDTO delete(Integer id) throws ChangeSetPersister.NotFoundException {
        return getOne(id)
                .map(acc -> {
                    accountRepository.deleteById(id);
                    return acc;
                })
                .orElseThrow(() -> new CustomException()
                        .customError(new CustomError()
                                .code("account.id.idNotExist")
                                .param(id)));
    }

    @Override
    public Optional<AccountDTO> findByUsername(String username){
        return  accountRepository.findByUsername(username).map(account -> modelMapper.map(account,AccountDTO.class));
    }

    @Override
    public void deleteAccounts(List<Integer> ids) {
        accountRepository.deleteByIds(ids);
    }

    private void validateCreate(CreatingAccountForm creatingAccountForm) {
        validateUsername(creatingAccountForm.getUsername());
        validateFirstName(creatingAccountForm.getFirstName());
        validateLastName(creatingAccountForm.getLastName());
        validateRole(creatingAccountForm.getRole());
    }
    private void validateUpdate(UpdatingAccountForm updatingAccountForm) {
        validateUsername(updatingAccountForm.getUsername());
        validateFirstName(updatingAccountForm.getFirstName());
        validateLastName(updatingAccountForm.getLastName());
        validateRole(updatingAccountForm.getRole());
    }

    private void validateUsername(String username) {
        accountRepository
                .findByUsername(username)
                .map(account ->{
                            throw new CustomException()
                                    .customError(new CustomError()
                                                        .code("account.username.usernameIsExists")
                                                        .param(username));
                });
        if (username.trim().isEmpty()){
            throw new CustomException()
                    .customError(new CustomError()
                            .code("account.username.usernameNotValid")
                            .param(username));
        }
    }
    private void validateFirstName(String firstName) {
        if (firstName.trim().isEmpty()){
            throw new CustomException()
                    .customError(new CustomError()
                            .code("account.firstName.firstNameNotValid")
                            .param(firstName));
        }
    }
    private void validateLastName(String username) {
        if (username.trim().isEmpty()){
            throw new CustomException()
                    .customError(new CustomError()
                            .code("account.lastName.LastNameNotValid")
                            .param(username));
        }
    }
    private void validateRole(String role) {
        if( !(role.equals(Constants.ROLE.ADMIN) ||
            role.equals(Constants.ROLE.MANAGER) ||
            role.equals(Constants.ROLE.EMPLOYEE)) ){
            throw new CustomException()
                                .customError(new CustomError()
                                        .code("account.role.roleNotValid")
                                        .param(role));

        }

    }
    private void validateDepartment(Integer departmentId) {
        Optional.ofNullable(departmentId)
                .map(id -> {
                    departmentRepository
                            .findById(id)
                            .orElseThrow(() ->
                                    new CustomException()
                                            .customError(new CustomError()
                                                    .code("account.department.departmentIdNotExist")
                                                    .param(id)));

                    return id;
        }).orElseThrow(()->
                        new CustomException()
                                .customError(new CustomError()
                                        .code("account.department.isNull")
                                        .param(departmentId)));

    }

    private void validateExpression(Expression expression) throws Exception {
        validateField(expression.getField());
        validateOperator(expression.getOperator());
        validateValue(expression.getValue());
    }
    private void validateOperator(String operator) throws Exception {
        if (operator == null || operator.trim().equals("")) {
            throw new Exception("Operator can be null");
        }
    }
    private void validateField(String field) throws Exception {
        if (field == null || field.trim().equals("")) {
            throw new Exception("Field can be null");
        }
    }
    private void validateValue(Object value) throws Exception {
        if (value == null) {
            throw new Exception("value can be null");
        }
    }

    private Specification<Account> buildWhere(AccountCriteria accountCriteria) {
        Specification<Account> where = Specification.where(null);

        if (accountCriteria.getId() != null) {
            where = where.and(queryService.buildIntegerFilter(Constants.ACCOUNT.ID, accountCriteria.getId()));
        }
        if (accountCriteria.getUsername() != null) {
            where = where.and(queryService.buildStringFilter(Constants.ACCOUNT.USERNAME, accountCriteria.getUsername()));
        }
        if (accountCriteria.getFirstName() != null) {
            where = where.and(queryService.buildStringFilter(Constants.ACCOUNT.FIRST_NAME, accountCriteria.getFirstName()));
        }
        if (accountCriteria.getLastName() != null) {
            where = where.and(queryService.buildStringFilter(Constants.ACCOUNT.LAST_NAME, accountCriteria.getLastName()));
        }
        if (accountCriteria.getRole() != null) {
            where = where.and(queryService.buildStringFilter(Constants.ACCOUNT.ROLE, accountCriteria.getRole()));
        }
        if (accountCriteria.getDepartmentName() != null) {
            where = where.and(queryService.buildStringFilter("departmentName", accountCriteria.getDepartmentName()));
        }

        if (accountCriteria.getSearch() != null) {
            Specification<Account> orSpec = Specification.where(null);

            orSpec = orSpec
                    .or(queryService.buildStringFilter(Constants.ACCOUNT.USERNAME, accountCriteria.getSearch()))
                    .or(queryService.buildStringFilter(Constants.ACCOUNT.FIRST_NAME, accountCriteria.getSearch()))
                    .or(queryService.buildStringFilter(Constants.ACCOUNT.LAST_NAME, accountCriteria.getSearch()))
                    .or(queryService.buildStringFilter(Constants.ACCOUNT.ROLE, accountCriteria.getSearch()));
//                    .or(queryService.buildStringFilter("departmentName", accountCriteria.getDepartmentName()));

            where = where.and(orSpec);
        }

        return where;
    }
}
