package com.example.service

import com.example.domain.Thing
import grails.gorm.services.Service

@Service(Thing)
interface ThingService {
    List<Thing> findAll(Map args)
    Thing findById(int id)
    Thing save(Thing thing)
}
