package es.iessaladillo.pedrojoya.stroop.ui.dashboard

import AboutDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.ui.MainActivity
import kotlinx.android.synthetic.main.assistant_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.toolbar

class DashboardFragment : Fragment(R.layout.dashboard_fragment) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        setupViews()

    }

    override fun onResume() {
        super.onResume()
        loadPlayer()

    }

    private fun buttons() {

        cardRanking.setOnClickListener{

            (requireActivity() as MainActivity).navController.navigate(R.id.rankingDestination)

        }

        cardPlay.setOnClickListener {

            if (!((requireActivity() as MainActivity).player == null || (requireActivity() as MainActivity).prefs!!.getString("PLAYER", "-1") == "-1")) {


                (requireActivity() as MainActivity).navController.navigate(R.id.gameDestination)

            }

            else {

                (requireActivity() as MainActivity).navController.navigate(R.id.addPlayerDestination)

            }

        }

        cardAssistant.setOnClickListener {

            (requireActivity() as MainActivity).navController.navigate(R.id.assistantDestination)

        }

        cardAbout.setOnClickListener {

            (requireActivity() as MainActivity).navController.navigate(R.id.aboutDestination)

        }

        cardSettings.setOnClickListener {

            (requireActivity() as MainActivity).navController.navigate(R.id.settingsDestination)

        }

        cardPlayer.setOnClickListener {

            (requireActivity() as MainActivity).navController.navigate(R.id.playerDestination)

        }

        imgPer.setOnClickListener {

            (requireActivity() as MainActivity).navController.navigate(R.id.playerDestination)

        }

        lblNombreChar.setOnClickListener {

            (requireActivity() as MainActivity).navController.navigate(R.id.playerDestination)

        }

    }

    private fun setupViews() {

        loadPlayer()

        setupToolbar()

        buttons()

    }

    private fun loadPlayer() {

        val gson = Gson()

        val json = (requireActivity() as MainActivity).prefs!!.getString("PLAYER", "-1")

        var player: Player? = null

        if (!json!!.equals("-1")) {

            player = gson.fromJson(json, Player::class.java)

        }

        else {

            player =  Player(-1, getString(R.string.player_selection_no_player_selected), R.drawable.logo)

        }

        if (!player!!.idPlayer.equals(-1)) {

            imgPer.setImageResource(player.avatar)

            lblNombreChar.text = player.nombre

        }

        else {

            imgPer.setImageResource(R.drawable.logo)

            lblNombreChar.text = getString(R.string.player_selection_no_player_selected)


        }

        (requireActivity() as MainActivity).player = player


    }


    private fun setupToolbar() {

        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
        toolbar.inflateMenu(R.menu.menu1)

        toolbar.setOnMenuItemClickListener {

            if (it.itemId == R.id.mnuInfo) {

                showConfirmationDialog()

            }

            true



        }


    }

    private fun showConfirmationDialog() {

        AboutDialog(getString(R.string.dashboard_title), getString(R.string.dashboard_help_description))

            .show(requireFragmentManager(), "DashDialog")

    }



}