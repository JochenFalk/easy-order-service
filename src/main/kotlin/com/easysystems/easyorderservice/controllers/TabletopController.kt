package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.CustomerDTO
import com.easysystems.easyorderservice.data.TabletopDTO
import com.easysystems.easyorderservice.services.TabletopService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/tabletops")
class TabletopController(val tabletopService: TabletopService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTabletop(@RequestBody tabletopDTO: TabletopDTO) : TabletopDTO {
        return tabletopService.createTabletop(tabletopDTO)
    }

    @GetMapping("/{id}")
    fun retrieveTabletopById(@PathVariable("id") id: Int): TabletopDTO {
        return tabletopService.retrieveTabletopById(id)
    }

    @GetMapping
    fun retrieveAllTabletops() : ArrayList<TabletopDTO> {
        return tabletopService.retrieveAllTabletops()
    }

    @PutMapping("/{id}")
    fun updateTabletop(@RequestBody tabletopDTO: TabletopDTO, @PathVariable("id") id: Int): TabletopDTO {
        return tabletopService.updateTabletop(id, tabletopDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTabletop(@PathVariable("id") id: Int) {
        return tabletopService.deleteTabletop(id)
    }

    @PutMapping("/{tabletopId}/{customerId}")
    fun addOrderToTabletop(@RequestBody orderList: List<Int>,
                           @PathVariable tabletopId: Int,
                           @PathVariable customerId: Int
    ): Boolean {
        return tabletopService.addOrderToTabletop(tabletopId, customerId, orderList)
    }
}