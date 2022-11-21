package com.example.seoulpublicswimmingpool.database

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
class SwimLesson(
    val id: Int, var center: String?, var week: String?, var time: String?, val fee: String?
) : Parcelable {
    companion object : Parceler<SwimLesson> {
        override fun create(parcel: Parcel): SwimLesson {
            return SwimLesson(parcel)
        }

        override fun SwimLesson.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(center)
            parcel.writeString(week)
            parcel.writeString(time)
            parcel.writeString(fee)
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )
}
