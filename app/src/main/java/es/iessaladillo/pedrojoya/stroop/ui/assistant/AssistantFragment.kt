package es.iessaladillo.pedrojoya.stroop.ui.assistant


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.ui.MainActivity
import kotlinx.android.synthetic.main.assistant_fragment.*
import kotlinx.android.synthetic.main.assistant_fragment.toolbar
import kotlinx.android.synthetic.main.dashboard_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class AssistantFragment : Fragment(R.layout.assistant_fragment) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        setupViews()

    }

    private fun setupViews() {

        viewPager.adapter = AssistantAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, _ ->

            viewPager.setCurrentItem(tab.position, true)

        }.attach()

        setupToolbar()

    }



    private fun setupToolbar() {

            (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)

    }


    }

