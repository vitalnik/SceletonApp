package com.example.skeletonapp.data

import kotlinx.coroutines.delay
import javax.inject.Inject

data class DataItem(val id: Int, val values: List<String>)

class DataProvider @Inject constructor() {

    suspend fun getData(): List<DataItem> {
        delay(1000)
        val list = mutableListOf<DataItem>()
        repeat(3) { i ->
            val subList = mutableListOf<String>()
            repeat(3) { j ->
                subList.add("Item ${j + (i * 3)}")
            }
            list.add(DataItem(i, subList))
        }
        return list
    }

}