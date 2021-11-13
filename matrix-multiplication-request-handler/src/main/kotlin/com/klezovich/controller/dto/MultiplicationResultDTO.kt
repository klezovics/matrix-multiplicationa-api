package com.klezovich.controller.dto

import com.klezovich.entity.RequestStatus

data class MultiplicationResultDTO(
    val status: RequestStatus,
    val resultMatrix: String? = null
)
