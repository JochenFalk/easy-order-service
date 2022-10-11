package com.easysystems.easyorderservice

import com.easysystems.easyorderservice.data.*
import com.easysystems.easyorderservice.repositories.*
import mu.KLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EasyOrderServiceApplication

fun main(args: Array<String>) {
	runApplication<EasyOrderServiceApplication>(*args)

	val log = KLogging()

	val tabletopRepository = TabletopRepository()
	val itemRepository = ItemRepository()
	val userRepository = UserRepository()

	val dataHelper = DataHelper()

	val table1 = tabletopRepository.getById(1)!!

	val employee1 = Employee(name = "Jack", password = "password")
	val customer1 = Customer(name = "Tom")
	val customer2 = Customer(name = "Jerry")

	employee1.assignTabletopToEmployee(table1, "Jack", "password")
	customer1.addCustomerToTabletop(table1,"code1")
	customer2.addCustomerToTabletop(table1,"code1")

	itemRepository.getById(2)?.let { tabletopRepository.getById(customer1.tableId)?.addItemToTabletop(it, customer1) }
	itemRepository.getById(2)?.let { tabletopRepository.getById(customer1.tableId)?.addItemToTabletop(it, customer1) }
	itemRepository.getById(1)?.let { tabletopRepository.getById(customer1.tableId)?.addItemToTabletop(it, customer1) }

	log.logger.info("${customer1.name} orders: ${customer1.itemDTOs}")

	itemRepository.getById(1)?.let { tabletopRepository.getById(customer2.tableId)?.addItemToTabletop(it, customer2) }
	itemRepository.getById(4)?.let { tabletopRepository.getById(customer2.tableId)?.addItemToTabletop(it, customer2) }
	itemRepository.getById(3)?.let { tabletopRepository.getById(customer2.tableId)?.addItemToTabletop(it, customer2) }

	log.logger.info("${customer2.name} orders: ${customer2.itemDTOs}")

	log.logger.info("Table ID: ${mainTabletopListDTO[0].id}, User count: ${mainTabletopListDTO[0].customers.size}")

	for (user in mainUserListDTO) {
		when (user) {
			user as? Employee -> {
				log.logger.info("User ${user.name} is an employee")
			}

			user as? Customer -> {
				log.logger.info("User ${user.name} is an customer")
			}
		}
	}

	val customers = mainUserListDTO.filterIsInstance<Customer>()

	for (c in customers) {
		log.logger.info(
			"Main courses ordered by ${c.name}: " +
					"${dataHelper.filterItemsByCategory(c.itemDTOs, ItemDTO.Category.MAIN)}"
		)
	}

	log.logger.info("Total for table ${table1.id}: ${tabletopRepository.getTotal(table1)}")
	log.logger.info("Total for ${customer1.name}: ${userRepository.getTotalFromCustomer(customer1)}")
	log.logger.info("Total for ${customer2.name}: ${userRepository.getTotalFromCustomer(customer2)}")
}
