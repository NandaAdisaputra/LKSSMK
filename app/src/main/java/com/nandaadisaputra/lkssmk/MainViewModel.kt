package com.nandaadisaputra.lkssmk

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {

    // Menyimpan data user setelah login
    val user = MutableLiveData<User?>()
    // Menyimpan pesan error jika login gagal
    val error = MutableLiveData<String>()

    private val repo = AuthRepository() // Menggunakan repositori untuk login

    // Fungsi untuk login
    fun login(email: String, password: String) {
        // Menjalankan login di thread baru agar tidak mengganggu UI
        thread {
            val loginUser = repo.login(email, password) // Coba login dengan data yang diberikan
            if (loginUser != null) {
                // Jika login berhasil, kirim data user ke UI
                user.postValue(loginUser)
            } else {
                // Jika login gagal, kirim pesan error ke UI
                error.postValue("Login gagal")
            }
        }
    }
}
