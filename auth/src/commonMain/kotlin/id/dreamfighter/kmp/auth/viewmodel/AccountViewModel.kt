package id.dreamfighter.kmp.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.dreamfighter.kmp.auth.api.Api
import id.dreamfighter.kmp.auth.model.Storage
import id.dreamfighter.kmp.auth.model.User
import id.dreamfighter.kmp.network.BASE_URL
import id.dreamfighter.kmp.network.flowReq
import id.dreamfighter.kmp.network.model.RequestUiState
import id.dreamfighter.kmp.network.model.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<RequestUiState<User>> = MutableStateFlow(RequestUiState())
    val uiState = _uiState.asStateFlow()
    private val preference = Storage.getPreference()
    private val _uiUserState: MutableStateFlow<RequestUiState<User>> = MutableStateFlow(RequestUiState())
    val userState = _uiUserState.asStateFlow()

    init {
        BASE_URL = "http://192.168.0.37:3000"
    }

    fun getUser(){
        viewModelScope.launch {
            _uiUserState.value = _uiUserState.value.copy(
                loading =  true,
                error = ""
            )
            preference.getUser().collect {
                _uiUserState.value = _uiUserState.value.copy(
                    data = it,
                    loading =  false,
                    error = ""
                )
            }
        }
    }

    fun signInGoogleWithCode(code:String){
        viewModelScope.launch {
            flowReq<User>(Api.googleToken(code)).collect{
                when(it){
                    is Resource.Success -> {
                        it.data?.let { user ->
                            preference.updateUser(user)
                        }

                        _uiState.value = _uiState.value.copy(
                            data = it.data,
                            loading =  false,
                            error = ""
                        )
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            loading =  true,
                        )
                    }

                    is Resource.Error ->{
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            error = it.error?:"Something went wrong"
                        )
                    }
                }
            }
        }
    }

    fun signInGoogle(idToken:String){
        viewModelScope.launch {
            flowReq<User>(Api.authGoogle(mapOf("idToken" to idToken)))
                .collect {
                when(it){
                    is Resource.Success -> {
                        it.data?.let { user ->
                            preference.updateUser(user)
                        }
                        _uiState.value = _uiState.value.copy(
                            data = it.data,
                            loading =  false,
                            error = ""
                        )
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            loading =  true,
                        )
                    }

                    is Resource.Error ->{
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            error = it.error?:"Something went wrong"
                        )
                    }
                }
            }
        }
    }

    fun deleteUser(onDeleted:()->Unit){
        viewModelScope.launch {
            preference.deleteUser()
            onDeleted()
        }
    }
}