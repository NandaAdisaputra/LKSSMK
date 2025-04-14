package com.nandaadisaputra.lkssmk

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Menghubungkan ViewModel
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Menyambungkan EditText dan Button ke variabel
        val email = findViewById<EditText>(R.id.email)
        val pass = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.btnLogin)

        // Ketika tombol login diklik
        login.setOnClickListener {
            // Mengambil email dan password dari EditText
            val emailInput = email.text.toString()
            val passInput = pass.text.toString()

            // Memanggil fungsi login di ViewModel
            vm.login(emailInput, passInput)
        }

        // Menangani hasil login dari ViewModel
        vm.user.observe(this) { user ->
            if (user != null) {
                // Menampilkan pesan jika login berhasil
                Toast.makeText(this, "Login berhasil! Selamat datang, ${user.name}", Toast.LENGTH_LONG).show()
                // Pindah ke activity lain jika perlu
                // startActivity(Intent(this, DashboardActivity::class.java))
            }
        }

        // Menangani error jika login gagal
        vm.error.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}

