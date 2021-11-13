package com.klezovich.repository

import com.klezovich.entity.MultiplicationRequest
import com.klezovich.entity.RequestStatus
import io.quarkus.test.TestTransaction
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
@TestTransaction
internal class MultiplicationRequestRepositoryTest {

    @Inject
    lateinit var repository: MultiplicationRequestRepository

    @Test
    fun testCanSaveAndLoadRequest() {
        val request = MultiplicationRequest()
        repository.persistAndFlush(request)

        val requestFromDb = repository.findById(request.id)
        assertNotNull(requestFromDb.id)
        assertEquals(RequestStatus.ACCEPTED, requestFromDb.status)
    }
}
