package es.iessaladillo.pedrojoya.stroop.data.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
@Entity(tableName = "ranking")
data class Ranking(
    @PrimaryKey(autoGenerate = true)
    val idRanking: Long,
    @ColumnInfo(name = "jugador")
    val jugador: Long,
    @ColumnInfo(name = "modo_juego")
    val modo_juego: String,
    @ColumnInfo(name = "minutos")
    val minutos: Int,
    @ColumnInfo(name = "palabras")
    val palabras: Int,
    @ColumnInfo(name = "correctas")
    val correctas: Int,
    @ColumnInfo(name = "incorrectas")
    val incorrectas: Int,
    @ColumnInfo(name = "puntos")
    val puntos: Int,
    @Embedded
    val jugadorob: Player

)