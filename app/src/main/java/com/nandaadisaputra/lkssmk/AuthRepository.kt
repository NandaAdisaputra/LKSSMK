package com.nandaadisaputra.lkssmk

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class AuthRepository {

    // Fungsi untuk login, mengirim email & password ke server
    fun login(email: String, password: String): User? {
        try {
            // URL API lokal yang bisa diakses dari emulator
            val url = URL("http://10.0.2.2:5000/api/auth")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.doOutput = true

            // Menyiapkan data email dan password dalam format JSON
            val data = """
                {
                    "email": "$email",
                    "password": "$password"
                }
            """.trimIndent()

            // Kirim data ke server
            conn.outputStream.use { it.write(data.toByteArray()) }

            // Cek respon dari server, jika sukses (200)
            if (conn.responseCode == 200) {
                val result = conn.inputStream.bufferedReader().readText() // Baca hasil response
                val json = JSONObject(result) // Ubah hasil menjadi JSON

                // Kembalikan objek User jika login berhasil
                return User(
                    id = json.getInt("id"),
                    name = json.getString("name"),
                    email = json.getString("email"),
                    division = json.getString("division")
                )
            } else {
                // Tampilkan error jika response tidak sukses
                println("Login Gagal: ${conn.responseCode} - ${conn.responseMessage}")
            }
        } catch (e: Exception) {
            // Tampilkan pesan kesalahan jika ada masalah saat koneksi
            println("Error: ${e.message}")
        }

        // Jika login gagal, kembalikan null
        return null
    }
}
