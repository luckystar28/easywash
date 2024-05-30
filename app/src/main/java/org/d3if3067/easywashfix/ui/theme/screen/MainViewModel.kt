package org.d3if3067.easywashfix.ui.theme.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.d3if3067.easywashfix.model.Pelanggan
import org.d3if3067.easywashfix.model.Status
import org.d3if3067.easywashfix.model.User
import org.d3if3067.easywashfix.navigation.Screen

class MainViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _customers = MutableLiveData<List<Pelanggan>>()
    val customers: LiveData<List<Pelanggan>> get() = _customers

    private val _statusList = MutableStateFlow<List<Status>>(emptyList())
    val statusList: StateFlow<List<Status>> = _statusList

    init {
        // Check if user is logged in
        auth.currentUser?.let {
            fetchUserData(it.uid)
        }
        fetchCustomers()
        fetchStatusData()
    }

    private fun fetchUserData(uid: String) {
        viewModelScope.launch {
            try {
                val snapshot = firestore.collection("users").document(uid).get().await()
                val user = snapshot.toObject(User::class.java)
                _currentUser.value = user
            } catch (e: Exception) {
                // Handle error
                Log.e("MainViewModel", "Error fetching user data", e)
            }
        }
    }

    private fun fetchCustomers() {
        viewModelScope.launch {
            try {
                val result = firestore.collection("pelangggan").get().await()
                val customerList = result.documents.mapNotNull { it.toObject<Pelanggan>() }
                _customers.value = customerList
            } catch (e: Exception) {
                // Handle error
                Log.e("MainViewModel", "Error fetching customers", e)
            }
        }
    }

    private fun fetchStatusData() {
        viewModelScope.launch {
            try {
                val snapshot = firestore.collection("status").get().await()
                val statuses = snapshot.documents.map { it.toObject<Status>()!! }
                _statusList.value = statuses
            } catch (e: Exception) {
                // Handle error
                Log.e("MainViewModel", "Error fetching status data", e)
            }
        }
    }

    fun logout(navController: NavHostController) {
        auth.signOut()
        _currentUser.value = null
        navController.navigate(Screen.Login.route)
    }

    fun registerUser(
        fullname: String,
        email: String,
        phoneNumber: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val user = authResult.user
                if (user != null) {
                    val userData = User(user.uid, fullname, email, phoneNumber, password)
                    firestore.collection("users").document(user.uid).set(userData).await()
                    _currentUser.value = userData
                    onSuccess()
                } else {
                    onFailure()
                }
            } catch (e: Exception) {
                onFailure()
            }
        }
    }
    fun editStatus(updatedStatus: Status) {
        viewModelScope.launch {
            try {
                // Update status di Firestore menggunakan ID status yang diubah
                updatedStatus.id?.let {
                    firestore.collection("status").document(it)
                        .set(updatedStatus)
                        .addOnSuccessListener {
                            Log.d("MainViewModel", "Status updated successfully")
                            // Refresh data setelah berhasil mengedit status
                            fetchStatusData()
                        }
                        .addOnFailureListener { e ->
                            // Handle error
                            Log.e("MainViewModel", "Error updating status", e)
                        }
                }
            } catch (e: Exception) {
                // Handle error
                Log.e("MainViewModel", "Error updating status", e)
            }
        }
    }

    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            try {
                val authResult = auth.signInWithEmailAndPassword(email, password).await()
                val user = authResult.user
                if (user != null) {
                    fetchUserData(user.uid)
                    onSuccess()
                } else {
                    onFailure()
                }
            } catch (e: Exception) {
                onFailure()
            }
        }
    }
}
