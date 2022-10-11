package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.Customer
import com.easysystems.easyorderservice.data.TabletopDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CustomerControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient
    @Autowired
    lateinit var mapper: ObjectMapper

    @Test
    fun createCustomerTest() {

        // For this test id of expected object should be 2 as the object returned by the test is the 2nd object created

        val name = "Tom"
        val expected = Customer(id = 2, name = name)

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

        val id = 10
        val customer = Customer(id = id, name = "Jerry") // Create object to delete
        val expected = "true"

        val result = webTestClient.get()
            .uri("/customers/delete_customer/{customerId}", id)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals(expected, result.responseBody)
    }

    @Test
    fun addCustomerToTableTest()
    {
        val code = "Code1"
        val tableTopDTO = TabletopDTO(code = code)
        val customer = Customer(name = "Jerry")
        val expected = "true"

        val result = webTestClient.get()
            .uri("/customers/add_customer_to_table/{customerId}/{tableId}/{code}", customer.id, tableTopDTO.id, code)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals(expected, result.responseBody)
    }
}