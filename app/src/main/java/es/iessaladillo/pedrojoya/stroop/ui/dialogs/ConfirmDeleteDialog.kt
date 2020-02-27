import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.service.notification.NotificationListenerService
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.dao.PlayerDao
import es.iessaladillo.pedrojoya.stroop.data.dao.RankingDao
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import es.iessaladillo.pedrojoya.stroop.data.entity.Ranking
import es.iessaladillo.pedrojoya.stroop.ui.MainActivity
import kotlin.concurrent.thread

class ConfirmDeleteDialog(val playerDao: PlayerDao) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        isCancelable = false

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =

        AlertDialog.Builder(requireContext())

            .setTitle(getString(R.string.player_deletion_title))

            .setMessage(getString(R.string.player_deletion_message))

            .setPositiveButton(getString(R.string.player_deletion_yes)) { _, _ ->

                val gson = Gson()

                val json = (requireActivity() as MainActivity).prefs!!.getString("PLAYER", "-1")

                val playernombre = gson.fromJson(json, Player::class.java).nombre

                val player = playerDao.queryPlayer(playernombre)

                (requireActivity() as MainActivity).prefs!!.edit().putString("PLAYER", "-1").commit()

                thread {

                    playerDao.deletePlayer(player)

                }

                (requireActivity() as MainActivity).navController.navigateUp()

                (requireActivity() as MainActivity).navController.popBackStack(
                    R.id.editPlayerDestination,
                    true
                )

            }

            .setNegativeButton(getString(R.string.player_deletion_no)) { _, _ ->

            }

                    .create()





}