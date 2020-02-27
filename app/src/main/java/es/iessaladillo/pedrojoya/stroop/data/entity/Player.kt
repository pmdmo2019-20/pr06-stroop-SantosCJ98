package es.iessaladillo.pedrojoya.stroop.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "players",
        indices = [

        Index(

            name = "PLAYERS_INDEX_NAME_UNIQUE",

            value = ["nombre"],

            unique = true

        )

])
data class Player(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idPlayer", index = true)
    val idPlayer: Long,
    @ColumnInfo(name = "nombre")
    var nombre: String,
    @ColumnInfo(name = "avatar")
    var avatar: Int
)