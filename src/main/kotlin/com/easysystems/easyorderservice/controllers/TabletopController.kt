package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.TabletopDTO
import com.easysystems.easyorderservice.services.TabletopService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/tabletops")
@Validated
class TabletopController(val tabletopService: TabletopService) {

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    fun createTabletop(@RequestBody @Valid tabletopDTO: TabletopDTO) : TabletopDTO {
//        return tabletopService.createTabletop(tabletopDTO)
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTabletop(@RequestBody @Valid tabletopList: ArrayList<TabletopDTO>) : ArrayList<TabletopDTO> {
        return tabletopService.createTabletopsByList(tabletopList)
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
    fun updateTabletop(@RequestBody @Valid tabletopDTO: TabletopDTO, @PathVariable("id") id: Int): TabletopDTO {
        return tabletopService.updateTabletop(id, tabletopDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTabletop(@PathVariable("id") id: Int) {
        return tabletopService.deleteTabletop(id)
    }
}