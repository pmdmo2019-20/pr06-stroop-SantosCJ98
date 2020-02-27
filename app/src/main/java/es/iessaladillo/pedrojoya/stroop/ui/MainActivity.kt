package es.iessaladillo.pedrojoya.stroop.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.google.gson.Gson
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.AppDatabase
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import kotlinx.android.synthetic.main.dashboard_fragment.*


class MainActivity : AppCompatActivity(), OnToolbarAvailableListener {

    var player: Player? = null
    var prefs: SharedPreferences? = null

    val settings: SharedPreferences by lazy {

        PreferenceManager.getDefaultSharedPreferences(this)

    }

    val db: AppDatabase by lazy {

        Room.databaseBuilder(this, AppDatabase::class.java, "app_database").build()

    }


    val navController: NavController by lazy {

        findNavController(R.id.navHostFragment)

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        prefs = getSharedPreferences("app_preferences", MODE_PRIVATE);
        loadPlayer()


    }

    override fun onResume() {
        super.onResume()

        if (prefs!!.getBoolean("PREF_KEY_FIRST_TIME", true)) {

            navController.navigate(R.id.assistantDestination)

            prefs!!.edit().putBoolean("PREF_KEY_FIRST_TIME", false).commit()


        }

        loadPlayer()

    }

    private fun loadPlayer() {


        if (player == null) {

            player =  Player(-1, getString(R.string.player_selection_no_player_selected), R.drawable.logo)


        }

        else {

            val gson = Gson();
            val json = prefs!!.getString("PLAYER", "-1")
            if (json.equals("-1")) {

                player =  Player(-1, getString(R.string.player_selection_no_player_selected), R.drawable.logo)

            }

            else {

                player = gson.fromJson(json, Player::class.java)

            }


        }


    }


    override fun onToolbarCreated(toolbar: Toolbar) {

        toolbar.setupWithNavController(navController, AppBarConfiguration(navController.graph))

    }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp(AppBarConfiguration(navController.graph)) || super.onSupportNavigateUp()

    }


    override fun onToolbarDestroyed() {

    }

}
