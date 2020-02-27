package es.iessaladillo.pedrojoya.stroop.ui.player


import AboutDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.AppDatabase
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao_Impl
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.ui.MainActivity
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.toolbar
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.android.synthetic.main.fragment_player.emptyView
import kotlinx.android.synthetic.main.fragment_player.fabaddPlayer
import kotlinx.android.synthetic.main.fragment_ranking.*

/**
 * A simple [Fragment] subclass.
 */



class PlayerFragment : Fragment(R.layout.fragment_player) {

    private val listAdapter: PlayerFragmentAdapter = PlayerFragmentAdapter().apply {

        setOnItemCLickListener {

            selectPlayer(getItem(it))

            checkButtonVisibility()

        }


    }

    private fun checkButtonVisibility() {


        val gson = Gson()

        val json = (requireActivity() as MainActivity).prefs!!.getString("PLAYER", "-1")

        var player: Player? = null

        if (!json!!.equals("-1")) {

            player = gson.fromJson(json, Player::class.java)


            if (!player!!.idPlayer.equals(-1)) {

                btnEditPlayer.visibility = View.VISIBLE

            }

            else {

                btnEditPlayer.visibility = View.INVISIBLE


            }

        }

        else {

            btnEditPlayer.visibility = View.GONE

        }





    }

    private fun selectPlayer(player: Player) {


        savePlayer(player)

        loadPlayer()






    }

    override fun onResume() {
        super.onResume()
        loadPlayer()
        checkButtonVisibility()
    }



    private fun savePlayer(player: Player) {

        val gson = Gson();
        val json = gson.toJson(player);
        (requireActivity() as MainActivity).prefs!!.edit().putString("PLAYER", json).commit();


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

                imgCharSelected.setImageResource(player.avatar)

                lblNombreCharSelected.text = player.nombre

            }

            else {

                imgCharSelected.setImageResource(R.drawable.logo)

                lblNombreCharSelected.text = getString(R.string.player_selection_no_player_selected)


            }

        (requireActivity() as MainActivity).player = player


    }




    private val viewModel: PlayerViewModel by viewModels {

        PlayerViewModelFactory(AppDatabase.getInstance(requireContext()).playerDao, requireActivity().application)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        setupViews()

        observeViewModel()

    }


    private fun observeViewModel() {

        viewModel.run {

            players.observe(viewLifecycleOwner) {

                listAdapter.submitList(it)

            }

            emptyViews.observe(viewLifecycleOwner) {

                emptyView.visibility = it

            }





        }

    }

    private fun setupViews() {
        loadPlayer()
        checkButtonVisibility()
        setupToolbar()
        setupRecyclerView()
        navigateToAddPlayer()
        navigatetoEditPlayer()
    }



    private fun navigatetoEditPlayer() {

        btnEditPlayer.setOnClickListener {

            (requireActivity() as MainActivity).navController.navigate(R.id.editPlayerDestination)

        }

    }

    private fun navigateToAddPlayer() {

        emptyView.setOnClickListener {

            (requireActivity() as MainActivity).navController.navigate(R.id.addPlayerDestination)


        }

        fabaddPlayer.setOnClickListener {

            (requireActivity() as MainActivity).navController.navigate(R.id.addPlayerDestination)


        }

    }

    private fun setupRecyclerView() {

            lstPlayers.run {

                setHasFixedSize(true)

                layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)

                itemAnimator = DefaultItemAnimator()

                adapter = listAdapter

            }


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

        AboutDialog(getString(R.string.player_selection_title), getString(R.string.player_selection_help_description))

            .show(requireFragmentManager(), "PlayerDialog")

    }




}


