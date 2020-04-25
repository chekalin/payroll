package com.example.payroll.service

import com.example.payroll.domain.Employee
import com.example.payroll.domain.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeService(val employeeRepository: EmployeeRepository) {

    fun getEmployees(): List<Employee> {
        return employeeRepository.findAll().filterNotNull()
    }

    fun createEmployee(employee: Employee): Employee {
        return employeeRepository.save(employee)
    }

    fun getEmployee(employeeId: String): Employee {
        return employeeRepository.findById(employeeId)
                .orElseThrow { EmployeeNotFoundException("Employee with id $employeeId not found") }
    }

}
