package com.klezovich.service.sqs

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.klezovich.service.MultiplicationService
import com.klezovich.service.sqs.dto.SqsMultiplicationResponse
import io.quarkus.logging.Log
import io.quarkus.scheduler.Scheduled
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.jboss.logging.Logger
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest
import software.amazon.awssdk.services.sqs.model.Message
import software.amazon.awssdk.services.sqs.model.QueueAttributeName
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class SqsResultConsumer {

    private val log: Logger = Logger.getLogger(this::class.java)

    @ConfigProperty(name = "queue.matrix-multiplication-result")
    lateinit var queueUrl: String

    @Inject
    lateinit var sqs: SqsClient

    @Inject
    lateinit var multiplicationService: MultiplicationService

    val objectMapper: ObjectMapper = jacksonObjectMapper()

    @Scheduled(every = "1s")
    fun consume() {

        val request = ReceiveMessageRequest.builder()
            .maxNumberOfMessages(10)
            .attributeNames(QueueAttributeName.ALL)
            .queueUrl(queueUrl)
            .build()

        if (sqs.receiveMessage(request).hasMessages()) {
            log.info("Topic $queueUrl has messages")
        } else {
            // log.info("No messages in $queueUrl")
        }

        sqs.receiveMessage(request)
            .messages().forEach {
                log.info("Receiving messages")
                val success = handleMessage(message = it)
                handleResult(message = it, success = success)
            }
    }

    private fun handleMessage(message: Message?): Boolean {
        if (message == null) {
            Log.debug("Message is null")
            return true
        }

        val payload = message.body()
        val response = objectMapper.readValue(payload, SqsMultiplicationResponse::class.java)
        if (response == null) {
            Log.info("Response is null $response, payload $payload")
        }

        val processed = multiplicationService.processMultiplicationResponse(response)

        return processed
    }

    private fun handleResult(message: Message, success: Boolean) {
        if (success) {
            println("Handled OK. Deleting message with id ${message.messageId()} from SQS")
            val request = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build()
            sqs.deleteMessage(request)
        }
    }
}
