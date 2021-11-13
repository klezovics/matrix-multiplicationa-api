package com.klezovich

class OutputObject {
    var result: String? = null
        private set
    var requestId: String? = null
        private set

    fun setResult(result: String?): OutputObject {
        this.result = result
        return this
    }

    fun setRequestId(requestId: String?): OutputObject {
        this.requestId = requestId
        return this
    }
}
