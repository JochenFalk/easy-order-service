package com.easysystems.easyorderservice.data

import kotlin.collections.ArrayList

data class TabletopDTO(
    var id: Int?=0,
    var code: String,
    var orders: ArrayList<Int> = ArrayList()
) {



}

