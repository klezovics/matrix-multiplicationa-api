package com.klezovich.service.sqs

import com.fasterxml.jackson.databind.ObjectMapper
import com.klezovich.service.sqs.dto.SqsMultiplicationRequest
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.jboss.logging.Logger
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class SqsGateway {

    private val log: Logger = Logger.getLogger(this::class.java)

    @Inject
    lateinit var sqs: SqsClient

    @ConfigProperty(name = "queue.matrix-multiplication-request")
    lateinit var queueUrl: String

    val sqsWriter = ObjectMapper().writerFor(SqsMultiplicationRequest::class.java)

    fun sendToMultiplicationQueue(request: SqsMultiplicationRequest) {
        log.info("Sending request ${request.requestId} to queue")
        try {
            val request = toRequest(request)
            log.info("Request is $request")
            val response = sqs.sendMessage(request)
            log.info("SQS response id is ${response.messageId()}")
        } catch (e: Exception) {
            log.info("Exception $e ${e.message}")
        }
    }

    private fun toRequest(request: SqsMultiplicationRequest): SendMessageRequest {
        val builder = SendMessageRequest.builder()
            .queueUrl(queueUrl)
            .messageBody(sqsWriter.writeValueAsString(request))
        return builder.build()
    }
}
