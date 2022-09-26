package com.example.finalexam.controller;

import com.example.finalexam.entity.Account;
import com.example.finalexam.entity.criteria.AccountCriteria;
import com.example.finalexam.entity.dto.AccountDTO;
import com.example.finalexam.entity.form.CreatingAccountForm;
import com.example.finalexam.entity.form.UpdatingAccountForm;
import com.example.finalexam.service.AccountService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;




@RestController
@RequestMapping(value = "/api/v1/accounts")
@Validated
public class AccountController  {
    private final AccountService accountService;
    private final ModelMapper modelMapper;

    public AccountController(AccountService accountService, ModelMapper modelMapper) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<Page<AccountDTO>> getAll(Pageable pageable, AccountCriteria accountCriteria){
        Page<AccountDTO> accounts = accountService.getAll(pageable,accountCriteria);

        List<AccountDTO> accountDTOList = modelMapper
                .map(
                        accounts.getContent(),
                        new TypeToken<List<AccountDTO>>() {}.getType());
        accountDTOList
                .forEach(accountDTO ->
                        accountDTO
                                .add(linkTo(methodOn(DepartmentController.class)
                                        .getOne(accountDTO.getDepartmentId()))
                                    .withSelfRel()));

        return ResponseEntity.ok().body(new PageImpl<>(accountDTOList,pageable,accounts.getTotalElements()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<AccountDTO>> getOne(@PathVariable Integer id){
        Optional<AccountDTO> account = accountService.getOne(id)
                .map(accountDTO -> {
                    accountDTO.add(linkTo(methodOn(AccountController.class).getOne(id)).withSelfRel());
                    accountDTO.add(linkTo(methodOn(DepartmentController.class).getOne(accountDTO.getDepartmentId())).withSelfRel());

                    return  accountDTO;
                });

        return  ResponseEntity.ok().body(account);
    }

    @PostMapping()
    public ResponseEntity<AccountDTO> create(@RequestBody @Valid CreatingAccountForm creatingAccountForm){
        AccountDTO newAccount = accountService.create(creatingAccountForm);
        return  ResponseEntity.status(HttpStatus.CREATED).body(newAccount);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> update(
            @PathVariable Integer id,
            @RequestBody @Valid UpdatingAccountForm updatingAccountForm)
    {
        AccountDTO updatedAccount = accountService.update(id,updatingAccountForm);
        return  ResponseEntity.status(HttpStatus.OK).body(updatedAccount);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> delete(@PathVariable Integer id) throws ChangeSetPersister.NotFoundException {
        AccountDTO deletedAccount = accountService.delete(id);
        return  ResponseEntity.status(HttpStatus.OK).body(deletedAccount);
    }

}
