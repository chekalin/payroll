package com.example.payroll.web

import com.example.payroll.domain.Employee
import com.example.payroll.service.EmployeeService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class EmployeeControllerTest {

    @InjectMocks
    lateinit var employeeController: EmployeeController

    @Mock
    lateinit var employeeService: EmployeeService

    val testEmployee = Employee(
            id = UUID.randomUUID().toString(),
            firstName = "Stas",
            lastName = "Chekalin",
            email = "admin@example.com",
            role = "ENGINEER")

    @Test
    internal fun `returns employees from employee service`() {
        val employee1 = testEmployee.copy(id = UUID.randomUUID().toString())
        val employee2 = testEmployee.copy(id = UUID.randomUUID().toString())
        given(employeeService.getEmployees()).willReturn(listOf(
                employee1, employee2
        ))

        val employees = employeeController.getEmployees()

        assertThat(employees).hasSize(2)
        assertThat(employees[0].id).isEqualTo(employee1.id)
        assertThat(employees[1].id).isEqualTo(employee2.id)
    }

    @Test
    internal fun `creates employee in employee service`() {
        val newEmployee = CreateEmployeeDto(firstName = "Stas", lastName = "Chekalin", email = "stas@example.com", role = "SOFTWARE_ENGINEER")
        val expectedCreatedEmployee = Employee(id = UUID.randomUUID().toString(), firstName = "Stas", lastName = "Chekalin", email = "stas@example.com", role = "SOFTWARE_ENGINEER")

        given(employeeService.createEmployee(any())).willReturn(expectedCreatedEmployee)

        val createdEmployee = employeeController.createEmployee(newEmployee)

        assertThat(createdEmployee.id).isEqualTo(expectedCreatedEmployee.id)
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> uninitialized(): T = null as T
}