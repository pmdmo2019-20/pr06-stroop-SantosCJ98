package es.iessaladillo.pedrojoya.stroop.ui.player.add

import AboutDialog
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
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.ui.MainActivity
import kotlinx.android.synthetic.main.dashboard_fragment.*

import kotlinx.android.synthetic.main.fragment_player.toolbar
import kotlinx.android.synthetic.main.player_add_fragment.*

class PlayerAddFragment : Fragment(R.layout.player_add_fragment) {

    private val listAdapter: PlayerAddFragmentAdapter = PlayerAddFragmentAdapter()
        .apply {

        setOnItemClickListener {updateIcon(getItem(it))

        viewModel.imagen = getItem(it)}

    }





    private val viewModel: PlayerAddViewModel by viewModels {

        PlayerAddViewModelFactory(AppDatabase.getInstance(requireContext()).playerDao, requireActivity().application)

    }

    private fun updateIcon(avatar: Int) {

        imgChar.setImageResource(avatar)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        setupViews()


    }


    private fun setupViews() {
        setupToolbar()
        setupRecyclerView()
        createPlayer()
    }

    private fun createPlayer() {
        fabaddPlayer.setOnClickListener {

            if (validatePlayer(txtNombreChar.text.toString(), viewModel.imagen)) {

                val player: Player = Player(0, txtNombreChar.text.toString(), viewModel.imagen)

                    viewModel.createPlayer(player)

                    if (viewModel.valido) {

                        savePlayer(player)


                        (requireActivity() as MainActivity).navController.navigateUp()

                        (requireActivity() as MainActivity).navController.popBackStack(
                            R.id.addPlayerDestination,
                            true
                        )

                    }

                }





        }
    }

    private fun savePlayer(player: Player) {

        val gson = Gson();
        val json = gson.toJson(player);
        (requireActivity() as MainActivity).prefs!!.edit().putString("PLAYER", json).commit();


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

        toolbar.inflateMenu(R.menu.menu1)

        toolbar.setOnMenuItemClickListener {

            if (it.itemId == R.id.mnuInfo) {

                showConfirmationDialog()

            }

            true



        }


    }

    private fun showConfirmationDialog() {

        AboutDialog(getString(R.string.player_creation_title), getString(R.string.player_creation_help_description))

            .show(requireFragmentManager(), "PlayerDialog")


    }

    fun validatePlayer(nombre: String, avatar: Int) = nombre.isNotBlank() && avatar != -1

}
