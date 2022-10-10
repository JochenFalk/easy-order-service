package com.easysystems.easyorderservice

import com.easysystems.easyorderservice.data.*
import mu.KLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EasyOrderServiceApplication

fun main(args: Array<String>) {
	runApplication<EasyOrderServiceApplication>(*args)

	val log = KLogging()

	val tableRepository = TableRepository()
	val itemRepository = ItemRepository()
	val userRepository = UserRepository()

	val dataHelper = DataHelper()

	val table1 = tableRepository.getById(1)!!

	val employee1 = Employee(name = "Jack", password = "password")
	val customer1 = Customer(name = "Tom")
	val customer2 = Customer(name = "Jerry")

	employee1.assignTableToEmployee(table1, "Jack", "password")
	customer1.addCustomerToTable(table1,"code1")
	customer2.addCustomerToTable(table1,"code1")

	itemRepository.getById(2)?.let { tableRepository.getById(customer1.tableId)?.addItemToTable(it, customer1) }
	itemRepository.getById(2)?.let { tableRepository.getById(customer1.tableId)?.addItemToTable(it, customer1) }
	itemRepository.getById(1)?.let { tableRepository.getById(customer1.tableId)?.addItemToTable(it, customer1) }

	log.logger.info("${customer1.name} orders: ${customer1.items}")

	itemRepository.getById(1)?.let { tableRepository.getById(customer2.tableId)?.addItemToTable(it, customer2) }
	itemRepository.getById(4)?.let { tableRepository.getById(customer2.tableId)?.addItemToTable(it, customer2) }
	itemRepository.getById(3)?.let { tableRepository.getById(customer2.tableId)?.addItemToTable(it, customer2) }

	log.logger.info("${customer2.name} orders: ${customer2.items}")

	log.logger.info("Table ID: ${mainTableList[0].id}, User count: ${mainTableList[0].customerList.size}")

	for (user in mainUserList) {
		when (user) {
			user as? Employee -> {
				log.logger.info("User ${user.name} is an employee")
			}

			user as? Customer -> {
				log.logger.info("User ${user.name} is an customer")
			}
		}
	}

	val customers = mainUserList.filterIsInstance<Customer>()

	for (c in customers) {
		log.logger.info(
			"Main courses ordered by ${c.name}: " +
					"${dataHelper.filterItemsByCategory(c.items, Item.Category.MAIN)}"
		)
	}

	log.logger.info("Total for table ${table1.id}: ${tableRepository.getTotal(table1)}")
	log.logger.info("Total for ${customer1.name}: ${userRepository.getTotalFromCustomer(customer1)}")
	log.logger.info("Total for ${customer2.name}: ${userRepository.getTotalFromCustomer(customer2)}")
}
