package com.example.payroll.service

import com.example.payroll.domain.Employee
import com.example.payroll.domain.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeService(val employeeRepository: EmployeeRepository) {

    fun getEmployees(): List<Employee> {
        return employeeRepository.findAll().filterNotNull()
    }

}
