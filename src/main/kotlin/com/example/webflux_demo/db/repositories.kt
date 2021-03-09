package com.example.webflux_demo.db

import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PersonRepository : CoroutineCrudRepository<PersonEntity, Long> {
    // SELECT * FROM person WHERE name LIKE '%tom%'
    suspend fun findByNameContains(name: String): Flow<PersonEntity>

    // SELECT * FROM person WHERE age >= 42
    suspend fun findByAgeGreaterThanEqual(age: Int): Flow<PersonEntity>

    // This is how to implement a custom query
    @Query("SELECT * FROM person WHERE age = :age AND name LIKE '%a%'")
    suspend fun findCustom(age: Int): Flow<PersonEntity>
}