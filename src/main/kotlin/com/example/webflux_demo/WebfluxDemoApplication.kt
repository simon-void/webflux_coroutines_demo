package com.example.webflux_demo

import com.example.webflux_demo.db.PersonEntity
import com.example.webflux_demo.db.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Demo project to check out WebFlux with Coroutines.
 * Adapted from https://medium.com/@tienisto/asynchronous-spring-using-kotlin-coroutines-and-r2dbc-93b3a079ac22
 */

@SpringBootApplication
class WebfluxDemoApplication(
    private val personRepository: PersonRepository,
): CommandLineRunner {

    override fun run(vararg args: String?) = runBlocking {
        // save a few customers
        personRepository.saveAll(
            listOf(
                PersonEntity("Jack Bauer", 42),
                PersonEntity("Chloe O'Brian", 36),
                PersonEntity("Kim Bauer", 15),
                PersonEntity("David Palmer", 55),
            )
        )
            // collecting is necessary,
            // otherwise the call to 'findAll()' will be too fast
            // and find nothing
            .collect()

        println("saved default persons")

        val persons: Flow<PersonEntity> = personRepository.findAll()
        val nrOfPersons = persons.count()
        println("found $nrOfPersons persons")
    }

}

fun main(args: Array<String>) {
    runApplication<WebfluxDemoApplication>(*args)
}
