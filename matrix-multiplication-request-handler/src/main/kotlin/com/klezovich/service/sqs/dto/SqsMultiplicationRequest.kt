package com.klezovich.service.sqs.dto

class SqsMultiplicationRequest(
    val requestId: Long,
    val matrix_1: String,
    val matrix_2: String
)
