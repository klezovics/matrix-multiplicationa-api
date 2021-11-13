package com.klezovich.controller

import com.klezovich.controller.dto.MultiplicationRequestDTO
import com.klezovich.service.MultiplicationService
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@QuarkusTest
internal class MultiplicationRequestControllerTest {

    @InjectMock
    private lateinit var multiplicationService: MultiplicationService

    @Test
    fun testValidRequestAccepted() {

        whenever(multiplicationService.processMultiplicationRequest(any()))
            .thenReturn(1L)

        given()
            .body(MultiplicationRequestDTO("[[1]]", "[[1]]"))
            .contentType(ContentType.JSON)
            .`when`()
            .post("/multiply")
            .then()
            .statusCode(200)
    }
}
