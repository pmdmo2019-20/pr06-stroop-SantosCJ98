package es.iessaladillo.pedrojoya.stroop.ui.player.edit

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.base.Event
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.ui.MainActivity
import java.lang.Exception
import kotlin.concurrent.thread

class PlayerEditViewModel(val playerDao: PlayerDao, val application: Application) : ViewModel() {

    var imagen = -1

    var valido = true

    fun updatePlayer(player: Player) {


        try {

            playerDao.editPlayer(player)

            valido = true

        }

        catch (e: Exception) {

        valido = false

        }



    }


    fun selectPlayer(idPlayer: Long): Player {

      return playerDao.queryPlayer(idPlayer)




    }





}
