package com.klezovich

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.databind.ObjectMapper
import com.klezovich.input.InputObject
import com.klezovich.output.OutputObject
import io.quarkus.logging.Log
import org.eclipse.microprofile.config.inject.ConfigProperty
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import javax.inject.Inject
import javax.inject.Named

@Named("test")
class MatrixMultiplicationLambda : RequestHandler<InputObject?, OutputObject> {

    @Inject
    lateinit var matrixMultiplicationService: MatrixMultiplicationService

    @Inject
    lateinit var sqsClient: SqsClient

    @ConfigProperty(name = "queue.matrix-multiplication-result")
    lateinit var queueUrl: String

    val mapper = ObjectMapper()

    override fun handleRequest(input: InputObject?, context: Context): OutputObject {

        Log.info("Hello from lambda!")
        Log.info("Input is: ${mapper.writeValueAsString(input)}")

        val outputObject =
            OutputObject(requestId = input!!.records[0].messageId, resultMatrix = arrayOf(DoubleArray(1)))

        Log.info("Trying to send request to sqs queue $queueUrl")
        try {
            val message = sqsClient.sendMessage(
                SendMessageRequest.builder()
                    .messageBody(mapper.writeValueAsString(outputObject))
                    .queueUrl(queueUrl)
                    .build()
            )
            Log.info("Message sent with id $message")
        }catch (e:Exception) {
           Log.info("Failed to send message: $e.message")
        }

        return outputObject
//        Log.info("Request is: ${input!!.records[0].body}")
//
//        val m1 = input.records[0].body.matrix_1
//        val m2 = input.records[0].body.matrix_2
//
//        return OutputObject(
//            requestId = input.records[0].body.requestId,
//            resultMatrix = matrixMultiplicationService.multiply(m1, m2)
//        )
    }
}
