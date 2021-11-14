package com.klezovich

import com.klezovich.input.InputObject
import com.klezovich.input.MultiplyRequest
import com.klezovich.input.SqsMessageRecord
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test

internal class MatrixMultiplicationLambdaTest

@QuarkusTest
class LambdaHandlerTest {

    @Test
    fun testSimpleLambdaSuccess() {
        val input = InputObject()

        val body = MultiplyRequest()
        body.requestId = 1
        body.matrix_1 = arrayOf(doubleArrayOf(1.0, 0.0), doubleArrayOf(1.0, 0.0))
        body.matrix_2 = arrayOf(doubleArrayOf(1.0, 0.0), doubleArrayOf(1.0, 0.0))

        var record = SqsMessageRecord()
        record.messageId = "adhsjd"
        record.body = body

        input.records = listOf(record)

        var response = given()
            .contentType("application/json")
            .accept("application/json")
            .body(input)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()

        print("${response.body().asString()}")
    }
}
