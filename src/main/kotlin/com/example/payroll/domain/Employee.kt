package com.example.payroll.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Employee(
        @Id val id: String,
        val firstName: String,
        val lastName: String,
        val email: String,
        val role: String
)
