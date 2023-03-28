package com.example.skeletonapp.data

import kotlinx.coroutines.delay
import javax.inject.Inject

data class DataItem(val id: Int, val values: List<String>)

class DataProvider @Inject constructor() {

    suspend fun getData(): List<DataItem> {
        delay(100)
        val list = mutableListOf<DataItem>()
        repeat(10) { i ->
            val subList = mutableListOf<String>()
            repeat(10) { j ->
                subList.add("Item ${j + (i * 10)}")
            }
            list.add(DataItem(i, subList))
        }
        return list
    }

}