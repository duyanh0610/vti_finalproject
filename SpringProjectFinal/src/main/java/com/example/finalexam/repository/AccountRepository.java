package com.example.finalexam.repository;

import com.example.finalexam.entity.Account;
import com.example.finalexam.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>, JpaSpecificationExecutor<Account> {
    Optional<Account> findByUsername(String username);
    List<Account> findAccountByDepartment(Department department);

    @Query("DELETE FROM Account a where a.id IN :ids")
    @Modifying
    @Transactional
    void deleteByIds(@Param("ids") List<Integer> ids);


}
