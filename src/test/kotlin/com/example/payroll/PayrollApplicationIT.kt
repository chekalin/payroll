package com.example.payroll

import com.example.payroll.domain.Employee
import com.example.payroll.domain.EmployeeRepository
import com.example.payroll.web.EmployeeDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import java.util.*
import kotlin.collections.ArrayList

@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
class PayrollApplicationIT {

	@Autowired lateinit var restTemplate: TestRestTemplate

	@Autowired lateinit var employeeRepository: EmployeeRepository

	@BeforeEach
	internal fun init() {
		employeeRepository.saveAll(listOf(
                Employee(UUID.randomUUID().toString(), "Stas", "Chekalin", "stas@example.com", "SOFTWARE_ENGINEER"),
                Employee(UUID.randomUUID().toString(), "John", "McClane", "john@example.com", "DEV_OPS_ENGINEER"))
        )
	}

	@Test
	fun returnsExistingEmployees() {
		val response = restTemplate.getForEntity("/employees/", EmployeeList::class.java)

		assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(response.body).hasSize(2)
	}

	class EmployeeList : MutableList<EmployeeDto> by ArrayList()

}
