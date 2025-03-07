package com.example.opendatajabar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opendatajabar.data.UserEntity
import com.example.opendatajabar.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userDetail = MutableStateFlow<UserEntity?>(null)
    val userDetail = _userDetail.asStateFlow()

    fun getUserById(id: Int) {
        viewModelScope.launch {
            _userDetail.value = userRepository.getUserById(id)
        }
    }

    fun updateUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.updateUser(user)
        }
    }
}
