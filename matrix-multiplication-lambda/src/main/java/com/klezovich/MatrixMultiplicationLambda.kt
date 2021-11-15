package com.klezovich

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.klezovich.input.InputObject
import com.klezovich.input.MultiplyRequest
import com.klezovich.output.OutputObject
import javax.inject.Inject
import javax.inject.Named

@Named("test")
class MatrixMultiplicationLambda : RequestHandler<InputObject?, OutputObject> {

    @Inject
    lateinit var matrixMultiplicationService: MatrixMultiplicationService

    @Inject
    lateinit var sqsGateway: SqsGateway

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
        val multiplyRequest = mapper.readValue(body, MultiplyRequest::class.java)

        val m1 = multiplyRequest.matrix_1
        val m2 = multiplyRequest.matrix_2

        logger.log("m1 ${mapper.writeValueAsString(m1)} and m2 ${mapper.writeValueAsString(m2)}!\n")

        val outputObject =
            OutputObject(
                requestId = multiplyRequest.requestId,
                resultMatrix = matrixMultiplicationService.multiply(m1, m2)
            )

        val outputString = mapper.writeValueAsString(outputObject)
        logger.log("Output string is $outputString\n")

        sqsGateway.sendMessage(outputString)

        return outputObject
    }
}
