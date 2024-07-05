package com.example.lessonapp

import android.os.Parcel
import android.os.Parcelable

data class Question(
    val id: Int = 0,
    val questionText: String = "",
    val options: List<String> = listOf(),
  //  val correctAnswerIndex: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: listOf(),
//        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(questionText)
        parcel.writeStringList(options)
       // parcel.writeInt(correctAnswerIndex)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}
