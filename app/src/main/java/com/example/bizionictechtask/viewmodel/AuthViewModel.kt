package com.example.bizionictechtask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private var _signInStatus = MutableLiveData<Resource<Boolean>>()
    var signInStatus: MutableLiveData<Resource<Boolean>> = _signInStatus

    private var _signUpStatus = MutableLiveData<Resource<Boolean>>()
    var signUpStatus: MutableLiveData<Resource<Boolean>> = _signUpStatus

    fun loginUser(email: String, password: String) {
        viewModelScope.launch(IO) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _signInStatus.postValue(Resource.Success(true))
                    } else {
                        _signInStatus.postValue(Resource.Error(task.exception?.message ?: "Error"))
                    }
                }
        }
    }

    fun signUpUser(email: String, password: String) {
        viewModelScope.launch(IO) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _signUpStatus.postValue(Resource.Success(true))
                    } else {
                        _signUpStatus.postValue(Resource.Error(task.exception?.message ?: "Error"))
                    }
                }
        }
    }
}