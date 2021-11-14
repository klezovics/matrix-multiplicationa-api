package com.klezovich

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.databind.ObjectMapper
import org.eclipse.microprofile.config.inject.ConfigProperty
import software.amazon.awssdk.services.sqs.SqsClient
import javax.inject.Inject
import javax.inject.Named

@Named("writer")
class QueueWriterLambda : RequestHandler<Map<String, String>, String> {

    @Inject
    lateinit var sqs: SqsClient

    @ConfigProperty(name = "queue.matrix-multiplication-result")
    lateinit var queueUrl: String

    val mapper = ObjectMapper()

    override fun handleRequest(event: Map<String, String>, context: Context): String {

        val log = context.logger
        log.log("##START to send message to queue\n")
        log.log("Event is ${mapper.writeValueAsString(event)}\n")
        log.log("Sending message in 1-2-3\n")

        log.log("##END\n")
        return "Done"
    }
}
