package com.example.payroll.web

import com.example.payroll.domain.Employee
import java.util.*

data class EmployeeDto(
        var id: String? = null,
        val firstName: String,
        val lastName: String,
        val email: String,
        val role: String) {

    companion object {
        fun fromDomain(employee: Employee): EmployeeDto =
                with(employee) {
                    EmployeeDto(
                            id = id,
                            firstName = firstName,
                            lastName = lastName,
                            email = email,
                            role = role
                    )
                }

        fun toDomain(employeeDto: EmployeeDto): Employee =
                with(employeeDto) {
                    Employee(id = UUID.randomUUID().toString(),
                            firstName = firstName,
                            lastName = lastName,
                            email = email,
                            role = role)
                }
    }
}

