package com.example.finalexam.service;

import com.example.finalexam.entity.Account;
import com.example.finalexam.entity.criteria.AccountCriteria;
import com.example.finalexam.entity.dto.AccountDTO;
import com.example.finalexam.entity.form.CreatingAccountForm;
import com.example.finalexam.entity.form.UpdatingAccountForm;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountService  {
    Page<AccountDTO> getAll(Pageable pageable, AccountCriteria accountCriteria);
    Optional<AccountDTO> getOne(Integer id);
    AccountDTO create(CreatingAccountForm creatingAccountForm);
    AccountDTO update(Integer id, UpdatingAccountForm updatingAccountForm);
    AccountDTO delete(Integer id) throws ChangeSetPersister.NotFoundException;
    Optional<AccountDTO> findByUsername(String username);


    void deleteAccounts(List<Integer> ids);
}
