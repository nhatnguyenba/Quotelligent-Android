package com.nhatnguyenba.quotelligent.presentation.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(private val themeManager: ThemeManager) : ViewModel() {

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    init {
        viewModelScope.launch {
            themeManager.darkModeFlow.collectLatest { enabled ->
                _isDarkMode.value = enabled
                applyTheme(enabled)
            }
        }
    }

    fun toggleDarkMode() {
        viewModelScope.launch {
            val newMode = !_isDarkMode.value
            themeManager.setDarkMode(newMode)
        }
    }

    private fun applyTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}