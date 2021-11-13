package com.klezovich.controller

import com.klezovich.controller.dto.MultiplicationRequestDTO
import com.klezovich.controller.dto.MultiplicationRequestResponseDTO
import com.klezovich.service.MultiplicationService
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
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
}
