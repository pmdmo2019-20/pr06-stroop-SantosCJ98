package es.iessaladillo.pedrojoya.stroop.ui.settings

import AboutDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.ui.MainActivity
import kotlinx.android.synthetic.main.assistant_fragment.*
import kotlinx.android.synthetic.main.assistant_fragment.toolbar
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.player_edit_fragment.*

class MainSettingsFragment : Fragment(R.layout.settings_fragment) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        setupViews()

    }

    private fun setupViews() {

        setupToolbar()

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

        AboutDialog(getString(R.string.settings_title), getString(R.string.settings_help_description))

            .show(requireFragmentManager(), "SettingsDialog")

    }

}