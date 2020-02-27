package es.iessaladillo.pedrojoya.stroop.ui.ranking

import AboutDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.AppDatabase
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.dao.RankingDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.data.entity.Ranking
import es.iessaladillo.pedrojoya.stroop.ui.MainActivity
import es.iessaladillo.pedrojoya.stroop.ui.Ranking.RankingFragmentAdapter
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.dashboard_fragment.toolbar
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.android.synthetic.main.fragment_ranking.*
import kotlinx.android.synthetic.main.fragment_ranking.emptyView
import java.util.*

class RankingFragment : Fragment(R.layout.fragment_ranking) {

    private val listAdapter: RankingFragmentAdapter = RankingFragmentAdapter().apply {

        setOnItemClickListener4 {


        }


    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
        spinner()
    }


    private val viewModel: RankingFragmentViewModel by viewModels {

        RankingFragmentViewModelFactory(AppDatabase.getInstance(requireContext()).rankingDao, requireActivity().application)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        setupViews()

        observeViewModel()

    }


    private fun observeViewModel() {

        viewModel.run {


            ranking.observe(viewLifecycleOwner) {

                listAdapter.submitList(it)

            }

            emptyViews.observe(viewLifecycleOwner) {

                emptyView.visibility = it

            }


        }

    }






    private fun setupViews() {
        setupToolbar()
        setupRecyclerView()
        spinner()

    }

        private fun spinner() {

            if ((requireActivity() as MainActivity).settings.getString(getString(R.string.prefRankingFilter_key), "All") == "All") {

                spnMode.setSelection(0)

            }

            else if ((requireActivity() as MainActivity).settings.getString(getString(R.string.prefRankingFilter_key), "All") == "Time") {

                spnMode.setSelection(1)

            }

            else {

                spnMode.setSelection(2)

            }

            spnMode.onItemSelectedListener =

                object : AdapterView.OnItemSelectedListener {

                    override fun onNothingSelected(parent: AdapterView<*>?) { }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?,

                                                position: Int, id: Long) {

                        val element = spnMode.getSelectedItem().toString()


                        if (element == "All") {

                            viewModel.queryAllRanking()

                        }

                        else if (element == "Time") {

                            viewModel.queryTimeRanking()

                        }

                      else {

                            viewModel.queryAttemptsRanking()

                        }


                    }


        }
    }


    private fun setupRecyclerView() {

        lstRanking.run {

            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

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

        AboutDialog(getString(R.string.ranking_title), getString(R.string.ranking_help_description))

            .show(requireFragmentManager(), "RankDialog")


    }




}
