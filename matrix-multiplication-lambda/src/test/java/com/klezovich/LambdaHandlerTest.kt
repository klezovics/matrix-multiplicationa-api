package com.klezovich

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.klezovich.input.InputObject
import com.klezovich.output.OutputObject
import com.nhaarman.mockito_kotlin.mock
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
open class LambdaHandlerTest {

    @Inject
    lateinit var handler: RequestHandler<InputObject?, OutputObject>

    @Test
    fun basicHandlerTest() {
        val mockContext = mock<Context>()

        val io = InputObject()
        val oo = handler.handleRequest(io,mockContext)

    }

    //
    //    @Test
    //    public void testSimpleLambdaSuccess() throws Exception {
    //        // you test your lambas by invoking on http://localhost:8081
    //        // this works in dev mode too
    //
    //        InputObject in = new InputObject();
    //        in.setName("Stu");
    //        in.setGreeting("Hello");
    //        given()
    //                .contentType("application/json")
    //                .accept("application/json")
    //                .body(in)
    //                .when()
    //                .post()
    //                .then()
    //                .statusCode(200)
    //                .body(containsString("Hello Stu"));
    //    }
}
