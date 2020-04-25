package com.example.payroll.web

import com.example.payroll.domain.Employee
import com.example.payroll.service.EmployeeNotFoundException
import com.example.payroll.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class EmployeeController(val employeeService: EmployeeService) {

    @GetMapping("/employees/")
    fun getEmployees(): List<EmployeeDto> {
        return employeeService.getEmployees().map(EmployeeDto.Companion::fromDomain)
    }

    @GetMapping("/employees/{employeeId}")
    fun getEmployee(@PathVariable employeeId: String): EmployeeDto {
        val employee = employeeService.getEmployee(employeeId)
        return EmployeeDto.fromDomain(employee)
    }

    @PostMapping("/employees/")
    fun createEmployee(@RequestBody newEmployee: CreateEmployeeDto): EmployeeDto {
        val employee = with(newEmployee) {
            Employee(id = UUID.randomUUID().toString(),
                    firstName = firstName,
                    lastName = lastName,
                    role = role,
                    email = email)
        }
        val createdEmployee = employeeService.createEmployee(employee)
        return EmployeeDto.fromDomain(createdEmployee)
    }

    @ExceptionHandler(EmployeeNotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Employee not found")
    fun handleException() {
    }

}