package es.iessaladillo.pedrojoya.stroop.ui.player.add

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.stroop.base.Event
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import java.lang.Exception
import kotlin.concurrent.thread

class PlayerAddViewModel(val playerDao: PlayerDao, val application: Application) : ViewModel() {

    var imagen = -1

    var valido = true

    fun createPlayer(player: Player) {

        try {


            playerDao.insertPlayer(player)

            valido = true

        }

        catch (e: Exception) {

            valido = false

        }

    }





}
