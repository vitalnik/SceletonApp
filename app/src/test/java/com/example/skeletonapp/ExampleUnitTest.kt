package com.example.skeletonapp

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

enum class YesNoValueDto {
    @SerializedName(value = "YES", alternate = ["Yes", "yes"])
    Yes,

    @SerializedName(value = "NO", alternate = ["No", "no"])
    No;
}

data class TestDto(
    val value1: YesNoValueDto,
    val value2: YesNoValueDto,
    val value3: YesNoValueDto,
    val value4: YesNoValueDto
)

class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testDto() {

        val jsonString = "{\"value1\":\"Yes\",\"value2\":\"No\",\"value3\":\"yes\",\"value4\":\"NO\"}"


        val testDto = Gson().fromJson(
            jsonString, TestDto::class.java
        )

        val testDtoString = Gson().toJson(testDto)

        assertEquals( "{\"value1\":\"YES\",\"value2\":\"NO\",\"value3\":\"YES\",\"value4\":\"NO\"}", testDtoString)

    }


}