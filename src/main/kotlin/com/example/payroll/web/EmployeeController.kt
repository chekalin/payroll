package com.example.payroll.web

import com.example.payroll.service.EmployeeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeController(val employeeService: EmployeeService) {

    @GetMapping("/employees/")
    fun getEmployees(): List<EmployeeDto> {
        return employeeService.getEmployees().map(EmployeeDto.Companion::fromDomain)
    }

    @PostMapping("/employees/")
    fun createEmployee(@RequestBody newEmployee: EmployeeDto): EmployeeDto {
        val employee = EmployeeDto.toDomain(newEmployee)
        val createdEmployee = employeeService.createEmployee(employee)
        return EmployeeDto.fromDomain(createdEmployee)
    }

}