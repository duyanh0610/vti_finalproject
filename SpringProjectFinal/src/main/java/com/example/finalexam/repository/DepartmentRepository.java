package com.example.finalexam.repository;

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
public interface DepartmentRepository extends JpaRepository<Department,Integer>, JpaSpecificationExecutor<Department> {
    Optional<Department> findByName(String name);

    @Query("DELETE FROM Department d where d.id IN :ids")
    @Modifying
    @Transactional
    void deleteByIds(@Param("ids") List<Integer> ids);
}
