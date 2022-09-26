package com.example.finalexam.controller;

import com.example.finalexam.entity.Account;
import com.example.finalexam.entity.Department;
import com.example.finalexam.entity.criteria.DepartmentCriteria;
import com.example.finalexam.entity.dto.DepartmentDTO;
import com.example.finalexam.entity.form.CreatingDepartmentForm;
import com.example.finalexam.entity.form.UpdatingDepartmentForm;
import com.example.finalexam.service.DepartmentService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/departments")
@Validated
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentDTO>> getAll(Pageable pageable, DepartmentCriteria departmentCriteria){
        Page<DepartmentDTO> departments = departmentService.getAll(pageable,departmentCriteria);
        return ResponseEntity.ok().body(departments);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<DepartmentDTO>> getOne(@PathVariable Integer id){
        Optional<DepartmentDTO> department = departmentService.getOne(id);
        return  ResponseEntity.ok().body(department);
    }

    @PostMapping()
    public ResponseEntity<DepartmentDTO> create(@RequestBody CreatingDepartmentForm creatingDepartmentForm){
        DepartmentDTO newDepartment = departmentService.create(creatingDepartmentForm);
        return  ResponseEntity.status(HttpStatus.CREATED).body(newDepartment);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DepartmentDTO> update(@PathVariable Integer id, @RequestBody UpdatingDepartmentForm updatingDepartmentForm) {
        DepartmentDTO updatedDepartment = departmentService.update(id,updatingDepartmentForm);
        return  ResponseEntity.status(HttpStatus.OK).body(updatedDepartment);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DepartmentDTO> delete(@PathVariable Integer id) {
        DepartmentDTO deletedDepartment = departmentService.delete(id);
        return  ResponseEntity.status(HttpStatus.OK).body(deletedDepartment);
    }
}
