package com.klezovich.repository

import com.klezovich.entity.MultiplicationRequest
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class MultiplicationRequestRepository : PanacheRepository<MultiplicationRequest>
