package com.example.payroll.web

import com.example.payroll.domain.Employee
import com.example.payroll.service.EmployeeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeController(val employeeService: EmployeeService) {

    @GetMapping("/employees/")
    fun getEmployees(): List<EmployeeDto> {
        return employeeService.getEmployees().map(this::toDto)
    }

    fun toDto(employee: Employee): EmployeeDto =
            with(employee) {
                EmployeeDto(
                        id = id,
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        role = role
                )
            }

}