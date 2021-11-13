package com.klezovich.controller

import com.klezovich.controller.dto.MultiplicationRequestDTO
import com.klezovich.controller.dto.MultiplicationRequestResponseDTO
import com.klezovich.controller.dto.MultiplicationResultDTO
import com.klezovich.entity.MultiplicationRequest
import com.klezovich.entity.RequestStatus
import com.klezovich.service.MultiplicationService
import io.quarkus.logging.Log
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/multiply")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
class MultiplicationRequestController {

    @Inject
    lateinit var service: MultiplicationService

    @POST
    fun multiplyMatrices(
        payload: MultiplicationRequestDTO
    ): MultiplicationRequestResponseDTO {
        val request_id = service.processMultiplicationRequest(payload)
        return MultiplicationRequestResponseDTO(request_id)
    }

    @GET
    @Path("/{request_id}")
    fun getMultiplicationResult(
        @PathParam("request_id") requestId: Long
    ): MultiplicationResultDTO {
        Log.info("Request ID is $requestId")
        val request = service.getMultiplicationResult(requestId)
        return toDto(request)
    }

    private fun toDto(request: MultiplicationRequest): MultiplicationResultDTO {
        return MultiplicationResultDTO(
            status = request.status,
            resultMatrix = if (request.status == RequestStatus.COMPLETE) request.result else null
        )
    }
}
