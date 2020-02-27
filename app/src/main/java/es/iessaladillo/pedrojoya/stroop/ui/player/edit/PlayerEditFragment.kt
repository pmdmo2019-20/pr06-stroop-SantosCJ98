package es.iessaladillo.pedrojoya.stroop.ui.player.edit

import AboutDialog
import ConfirmDeleteDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.AppDatabase
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.ui.MainActivity
import kotlinx.android.synthetic.main.dashboard_fragment.*

import kotlinx.android.synthetic.main.player_edit_fragment.*
import kotlinx.android.synthetic.main.player_edit_fragment.toolbar

class PlayerEditFragment : Fragment(R.layout.player_edit_fragment) {

    private val listAdapter: PlayerEditFragmentAdapter = PlayerEditFragmentAdapter()
        .apply {

            setOnItemClickListener3 {
                updateIcon(getItem(it))

                viewModel.imagen = getItem(it)
            }

        }


    private val viewModel: PlayerEditViewModel by viewModels {

        PlayerEditViewModelFactory(
            AppDatabase.getInstance(requireContext()).playerDao,
            requireActivity().application
        )

    }

    private fun updateIcon(avatar: Int) {

        imgCharEdit.setImageResource(avatar)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        setupViews()


    }


    private fun setupViews() {
        loadPlayer()
        setupToolbar()
        setupRecyclerView()
        updatePlayer()
    }

    private fun loadPlayer() {

        val player = (requireActivity() as MainActivity).player

        imgCharEdit.setImageResource(player!!.avatar)

        txtNombreCharEdit.setText(player!!.nombre)

    }

    fun getPlayer(): Player {

        val gson = Gson()

        val json = (requireActivity() as MainActivity).prefs!!.getString("PLAYER", "-1")

        var player = gson.fromJson(json, Player::class.java)

        return viewModel.playerDao.queryPlayer(player!!.idPlayer)

    }

    private fun savePlayer(player: Player) {

        val gson = Gson();
        val json = gson.toJson(player);
        (requireActivity() as MainActivity).prefs!!.edit().putString("PLAYER", json).commit();


    }

    private fun updatePlayer() {
        fabeditPlayer.setOnClickListener {

            if (validatePlayer(txtNombreCharEdit.text.toString(), viewModel.imagen)) {

                val player = getPlayer()

                player.nombre = txtNombreCharEdit.text.toString()

                player.avatar = viewModel.imagen


                viewModel.updatePlayer(player)

                if (viewModel.valido) {

                    savePlayer(player)


                    (requireActivity() as MainActivity).navController.navigateUp()

                    (requireActivity() as MainActivity).navController.popBackStack(
                        R.id.editPlayerDestination,
                        true
                    )

                }




            }

        }
    }

    private fun setupRecyclerView() {

        lstAvatars.run {

            setHasFixedSize(true)

            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)

            itemAnimator = DefaultItemAnimator()

            adapter = listAdapter

        }


    }

    private fun setupToolbar() {

        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)

        toolbar.inflateMenu(R.menu.menu2)

        toolbar.setOnMenuItemClickListener {

            if (it.itemId == R.id.mnuInfo) {

                showConfirmationDialog()

            }

            else if (it.itemId == R.id.mnuDelete) {

                showDeleteDialog(viewModel.playerDao)

            }

            true



        }


    }

    private fun showConfirmationDialog() {

        AboutDialog(getString(R.string.player_edition_title), getString(R.string.player_edition_help_description))

            .show(requireFragmentManager(), "PlayerEditDialog")

    }

    private fun showDeleteDialog(playerdao: PlayerDao) {

        ConfirmDeleteDialog(playerdao)

            .show(requireFragmentManager(), "PlayerDeleteDialog")

    }

    fun validatePlayer(nombre: String, avatar: Int) = nombre.isNotBlank() && avatar != -1



}
