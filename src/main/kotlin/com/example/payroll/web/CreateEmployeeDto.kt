package com.example.payroll.web

data class CreateEmployeeDto(
        val firstName: String,
        val lastName: String,
        val email: String,
        val role: String)
