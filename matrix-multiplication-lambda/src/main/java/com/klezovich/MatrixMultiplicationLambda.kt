package com.klezovich

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.klezovich.input.InputObject
import com.klezovich.output.OutputObject
import org.eclipse.microprofile.config.inject.ConfigProperty
import software.amazon.awssdk.services.sqs.SqsClient
import javax.inject.Inject
import javax.inject.Named

@Named("test")
class MatrixMultiplicationLambda : RequestHandler<InputObject?, OutputObject> {

    @Inject
    lateinit var matrixMultiplicationService: MatrixMultiplicationService

//    var sqsClient = SqsClient.builder().region(Region.EU_CENTRAL_1)
//        .httpClient(software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient.builder().build()).build()

    @Inject
    lateinit var sqs: SqsClient

    @ConfigProperty(name = "queue.matrix-multiplication-result")
    lateinit var queueUrl: String

    val mapper = ObjectMapper()

    var gson: Gson = GsonBuilder().setPrettyPrinting().create()

    override fun handleRequest(input: InputObject?, context: Context): OutputObject {

        val logger = context.logger
        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()) + "\n")
        logger.log("CONTEXT: " + gson.toJson(context) + "\n")
        // process event

        logger.log("Hello from lambda!\n")
        logger.log("Input is: ${mapper.writeValueAsString(input)}\n")

        val body = input!!.records[0].body

        val m1 = body.matrix_1
        val m2 = body.matrix_2

        logger.log("m1 $m1 and m2 $m2!\n")

        val outputObject =
            OutputObject(
                requestId = body.requestId,
                resultMatrix = matrixMultiplicationService.multiply(m1, m2)
            )

        val outputString = mapper.writeValueAsString(outputObject)
        logger.log("Output string is $outputString\n")
        logger.log("Trying to send request to sqs queue $queueUrl\n")
        try {
//            val message = sqsClient.sendMessage(
//                SendMessageRequest.builder()
//                    .messageBody(outputString)
//                    .queueUrl(queueUrl)
//                    .build()
//            )
            // Log.info("Message sent with id $message")
            // sqsClient.listQueues().queueUrls().forEach { Log.info("Q $it") }
            logger.log("Let's say the message is sent\n")
        } catch (e: Throwable) {
            logger.log("Failed to send message: $e.message\n")
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
