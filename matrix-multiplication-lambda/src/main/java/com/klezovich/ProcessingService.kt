package com.klezovich

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ProcessingService {
    fun process(input: InputObject): OutputObject {
        require(input.name != "Stuart") { CAN_ONLY_GREET_NICKNAMES }
        val result = input.greeting + " " + input.name
        val out = OutputObject()
        out.setResult(result)
        return out
    }

    companion object {
        const val CAN_ONLY_GREET_NICKNAMES = "Can only greet nicknames"
    }
}
