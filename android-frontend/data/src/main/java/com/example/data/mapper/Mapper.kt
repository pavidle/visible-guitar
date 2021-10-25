package com.example.data.mapper

interface Mapper<Entity, Domain> {
    fun mapFromEntity(t: Entity) : Domain
    fun mapToEntity(t: Domain) : Entity
}
