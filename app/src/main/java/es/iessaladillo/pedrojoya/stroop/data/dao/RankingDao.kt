package es.iessaladillo.pedrojoya.stroop.data.dao
import androidx.lifecycle.LiveData
import androidx.room.*
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.data.entity.PlayersInRanking
import es.iessaladillo.pedrojoya.stroop.data.entity.Ranking


@Dao
interface RankingDao {

    @Insert

    fun insertRanking(ranking: Ranking)

    @Update

    fun updateRanking(ranking: Ranking)

    @Query("SELECT * FROM ranking ORDER BY ranking.puntos DESC")
    fun queryAllRanking(): List<Ranking>

    @Query("SELECT * FROM ranking WHERE ranking.modo_juego = 'Time' ORDER BY ranking.puntos DESC")
    fun queryTimeRanking(): List<Ranking>


    @Query("SELECT * FROM ranking WHERE ranking.modo_juego = 'Attempts' ORDER BY ranking.puntos DESC")
    fun queryAttemptsRanking(): List<Ranking>

    @Query("SELECT * FROM ranking WHERE ranking.modo_juego LIKE :modojuego ORDER BY ranking.puntos DESC")
    fun queryDefaultRanking(modojuego: String): List<Ranking>

    @Query("SELECT * FROM ranking WHERE jugador = :playerId")
    fun queryPlayer(playerId: Long): List<Ranking>

}