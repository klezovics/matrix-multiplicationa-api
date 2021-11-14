package com.klezovich

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.klezovich.input.InputObject
import com.klezovich.output.OutputObject
import io.quarkus.logging.Log
import javax.inject.Inject
import javax.inject.Named

@Named("test")
class MatrixMultiplicationLambda : RequestHandler<InputObject?, OutputObject> {

    @Inject
    lateinit var matrixMultiplicationService: MatrixMultiplicationService

    override fun handleRequest(input: InputObject?, context: Context): OutputObject {

        Log.info("Hello from lambda!")
        Log.info("Input is: $input")
        Log.info("Request is: ${input!!.records[0].body}")

        val m1 = input.records[0].body.matrix_1
        val m2 = input.records[0].body.matrix_2

        return OutputObject(
            requestId = input.records[0].body.requestId,
            resultMatrix = matrixMultiplicationService.multiply(m1, m2)
        )
    }
}
