package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.CustomerDTO
import com.easysystems.easyorderservice.entities.Customer
import com.easysystems.easyorderservice.entities.Tabletop
import com.easysystems.easyorderservice.repositories.CustomerRepository
import com.easysystems.easyorderservice.repositories.TabletopRepository
import mu.KLogging
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
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

    companion object : KLogging()

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setUp() {
        customerRepository.deleteAll()
    }

    @Test
    fun createCustomer() {

        val customerDTO = CustomerDTO(null, name = "Tom")

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
    fun retrieveCustomerById() {

        val customer = Customer(null, "Tom").apply {
            customerRepository.save(this)
        }

        val result = webTestClient.get()
            .uri("/v1/customers/{id}", customer.id)
            .exchange()
            .expectStatus().isOk
            .expectBody(CustomerDTO::class.java)
            .returnResult()
            .responseBody

        logger.info("Test result: $result")
        Assertions.assertEquals(customer.name, result!!.name)
    }

    @Test
    fun retrieveAllCustomers() {

        val customers = listOf(
            Customer(null, "Tom"),
            Customer(null, "Jerry"),
            Customer(null, "Tweety")
        )
        customerRepository.saveAll(customers)

        val result = webTestClient.get()
            .uri("/v1/customers")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CustomerDTO::class.java)
            .returnResult()
            .responseBody

        logger.info("Test result: $result")
        Assertions.assertEquals(3, result!!.size)
    }

    @Test
    fun updateCustomer() {

        val customer = Customer(null, "Tom").apply {
            customerRepository.save(this)
        }
        val updatedCustomerDTO = CustomerDTO(1, "Jerry")

        val result = webTestClient.put()
            .uri("/v1/customers/{id}", customer.id)
            .bodyValue(updatedCustomerDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CustomerDTO::class.java)
            .returnResult()
            .responseBody

        logger.info("Test result: $result")
        Assertions.assertEquals(updatedCustomerDTO.name, result!!.name)
    }

    @Test
    fun deleteCustomer() {

        val customer = Customer(1, "Tom").apply {
            customerRepository.save(this)
        }

        webTestClient.delete()
            .uri("/v1/customers/{id}", customer.id)
            .exchange()
            .expectStatus().isNoContent

        val result = webTestClient.get()
            .uri("/v1/customers")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CustomerDTO::class.java)
            .returnResult()
            .responseBody

        logger.info("Test result: $result")
        Assertions.assertEquals(0, result!!.size)
    }

//    @Test
//    fun addCustomerToTabletop() {
//
//        val customer = Customer(null, "Tom", 0, ArrayList()).apply {
//            customerRepository.save(this)
//        }
//        val tabletop = Tabletop(null, "Code1", arrayListOf(1,1,1)).apply {
//            tabletopRepository.save(this)
//        }
//
//        val result = webTestClient.put()
//            .uri("/v1/customers//{customerId}/{tabletopId}/{tabletopCode}", customer.id, tabletop.id, tabletop.authCode)
//            .exchange()
//            .expectStatus().isOk
//            .expectBody(Boolean::class.java)
//            .returnResult()
//            .responseBody
//
//        logger.info("Test result: $result")
//        Assertions.assertEquals(true, result!!)
//    }
}