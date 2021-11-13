package com.klezovich

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import javax.inject.Inject
import javax.inject.Named

@Named("test")
class TestLambda : RequestHandler<InputObject?, OutputObject> {
    @Inject
    var service: ProcessingService? = null
    override fun handleRequest(input: InputObject?, context: Context): OutputObject {
        return service!!.process(input!!).setRequestId(context.awsRequestId)
    }
}
