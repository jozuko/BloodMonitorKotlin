package com.studio.jozu.bloodmonitor.service.aws

import android.content.Context
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.studio.jozu.bloodmonitor.domain.aws.Aws
import timber.log.Timber
import java.io.InputStream
import java.io.InputStreamReader

class AwsManager(aContext: Context) {

    lateinit var aws: Aws
        private set

    init {
        readSettings(aContext)
    }

    private fun readSettings(aContext: Context) {
        var jsonReader: JsonReader? = null
        try {
            val inputStream: InputStream = aContext.assets.open("aws.json")
            jsonReader = JsonReader(InputStreamReader(inputStream))
            aws = Gson().fromJson(jsonReader, Aws::class.java)

            Timber.d("aws=$aws")
        } finally {
            jsonReader?.close()
        }
    }

}