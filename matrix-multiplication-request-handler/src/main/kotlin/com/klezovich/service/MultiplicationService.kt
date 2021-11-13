package com.klezovich.service

import com.klezovich.controller.dto.MultiplicationRequestDTO
import com.klezovich.entity.MultiplicationRequest
import com.klezovich.repository.MultiplicationRequestRepository
import com.klezovich.service.sqs.SqsGateway
import com.klezovich.service.sqs.dto.SqsMultiplicationRequest
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional

@ApplicationScoped
class MultiplicationService {

    @Inject
    private lateinit var repository: MultiplicationRequestRepository

    @Inject
    private lateinit var sqsGateway: SqsGateway

    @Transactional
    fun processMultiplicationRequest(dto: MultiplicationRequestDTO): Long {

        var request = MultiplicationRequest()
        repository.persistAndFlush(request)
        var requestId = request.id!!
        sqsGateway.sendToMultiplicationQueue(toSqsRequest(dto, requestId))

        return requestId
    }

    fun toSqsRequest(dto: MultiplicationRequestDTO, requestId: Long): SqsMultiplicationRequest {
        return SqsMultiplicationRequest(
            requestId = requestId,
            matrix_1 = dto.matrix_1,
            matrix_2 = dto.matrix_2,
        )
    }
}
