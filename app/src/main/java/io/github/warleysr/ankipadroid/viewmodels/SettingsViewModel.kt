package io.github.warleysr.ankipadroid.viewmodels

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import io.github.warleysr.ankipadroid.AnkiPADroid
import io.github.warleysr.ankipadroid.screens.settings.HSVColor

class SettingsViewModel() : ViewModel() {

    private val sharedPrefs = AnkiPADroid.instance.getSharedPreferences(
        "settings", Context.MODE_PRIVATE
    )
    var materialYou: MutableState<Boolean> = mutableStateOf(false)
        private set

    var theme: MutableState<String> = mutableStateOf("system")
        private set

    var adjustingColors: MutableState<Boolean> = mutableStateOf(false)
        private set

    init {
        println("SettingsViewModel initialized")
        val materialYou = getSetting("material_you")
        this.materialYou.value = materialYou.isNotEmpty() && materialYou.toBooleanStrict()

        theme.value = getSetting("theme")
    }

    fun getSetting(key: String): String {
        return sharedPrefs.getString(key, "") ?: ""
    }

    fun saveSetting(key: String, value: String) {
        with (sharedPrefs.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun setMaterialYou(enabled: Boolean) {
        materialYou.value = enabled
        saveSetting("material_you", enabled.toString())
    }

    fun changeLanguage(language: String) {
        AnkiPADroid.instance.run {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))
        }
    }

    fun toggleAdjustingColors() {
        adjustingColors.value = !adjustingColors.value
    }

    fun saveRangeColors(lower: HSVColor, upper: HSVColor) {
        with (sharedPrefs.edit()) {
            putFloat("color_lower_H", lower.H)
            putFloat("color_lower_S", lower.S)
            putFloat("color_lower_V", lower.V)

            putFloat("color_upper_H", upper.H)
            putFloat("color_upper_S", upper.S)
            putFloat("color_upper_V", upper.V)

            apply()
        }

        toggleAdjustingColors()
    }

    fun getRangeColors(): Pair<HSVColor, HSVColor> {
        val lower = HSVColor(
            sharedPrefs.getFloat("color_lower_H", 120f),
            sharedPrefs.getFloat("color_lower_S", 0.5f),
            sharedPrefs.getFloat("color_lower_V", 0.5f),
        )
        val upper = HSVColor(
            sharedPrefs.getFloat("color_upper_H", 125f),
            sharedPrefs.getFloat("color_upper_S", 0.55f),
            sharedPrefs.getFloat("color_upper_V", 0.55f),
        )
        return Pair(lower, upper)
    }

    fun openAboutInfo() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://github.com/warleysr/ankipadroid")
        AnkiPADroid.instance.startActivity(intent)
    }

}