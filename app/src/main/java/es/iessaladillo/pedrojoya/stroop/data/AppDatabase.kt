package es.iessaladillo.pedrojoya.stroop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.avatars
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.dao.RankingDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.data.entity.Ranking
import kotlin.concurrent.thread

@Database(entities = [Player::class, Ranking::class], version = 1, exportSchema = true)

abstract class AppDatabase : RoomDatabase() {

    abstract val playerDao: PlayerDao

    abstract val rankingDao: RankingDao

    companion object {

        @Volatile

        private var INSTANCE: AppDatabase? = null
 fun getInstance(context: Context): AppDatabase {

            if (INSTANCE == null) {

                synchronized(this) {

                    if (INSTANCE == null) {

                        INSTANCE = Room.databaseBuilder(

                            context.applicationContext,

                            AppDatabase::class.java,

                            "app_database"

                        ).allowMainThreadQueries().build()

                    }

                }

            }

            return INSTANCE!!

        }

    }

}
