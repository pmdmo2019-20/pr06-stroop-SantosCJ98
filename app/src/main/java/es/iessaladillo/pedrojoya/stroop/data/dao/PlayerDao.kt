package es.iessaladillo.pedrojoya.stroop.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import es.iessaladillo.pedrojoya.stroop.data.entity.Player


@Dao
interface PlayerDao {

    @Insert
    fun insertPlayer(player: Player)

    @Update
    fun editPlayer(player: Player)

    @Delete
    fun deletePlayer(player: Player)

    @Query("SELECT * FROM players")
    fun queryAllPlayers(): LiveData<List<Player>>

    @Query("SELECT * FROM players WHERE players.idPlayer = :playerId")
    fun queryPlayer(playerId: Long): Player

    @Query("SELECT * FROM players WHERE players.nombre = :nombre")
    fun queryPlayer(nombre: String): Player

}