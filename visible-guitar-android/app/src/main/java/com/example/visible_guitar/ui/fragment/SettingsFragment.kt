package com.example.visible_guitar.ui.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.visible_guitar.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.root_preferences)
        bindPreference(findPreference("theme"))
    }

    private val listener = Preference.OnPreferenceChangeListener { _, newValue ->
        if (newValue as Boolean)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        true
    }

    private fun bindPreference(preference: Preference?) {
        preference?.onPreferenceChangeListener = listener
    }
}