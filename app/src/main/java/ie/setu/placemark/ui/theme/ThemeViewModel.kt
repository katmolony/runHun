package ie.setu.placemark.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    init {
        viewModelScope.launch {
            ThemePreferences.isDarkTheme(application).collect { isDark ->
                _isDarkTheme.value = isDark
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            val newTheme = !_isDarkTheme.value
            _isDarkTheme.value = newTheme
            ThemePreferences.setDarkTheme(getApplication(), newTheme)
        }
    }

    fun setDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            _isDarkTheme.value = isDark
            ThemePreferences.setDarkTheme(getApplication(), isDark)
        }
    }
}