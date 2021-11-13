package com.klezovich.entity

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
open class MultiplicationRequest(
    @Enumerated(EnumType.STRING)
    open var status: RequestStatus = RequestStatus.ACCEPTED
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = 0
    open var result: String? = null
}
