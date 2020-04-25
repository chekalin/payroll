package com.example.payroll

import com.example.payroll.domain.Employee
import com.example.payroll.domain.EmployeeRepository
import com.example.payroll.web.CreateEmployeeDto
import com.example.payroll.web.EmployeeDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import java.util.*
import kotlin.collections.ArrayList

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PayrollApplicationIT {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    @BeforeEach
    internal fun init() {
        employeeRepository.deleteAll()
    }

    private val testEmployee = Employee(
            id = UUID.randomUUID().toString(),
            firstName = "John",
            lastName = "McClane",
            email = "john@example.com",
            role = "DEV_OPS_ENGINEER"
    )

    @Test
    internal fun `returns existing employees`() {
        employeeRepository.saveAll(listOf(
                testEmployee.copy(id = UUID.randomUUID().toString()),
                testEmployee.copy(id = UUID.randomUUID().toString())
        ))

        val response = restTemplate.getForEntity<EmployeeList>("/employees/")

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).hasSize(2)
    }

    @Test
    internal fun `creates and gets employee`() {
        val newEmployee = CreateEmployeeDto(firstName = "Stas", lastName = "Chekalin", email = "stas@example.com", role = "SOFTWARE_ENGINEER")

        val response = restTemplate.postForEntity("/employees/", newEmployee, EmployeeDto::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        val createdEmployeeId = response.body!!.id
        assertThat(createdEmployeeId).isNotNull()

        val employee = restTemplate.getForEntity<EmployeeDto>("/employees/${createdEmployeeId}")

        assertThat(employee.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(employee.body).isNotNull()
        assertThat(employee.body!!.id).isEqualTo(createdEmployeeId)
    }

    @Test
    internal fun `returns 404 when employee data not found`() {
        val response = restTemplate.getForEntity<Any>("/employees/does-not-exist")

        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    class EmployeeList : MutableList<EmployeeDto> by ArrayList()

}
