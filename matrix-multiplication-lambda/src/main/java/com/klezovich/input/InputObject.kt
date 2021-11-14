package com.klezovich.input

import com.fasterxml.jackson.annotation.JsonProperty

class InputObject {
    @JsonProperty("Records")
    lateinit var records: List<SqsMessageRecord>
}
