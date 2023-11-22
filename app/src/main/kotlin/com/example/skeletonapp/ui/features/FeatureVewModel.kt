package com.example.skeletonapp.ui.features

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLException


class FeatureVewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Home Fragment"
    }
    val text: LiveData<String> = _text

    fun testNetworking() {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {

                val result =
                    getUrlContentAsString("https://flimp.me/decision_support-Insured_Members?em=Y")

                Log.d("TAG", "RESULT: $result")
            }

        }
    }

    private fun getUrlContentAsString(urlString: String): String {
        val url = URL(urlString)

//        val proxy = Proxy(
//            Proxy.Type.HTTP,
//            InetSocketAddress("192.168.171.86", 8888)
//        )

        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        // HTTP GET request sometimes returns error 404 along with the content
        @Suppress("SwallowedException")
        val inputStream = try {
            connection.inputStream
        } catch (e: FileNotFoundException) {
            connection.errorStream
        } catch(e: SSLException) {
            return ""
        }
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()

        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }

        bufferedReader.close()
        inputStream.close()
        connection.disconnect()

        return stringBuilder.toString()
    }

}