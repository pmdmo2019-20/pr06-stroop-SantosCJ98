package es.iessaladillo.pedrojoya.stroop.ui.game

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.gson.Gson
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.AppDatabase
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.data.entity.Ranking
import es.iessaladillo.pedrojoya.stroop.ui.MainActivity
import es.iessaladillo.pedrojoya.stroop.ui.player.PlayerViewModelFactory
import kotlinx.android.synthetic.main.game_fragment.*

class GameFragment : Fragment(R.layout.game_fragment) {

    private val viewModel: GameViewModel by viewModels {

        GameViewModelFactory(AppDatabase.getInstance(requireContext()).rankingDao, requireActivity().application)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        setupViews()

    }

    private fun setupViews() {

       startGame()

        observeViewModel()

        buttons()

    }

    private fun buttons() {

        imgRight.setOnClickListener {

            viewModel.checkRight()

        }

        imgWrong.setOnClickListener {

            viewModel.checkWrong()

        }

    }

    private fun observeViewModel() {

        viewModel.run {

            bar.observe(viewLifecycleOwner) {

                pbBarra.progress = it

            }

            palabra.observe(viewLifecycleOwner) {

                lblWordStroop.text = it

            }

            color.observe(viewLifecycleOwner) {

                lblWordStroop.setTextColor(it)

            }

            words.observe(viewLifecycleOwner) {

                lblWords.text = it.toString()

            }

            puntos.observe(viewLifecycleOwner) {

                lblPoints.text = it.toString()

            }

            correct.observe(viewLifecycleOwner) {

                lblCorrect.text = it.toString()

            }

            finish.observe(viewLifecycleOwner) {

                if (it) {

                   insertRanking()

                    navigateToResults()

                }

                modo.observe(viewLifecycleOwner) {

                    if (it == "Time") {

                        llAttempts.visibility = View.INVISIBLE

                    }

                    else if (it == "Attempts"){

                        llPoints.visibility = View.INVISIBLE

                    }

                }

                attempts.observe(viewLifecycleOwner) {

                    lblAttempts.text = it.toString()

                }

            }


        }

    }

    private fun navigateToResults() {

        (requireActivity() as MainActivity).navController.popBackStack(R.id.gameDestination, true)

        (requireActivity() as MainActivity).navController.navigate(R.id.resultsDestination)



    }

    private fun insertRanking() {

        val player: Player = viewModel.player.value as Player

        val ranking = Ranking(0, player.idPlayer, viewModel.modo.value as String, viewModel.tiempo.value!!.div(60000), viewModel.words.value as Int, viewModel.correct.value as Int, viewModel.incorrect.value as Int, viewModel.puntos.value as Int, player)

        viewModel.rankingDao.insertRanking(ranking)

        val gson = Gson()

        val json = gson.toJson(ranking, Ranking::class.java)

        (requireActivity() as MainActivity).prefs!!.edit().putString("RANKING", json).commit()



    }

    private fun loadPlayer(): Player? {

        val gson = Gson()

        val json = (requireActivity() as MainActivity).prefs!!.getString("PLAYER", "-1")

        return gson.fromJson(json, Player::class.java)


    }

    private fun startGame() {

        viewModel.player.value = loadPlayer()

        viewModel.tiempo.value = (requireActivity() as MainActivity).settings.getString(getString(R.string.prefGameTime_key), getString(R.string.prefGameTime_value_60))!!.toInt()

        pbBarra.max = viewModel.tiempo.value as Int

        pbBarra.progress = viewModel.tiempo.value as Int

        viewModel.modo.value = (requireActivity() as MainActivity).settings.getString(getString(R.string.prefGameMode_key), getString(R.string.prefGameMode_defaultValue))

        viewModel.attempts.value = (requireActivity() as MainActivity).settings.getString(getString(R.string.prefAttempts_key), "5")!!.toInt()

        val wordTime = (requireActivity() as MainActivity).settings.getString(getString(R.string.prefWordTime_key), getString(R.string.prefWordTime_value_1000))!!.toInt()

        viewModel.startGameThread(viewModel.tiempo.value as Int, wordTime)

    }


}