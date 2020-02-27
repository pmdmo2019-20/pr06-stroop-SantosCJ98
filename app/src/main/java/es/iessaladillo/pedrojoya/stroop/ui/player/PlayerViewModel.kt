package es.iessaladillo.pedrojoya.stroop.ui.player

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import kotlin.concurrent.thread

class PlayerViewModel(val playerDao: PlayerDao, val application: Application) : ViewModel() {


    val players: LiveData<List<Player>> = playerDao.queryAllPlayers()

    val emptyViews: LiveData<Int> = players.map {

        if (it.isEmpty()) View.VISIBLE else View.INVISIBLE

    }
    var nombre: String = ""

    var avatar: Int = -1




    }
