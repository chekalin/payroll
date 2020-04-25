package com.example.payroll.web

import com.example.payroll.domain.Employee

data class EmployeeDto(
        var id: String? = null,
        val name: String,
        val email: String,
        val role: String) {

    companion object {
        fun fromDomain(employee: Employee): EmployeeDto =
                with(employee) {
                    EmployeeDto(
                            id = id,
                            name = "$firstName $lastName",
                            email = email,
                            role = role
                    )
                }
    }
}

