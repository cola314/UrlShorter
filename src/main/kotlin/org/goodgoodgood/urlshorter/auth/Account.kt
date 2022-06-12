package org.goodgoodgood.urlshorter.auth

import org.goodgoodgood.urlshorter.common.entity.TimestampedEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Account(
    username: String,
    email: String,
    password: String,
) : TimestampedEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var username: String = username
        private set

    @Column(nullable = false, unique = true)
    var email: String = email
        private set

    @Column(nullable = false)
    var password: String = password
        private set
}