package com.example.seoulpublicswimmingpool.seoulPublicSwimLessonData

import com.google.gson.annotations.SerializedName

data class Row(
    @SerializedName("ADDRESS")
    val ADDRESS: String,
    val CAPACITY: String,
    val CENTER_NAME: String,
    val CLASS_E: String,
    val CLASS_NAME: String,
    val CLASS_S: String,
    val CLASS_TIME: String,
    val EMAIL: String,
    val ENTER_TERM: String,
    val ENTER_WAY: String,
    val FAX: String,
    val FEE: Double,
    val FEE_FREE: String,
    val GROUND_NAME: String,
    val HOMEPAGE: String,
    val INTRO: String,
    val ONLINE_LINK: String,
    val PARKING_SIDE: String,
    val PLACE: String,
    val PROGRAM_NAME: String,
    val P_LEVEL: String,
    val SELECT_WAY: String,
    val SUBJECT_NAME: String,
    val TARGET: String,
    val TEL: String,
    val TERM: String,
    val USE_YN: String,
    val WEEK: String
)