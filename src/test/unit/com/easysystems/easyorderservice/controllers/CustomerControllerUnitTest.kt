package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.CustomerDTO
import com.easysystems.easyorderservice.entities.Customer
import com.easysystems.easyorderservice.entities.Tabletop
import com.easysystems.easyorderservice.repositories.CustomerRepository
import com.easysystems.easyorderservice.services.CustomerService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CustomerController::class])
@AutoConfigureWebTestClient
internal class CustomerControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var customerServiceMockk: CustomerService

    @Test
    fun createCustomer() {

        val customerDTO = CustomerDTO(1, name = "Jack")

        every { customerServiceMockk.createCustomer(any()) } returns (customerDTO)

        val result = webTestClient.post()
            .uri("/v1/customers")
            .bodyValue(customerDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CustomerDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            result!!.id != null
        }
    }

    @Test
    fun createCustomer_RuntimeException() {

        val customerDTO = CustomerDTO(1, name = "Jack")
        val message = "Test runtime error handler"

        every { customerServiceMockk.createCustomer(any()) } throws RuntimeException(message)

        val result = webTestClient.post()
            .uri("/v1/customers")
            .bodyValue(customerDTO)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(message, result)
    }

    @Test
    fun retrieveCustomerById() {

        val customer = CustomerDTO(1, "Tom")

        every { customerServiceMockk.retrieveCustomerById(any()) } returns (customer)

        val result = webTestClient.get()
            .uri("/v1/customers/{id}", customer.id)
            .exchange()
            .expectStatus().isOk
            .expectBody(CustomerDTO::class.java)
            .returnResult()
            .responseBody

        CustomerControllerIntgTest.logger.info("Test result: $result")
        Assertions.assertEquals(customer.name, result!!.name)
    }

    @Test
    fun retrieveAllCustomers() {

        val customers = ArrayList<CustomerDTO>()
        customers.addAll(
            listOf(
                CustomerDTO(1, "Tom"),
                CustomerDTO(2, "Jerry"),
                CustomerDTO(3, "Tweety")
            )
        )

        every { customerServiceMockk.retrieveAllCustomers() } returns (customers)

        val result = webTestClient.get()
            .uri("/v1/customers")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CustomerDTO::class.java)
            .returnResult()
            .responseBody

        CustomerControllerIntgTest.logger.info("Test result: $result")
        Assertions.assertEquals(3, result!!.size)
    }

    @Test
    fun updateCustomer() {

        val customerDTO = CustomerDTO(1, "Tom")

        every { customerServiceMockk.updateCustomer(any(), any()) } returns (customerDTO)

        val result = webTestClient.put()
            .uri("/v1/customers/{id}", 1)
            .bodyValue(customerDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CustomerDTO::class.java)
            .returnResult()
            .responseBody

        CustomerControllerIntgTest.logger.info("Test result: $result")
        Assertions.assertEquals(customerDTO.name, result!!.name)
    }

    @Test
    fun deleteCustomer() {

        every { customerServiceMockk.deleteCustomer(any()) } just runs

        webTestClient.delete()
            .uri("/v1/customers/{id}", 1)
            .exchange()
            .expectStatus().isNoContent
    }
}

//    @Test
//    fun addCustomerToTabletop() {
//
//        every { customerServiceMockk.addCustomerToTabletop(any(), any(), any()) } returns (true)
//
//        val result = webTestClient.put()
//            .uri("/v1/customers//{customerId}/{tabletopId}/{tabletopCode}", 1, 2, "Code2")
//            .exchange()
//            .expectStatus().isOk
//            .expectBody(Boolean::class.java)
//            .returnResult()
//            .responseBody
//
//        CustomerControllerIntgTest.logger.info("Test result: $result")
//        Assertions.assertEquals(true, result!!)
//    }