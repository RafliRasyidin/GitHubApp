package com.rasyidin.githubapp.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.core.service.AlarmReceiver
import org.koin.android.ext.android.inject

class SettingPreferencesFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var keyLanguage: String
    private lateinit var keyReminder: String

    private lateinit var reminderPreference: SwitchPreference
    private lateinit var languagePreference: Preference

    private val alarmReceiver: AlarmReceiver by inject()

    private lateinit var sharedPreference: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting_preferences)
        /*preferenceManager.createPreferenceScreen(context)*/

        init()

        setSummaries()

        onClickLanguage()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == keyReminder) {
            if (reminderPreference.isChecked) {
                context?.let {
                    alarmReceiver.setReminder(it)
                }
            } else {
                context?.let {
                    alarmReceiver.cancelReminder(it)
                }
            }
        }

        if (key == keyLanguage) {
            languagePreference.summary = sharedPreferences.getString(
                keyLanguage,
                resources.getString(R.string.value_language)
            )
        }

    }

    private fun init() {
        sharedPreference = preferenceManager.sharedPreferences
        keyReminder = resources.getString(R.string.key_reminder)
        keyLanguage = resources.getString(R.string.key_language)

        reminderPreference = findPreference<SwitchPreference>(keyReminder) as SwitchPreference
        languagePreference = findPreference<Preference>(keyLanguage) as Preference
    }

    private fun setSummaries() {
        languagePreference.summary =
            sharedPreference.getString(
                keyLanguage,
                resources.getString(R.string.value_language)
            )
    }

    private fun onClickLanguage() {
        languagePreference.setOnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            return@setOnPreferenceClickListener true
        }
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

}