package com.example.nto.controller;

import com.example.nto.dto.EmployeeInfoDto;
import com.example.nto.entity.Employee;
import com.example.nto.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{code}/auth")
    public ResponseEntity<Employee>  auth(@PathVariable(value = "code") String code) {
        employeeService.checkExists(code);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{code}/info")
    public ResponseEntity<EmployeeInfoDto> info(@PathVariable(value = "code") String code) {
        return ResponseEntity.ok(employeeService.getEmployeeInfoDto(code));
    }
}
