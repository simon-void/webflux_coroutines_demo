# Spring Webflux with Kotlin Coroutines demo

just creating a `CoroutineCrudRepository` and wiring it up to provide a non-blocking RestAPI.

Check out `PersonRepository` and `PersonController` on how they use suspend functions and `Flow`
in order to achieve this non-blocking behaviour.

## start-up

via `./gradlew bootRun`

## calls

(if you test locally, put `http://localhost:8080` in front of all the urls)
- GET `/people` : to get a list of all people
- GET `/people/search?name={name}` : to get a list of all people with that name. List might be empty. 
- GET `/people/adults` : to get a list of all people older than or equal 18
- POST `/people` <data> : creates a new person entry. provide name and age in request body.
- PUT `/people` <data> : updates an existing person entry. provide name and/or age in request body.
- DELETE `/people/{id}` : delete a person given its id.

4 person entries are added to the in-memory database at start-up to play around with.
