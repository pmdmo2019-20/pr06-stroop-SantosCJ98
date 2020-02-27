package es.iessaladillo.pedrojoya.stroop.ui.ranking


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.stroop.data.dao.RankingDao

class RankingFragmentViewModelFactory (private val repository: RankingDao, private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RankingFragmentViewModel::class.java!!)) {
            RankingFragmentViewModel(this.repository, this.application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}