package es.iessaladillo.pedrojoya.stroop.ui.about


import AboutDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import kotlinx.android.synthetic.main.assistant_fragment.*
import kotlinx.android.synthetic.main.assistant_fragment.toolbar
import kotlinx.android.synthetic.main.dashboard_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment(R.layout.about_fragment) {

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

        AboutDialog(getString(R.string.about_title), getString(R.string.assistant_aboutDescription))

            .show(requireFragmentManager(), "AboutDialog")

    }

}
