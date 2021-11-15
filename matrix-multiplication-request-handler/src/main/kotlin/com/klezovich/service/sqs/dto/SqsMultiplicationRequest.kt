package com.klezovich.service.sqs.dto

class SqsMultiplicationRequest(
    val requestId: Long,
    val matrix_1: Array<DoubleArray>,
    val matrix_2: Array<DoubleArray>
)
