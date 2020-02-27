package es.iessaladillo.pedrojoya.stroop.ui.assistant.Parts


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator

import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.ui.MainActivity
import es.iessaladillo.pedrojoya.stroop.ui.assistant.AssistantAdapter
import kotlinx.android.synthetic.main.assistant_fragment.*
import kotlinx.android.synthetic.main.eightfragment_layout.*

/**
 * A simple [Fragment] subclass.
 */
class EightFragment : Fragment(R.layout.eightfragment_layout) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        setupViews()

    }

    private fun setupViews() {

        btnSalirTutorial.setOnClickListener {

            (requireActivity() as MainActivity).navController.navigate(R.id.dashboardDestination)

        }



    }




}
