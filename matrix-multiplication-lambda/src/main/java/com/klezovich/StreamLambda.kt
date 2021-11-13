package com.klezovich

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import kotlin.Throws
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Named

@Named("stream")
class StreamLambda : RequestStreamHandler {
    @Throws(IOException::class)
    override fun handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context) {
        var letter: Int
        while (inputStream.read().also { letter = it } != -1) {
            val character: Int = letter.toInt()
            outputStream.write(character)
        }
    }
}
