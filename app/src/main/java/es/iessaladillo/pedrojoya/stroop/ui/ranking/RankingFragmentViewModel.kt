package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.app.Application
import android.content.SharedPreferences
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.dao.RankingDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.data.entity.Ranking


class RankingFragmentViewModel(val rankingDao: RankingDao, val application: Application): ViewModel() {


    private val _ranking: MutableLiveData<List<Ranking>> = MutableLiveData()

    val ranking: LiveData<List<Ranking>>

        get() = _ranking

     fun queryAllRanking() {

        _ranking.value = rankingDao.queryAllRanking()

    }

     fun queryTimeRanking() {

        _ranking.value = rankingDao.queryTimeRanking()

    }

    fun queryAttemptsRanking() {

        _ranking.value = rankingDao.queryAttemptsRanking()

    }

    val emptyViews: LiveData<Int> = ranking.map {

        if (it.isEmpty()) View.VISIBLE else View.INVISIBLE

    }



}