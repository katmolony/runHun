package ie.setu.placemark.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ie.setu.placemark.ui.theme.ThemeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val themeViewModel: ThemeViewModel) : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(themeViewModel.isDarkTheme.value)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    init {
        viewModelScope.launch {
            themeViewModel.isDarkTheme.collect { isDark ->
                _isDarkTheme.value = isDark
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            themeViewModel.toggleTheme()
        }
    }

    // Add language change logic here
}