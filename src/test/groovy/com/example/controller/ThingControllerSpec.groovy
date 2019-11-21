package com.example.controller

import com.example.domain.Thing
import com.example.service.ThingService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest(packages = ['com.example.domain'])
class ThingControllerSpec extends Specification {

    @Shared @AutoCleanup @Inject @Client("/")
    RxHttpClient client

    @Inject
    ThingService thingService

    void 'test getOne that does not exist'() {
        when:
        HttpResponse<Thing> response = client.toBlocking().exchange('/things/1', Thing)

        then:
        response.status == HttpStatus.NOT_FOUND

        and:
        1 * thingService.findById(1) >> null
    }

    void 'test getOne that does exist'() {
        setup:
        Thing thing = new Thing(name: 'Foo Bar', code: 'BAZ-QUX')

        when:
        HttpResponse<Thing> response = client.toBlocking().exchange('/things/1', Thing)

        then:
        response.status == HttpStatus.OK
        response.body.ifPresent { x -> x.code == 'BAZ-QUC' }

        and:
        1 * thingService.findById(1) >> thing
    }

    @MockBean(ThingService)
    ThingService thingService() {
        Mock(ThingService)
    }
}
