package com.klezovich

class InputObject {

    var name: String? = null
        private set
    var greeting: String? = null
        private set

    fun setName(name: String?): InputObject {
        this.name = name
        return this
    }

    fun setGreeting(greeting: String?): InputObject {
        this.greeting = greeting
        return this
    }
}
