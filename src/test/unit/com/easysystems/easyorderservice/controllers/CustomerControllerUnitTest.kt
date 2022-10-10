package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.Customer
import com.easysystems.easyorderservice.data.Table
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
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
    @Autowired
    lateinit var mapper: ObjectMapper
    @MockkBean
    lateinit var customerControllerMockk: CustomerController

    @Test
    fun createCustomerTest() {

        val name = "Tom"
        val expected = Customer(name = "Tom")

        every { customerControllerMockk.createCustomer(any()) } returns (expected)

        val result = webTestClient.get()
            .uri("/customers/create_customer/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        val responseAsObject = mapper.readValue(result.responseBody, Customer::class.java)

        Assertions.assertEquals(expected, responseAsObject)
    }

    @Test
    fun deleteCustomerTest() {

        val id = 1
        val expected = true

        every { customerControllerMockk.deleteCustomer(any()) } returns (expected)

        val result = webTestClient.get()
            .uri("/customers/delete_customer/{customerId}", id)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals(expected.toString(), result.responseBody)
    }

    @Test
    fun addCustomerToTableTest()
    {
        val code = "Code1"
        val table = Table(code = code)
        val customer = Customer(name = "Jerry")
        val expected = true

        every { customerControllerMockk.addCustomerToTable(any(),any(),any()) } returns (expected)

        val result = webTestClient.get()
            .uri("/customers/add_customer_to_table/{customerId}/{tableId}/{code}", customer.id, table.id, code)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals(expected.toString(), result.responseBody)
    }
}