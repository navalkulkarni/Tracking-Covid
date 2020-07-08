package com.naval.trackingcovid.model

import androidx.room.Embedded
import androidx.room.Relation

 class UserWithOxygenReadings() {
    @Embedded
    lateinit var user: User

    @Relation(
        parentColumn = "mobile_number",
        entityColumn = "owner_id"
    )
    lateinit var oxygenReadingsList : MutableList<OxygenReadings>
}