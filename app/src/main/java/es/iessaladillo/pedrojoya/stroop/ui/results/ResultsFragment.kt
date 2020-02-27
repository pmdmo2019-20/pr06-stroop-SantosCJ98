package es.iessaladillo.pedrojoya.stroop.ui.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.data.entity.Ranking
import es.iessaladillo.pedrojoya.stroop.ui.MainActivity
import kotlinx.android.synthetic.main.assistant_fragment.*
import kotlinx.android.synthetic.main.assistant_fragment.toolbar
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.results_fragment.*

class ResultsFragment : Fragment(R.layout.results_fragment) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        setupViews()

    }

    private fun setupViews() {
        setupToolbar()
        setupResults()
    }

    private fun setupResults() {

        val gson = Gson()

        val json = (requireActivity() as MainActivity).prefs!!.getString("RANKING", "-1")

        val ranking = gson.fromJson(json, Ranking::class.java)

        imgPerR.setImageResource(ranking.jugadorob.avatar)

        lblPerR.text = ranking.jugadorob.nombre

        lblCorrec.text = ranking.correctas.toString()

        lblIncorrec.text = ranking.incorrectas.toString()

        lblTotal.text = ranking.puntos.toString()

    }

    private fun setupToolbar() {

        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)

        toolbar.inflateMenu(R.menu.menu1)

    }

}

