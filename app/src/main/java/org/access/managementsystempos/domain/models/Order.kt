package org.access.managementsystempos.domain.models

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.UUID

data class Order(
    val id: String = UUID.randomUUID().toString(),
    val timePlaced: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val items: Map<Product, Int>,
    var isCompleted: Boolean = false
)
