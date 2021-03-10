package com.example.webflux_demo.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

/**
 * the reason [DbEntity] is an interface with default/extension methods instead of an abstract class
 * which overrides [equals] and [hashCode] is that this way the entity class itself can
 * still be a data class. Notice that this data class has to override [equals] and [hashCode]
 * but the implementation never changes (so no risk to forget something when copying code)
 * but simply delegates to the DbEntity (extended) versions of those methods.
 */
interface DbEntity<Key:Any> {
    val idOrNull: Key?
    val id: Key get() = idOrNull ?: error("primary key was expected to not be null")
}
private val DbEntity<*>.dbEntityHashCode: Int get() = idOrNull.hashCode()
private inline fun <reified T:DbEntity<Key>, Key> T.dbEntityEquals(other: Any?): Boolean =
    this === other || (other is T && idOrNull?.let { it==other.idOrNull } ?: false)


@Table("person")
data class PersonEntity(
    val name: String,
    val age: Int,
    @Id override val idOrNull: Long? = null,
): DbEntity<Long> {
    override fun equals(other: Any?) = dbEntityEquals(other)
    override fun hashCode() = dbEntityHashCode
}
