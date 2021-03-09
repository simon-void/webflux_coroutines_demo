package com.example.webflux_demo.api

import com.example.webflux_demo.db.PersonEntity
import com.example.webflux_demo.db.PersonRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class CreatePersonDto(val name: String, val age: Int)
data class UpdatePersonDto(val id: Long, val name: String?, val age: Int?)

@RestController
@RequestMapping("/people")
class PersonController(
    private val personRepo: PersonRepository
) {

    // GET /people
    @GetMapping
    suspend fun getPeople(): Flow<PersonEntity> {
        return personRepo.findAll()
    }

    // GET /people/search?name=tom
    @GetMapping("/search")
    suspend fun getPeopleByName(@RequestParam name: String): Flow<PersonEntity> {
        return personRepo.findByNameContains(name)
    }

    // GET /people/adults
    @GetMapping("/adults")
    suspend fun getAdults(): Flow<PersonEntity> {
        return personRepo.findByAgeGreaterThanEqual(18)
    }

    // POST /people <data>
    @PostMapping
    suspend fun createPerson(@RequestBody request: CreatePersonDto) {
        val person = PersonEntity(request.name, request.age)
        personRepo.save(person)
    }

    // PUT /people <data>
    @PutMapping
    suspend fun updatePerson(@RequestBody request: UpdatePersonDto): ResponseEntity<Unit> {
        val person = personRepo.findById(request.id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val updatedPerson = person.copy(
            name = request.name ?: person.name,
            age = request.age ?: person.age,
        )
        personRepo.save(updatedPerson)
        return ResponseEntity(HttpStatus.OK)
    }

    // DELETE /people/352
    @DeleteMapping("/{id}")
    suspend fun deletePerson(@PathVariable id: Long): ResponseEntity<Unit> {
        val person = personRepo.findById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        personRepo.delete(person)
        return ResponseEntity(HttpStatus.OK)
    }
}
