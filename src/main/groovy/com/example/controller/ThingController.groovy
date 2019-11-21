package com.example.controller

import com.example.domain.Thing
import com.example.service.ThingService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

import javax.inject.Inject
import javax.validation.Valid

@Controller('/things')
class ThingController {

    @Inject
    ThingService thingService

    @Get('/')
    List<Thing> getAll() {
        thingService.findAll()
    }

    @Get('/{id}')
    Thing getOne(int id) {
        thingService.findById(id)
    }

    @Post('/')
    HttpResponse save(@Valid @Body Thing thing) {
        Thing result = thingService.save(thing)
        HttpResponse.created(result, URI.create("/things/${result.id}"))
    }

}