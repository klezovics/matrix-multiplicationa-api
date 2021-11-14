package com.klezovich

import io.quarkus.logging.Log
import org.eclipse.microprofile.config.inject.ConfigProperty
import software.amazon.awssdk.services.sqs.SqsClient
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class SqsGateway {

    @Inject
    lateinit var sqs: SqsClient

    @ConfigProperty(name = "queue.matrix-multiplication-result")
    lateinit var queueUrl: String

    fun sendMessage(message: String) {
        Log.info("Trying to send request to sqs queue $queueUrl\n")
        try {
            sqs.sendMessage {
                it.messageBody(message)
                it.queueUrl(queueUrl)
            }
            Log.info("Message sent")
        } catch (t: Throwable) {
            Log.error("Failed to send message $t ${t.message} ${t.stackTrace}")
        }
    }
}
