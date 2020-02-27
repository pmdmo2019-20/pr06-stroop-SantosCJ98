package es.iessaladillo.pedrojoya.stroop.ui.settings

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.stroop.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {



        setPreferencesFromResource(R.xml.preferences, rootKey);


    }


}