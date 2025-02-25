package org.white.green.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(Firebase.auth.currentUser != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    init {
        observeAuthChanges()
    }

    fun observeAuthChanges() {
        viewModelScope.launch {
            val currentUser = Firebase.auth.currentUser
            _isLoggedIn.emit(currentUser != null)
        }
    }

    fun logout() {
        viewModelScope.launch {
            Firebase.auth.signOut()
            _isLoggedIn.emit(false)
        }
    }
}
