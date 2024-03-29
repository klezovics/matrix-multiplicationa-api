package com.klezovich.input

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class InputObjectTest {
    val mapper = ObjectMapper()

    @BeforeEach
    fun setup() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Test
    fun testDeserealizeRequest() {
        val json = """{"requestId":18,"matrix_1":[[1,0],[0,1]],"matrix_2":[[4,0],[0,4]]}""".trimIndent()

        val request = mapper.readValue(json, MultiplyRequest::class.java)
        assertEquals(18L, request.requestId)
    }

    @Test
    fun testDeserialise() {
        val json = """
            {
              "Records": [
                {
                  "messageId": "19dd0b57-b21e-4ac1-bd88-01bbb068cb78",
                  "receiptHandle": "MessageReceiptHandle",
                  "body": "{ \"requestId\": 99,\"matrix_1\": [[2,0],[0,2]],\"matrix_2\": [[1,0],[0,1]]}",
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

        val io = mapper.readValue(json, InputObject::class.java)

        var recordBody = io.records[0].body
        val request = mapper.readValue(recordBody, MultiplyRequest::class.java)
        assertEquals(99, request.requestId)
    }
}
