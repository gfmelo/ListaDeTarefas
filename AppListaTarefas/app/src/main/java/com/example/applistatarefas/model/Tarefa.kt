package com.example.applistatarefas.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.Date

data class Tarefa(
    val idTarefa: Int,
    val descricao: String,
    val dataCadastro: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idTarefa)
        parcel.writeString(descricao)
        parcel.writeString(dataCadastro)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Tarefa> {
        override fun createFromParcel(parcel: Parcel): Tarefa {
            return Tarefa(parcel)
        }

        override fun newArray(size: Int): Array<Tarefa?> {
            return arrayOfNulls(size)
        }
    }
}