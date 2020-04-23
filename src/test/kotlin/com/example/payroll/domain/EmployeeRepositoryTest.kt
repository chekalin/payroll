package com.example.payroll.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.util.*


@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired lateinit var employeeRepository: EmployeeRepository

    @Autowired lateinit var entityManager: TestEntityManager

    @Test
    internal fun `saves and loads employee`() {
        val id = UUID.randomUUID().toString()
        val testEmployee = Employee(
                id = id,
                firstName = "Stas",
                lastName = "Chekalin",
                email = "admin@example.com",
                role = "ENGINEER")

        employeeRepository.save(testEmployee)
        entityManager.flush()

        val saved = employeeRepository.findById(id)
        assertThat(saved).isPresent
        val savedEmployee = saved.get()
        assertThat(savedEmployee.id).isEqualTo(id)
        assertThat(savedEmployee.firstName).isEqualTo(testEmployee.firstName)
        assertThat(savedEmployee.lastName).isEqualTo(testEmployee.lastName)
        assertThat(savedEmployee.email).isEqualTo(testEmployee.email)
        assertThat(savedEmployee.role).isEqualTo(testEmployee.role)
    }
}