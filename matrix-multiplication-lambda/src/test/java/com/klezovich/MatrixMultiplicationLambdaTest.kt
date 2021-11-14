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
        val json = """
            {
              "Records": [
                {
                  "messageId": "19dd0b57-b21e-4ac1-bd88-01bbb068cb78",
                  "receiptHandle": "MessageReceiptHandle",
                  "body": {
                    "requestId": 98,
                    "matrix_1": [[2,0],[0,2]],
                    "matrix_2": [[1,0],[0,1]]
                  },
                  "attributes": {
                    "ApproximateReceiveCount": "1",
                    "SentTimestamp": "1523232000000",
                    "SenderId": "123456789012",
                    "ApproximateFirstReceiveTimestamp": "1523232000001"
                  },
                  "messageAttributes": {},
                  "md5OfBody": "{{{md5_of_body}}}",
                  "eventSource": "aws:sqs",
                  "eventSourceARN": "arn:aws:sqs:us-east-1:123456789012:MyQueue",
                  "awsRegion": "us-east-1"
                }
              ]
            }
        """.trimIndent()

        var response = given()
            .contentType("application/json")
            .accept("application/json")
            .body(json)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()

        print("${response.body().asString()}")
    }
}
