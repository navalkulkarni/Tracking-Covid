package com.naval.trackingcovid.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithOxygenReadings(
    @Embedded val user: User,
    @Relation(
        parentColumn = "mobile_number",
        entityColumn = "owner_id"
    )
    val oxygenReadings : List<OxygenReadings>
) {
}