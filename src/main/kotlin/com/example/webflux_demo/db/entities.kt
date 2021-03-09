package com.example.webflux_demo.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("person")
data class PersonEntity(
    val name: String,
    val age: Int,
    @Id val id: Long? = null,
) {
    override fun equals(other: Any?): Boolean {
        val otherCustomer = other as? PersonEntity ?: return false
        return id!=null && id==otherCustomer.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}