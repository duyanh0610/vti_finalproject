package com.example.finalexam.service;


import com.example.finalexam.entity.Department;
import com.example.finalexam.entity.criteria.DepartmentCriteria;
import com.example.finalexam.entity.dto.DepartmentDTO;
import com.example.finalexam.entity.form.CreatingDepartmentForm;
import com.example.finalexam.entity.form.UpdatingDepartmentForm;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Page<DepartmentDTO> getAll(Pageable pageable, DepartmentCriteria departmentCriteria);
    Optional<DepartmentDTO> getOne(Integer id);
    DepartmentDTO create(CreatingDepartmentForm creatingDepartmentForm);
    DepartmentDTO update(Integer id, UpdatingDepartmentForm updatingDepartmentForm);
    DepartmentDTO delete(Integer id);
    Optional<DepartmentDTO> findByName(String name);

    void deleteDepartment(List<Integer> ids);
}
