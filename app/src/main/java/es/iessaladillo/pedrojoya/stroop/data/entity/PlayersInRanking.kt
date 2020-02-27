package es.iessaladillo.pedrojoya.stroop.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PlayersInRanking(

    @Embedded

    val player: Player,

    @Relation(

        parentColumn = "idPlayer",

        entityColumn = "jugador"

    )

    val ranking: Ranking?

)