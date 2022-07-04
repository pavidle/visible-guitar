package com.example.visible_guitar.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.example.visible_guitar.R


class BaseSettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    private val preferences = mutableListOf<Preference>()

    private val h = mutableMapOf<String, Preference>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.root_preferences)
        findPreference<Preference>("theme")?.let { bindPreference(it) }

    }

    private fun addKey(key: String) {
        findPreference<Preference>(key)?.let { preferences.add(it) }
    }

    private fun bindPreference(preference: Preference?) {
        preference?.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        return if (preference.key == "theme") {
            if (newValue as Boolean)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            true
        } else {
            false
        }

    }
}