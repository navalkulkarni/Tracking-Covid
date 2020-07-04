package com.naval.trackingcovid.model

import java.sql.Date

data class Reading(
    val user: User,
    val date: Date,
    val readings : List<String>
) {
}