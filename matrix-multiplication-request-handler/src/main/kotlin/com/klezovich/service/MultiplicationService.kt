package com.klezovich.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.klezovich.controller.dto.MultiplicationRequestDTO
import com.klezovich.entity.MultiplicationRequest
import com.klezovich.entity.RequestStatus
import com.klezovich.repository.MultiplicationRequestRepository
import com.klezovich.service.sqs.SqsGateway
import com.klezovich.service.sqs.dto.SqsMultiplicationRequest
import com.klezovich.service.sqs.dto.SqsMultiplicationResponse
import org.jboss.logging.Logger
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional

@ApplicationScoped
class MultiplicationService {

    private val log: Logger = Logger.getLogger(this::class.java)

    @Inject
    private lateinit var repository: MultiplicationRequestRepository

    @Inject
    private lateinit var sqsGateway: SqsGateway

    private val mapper = ObjectMapper()

    @Transactional
    fun processMultiplicationRequest(dto: MultiplicationRequestDTO): Long {

        var request = MultiplicationRequest()
        repository.persistAndFlush(request)
        var requestId = request.id!!
        sqsGateway.sendToMultiplicationQueue(toSqsRequest(dto, requestId))

        return requestId
    }

    @Transactional
    fun getMultiplicationResult(requestId: Long): MultiplicationRequest {
        return repository.findById(requestId)
    }

    fun toSqsRequest(dto: MultiplicationRequestDTO, requestId: Long): SqsMultiplicationRequest {
        return SqsMultiplicationRequest(
            requestId = requestId,
            matrix_1 = dto.matrix_1,
            matrix_2 = dto.matrix_2,
        )
    }

    @Transactional
    fun processMultiplicationResponse(response: SqsMultiplicationResponse): Boolean {
        var request = repository.findById(response.requestId)
        if (request == null) {
            log.error("No matching request for response with id ${response.requestId}")
            return true
        }

        request.status = RequestStatus.COMPLETE
        request.result = mapper.writeValueAsString(response.resultMatrix)
        repository.persist(request)
        log.info("Request ${request.id} proccessed. ${request.result}")
        return true
    }
}
