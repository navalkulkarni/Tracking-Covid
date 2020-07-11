package com.naval.trackingcovid.model

import androidx.room.Embedded
import androidx.room.Relation

class DateWithReadings() {

    @Embedded
    lateinit var date: DateOfReading

    @Relation(
        parentColumn = "reading_date",
        entityColumn = "owner_date"
    )
    lateinit var oxygenReadingsList : MutableList<OxygenReadings>
}