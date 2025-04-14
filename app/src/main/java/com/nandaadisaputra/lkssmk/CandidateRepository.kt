import android.os.AsyncTask
import com.nandaadisaputra.lkssmk.Candidate
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

// CandidateRepository adalah kelas yang bertanggung jawab untuk mengambil data kandidat dari API menggunakan AsyncTask
class CandidateRepository(val callback: (List<Candidate>) -> Unit) : AsyncTask<Void, Void, List<Candidate>>() {

    // doInBackground adalah fungsi yang berjalan di background thread untuk mengambil data dari API
    override fun doInBackground(vararg params: Void?): List<Candidate> {
        val urlString = "http://10.0.2.2:5000/api/voting-candidates/1"  // URL API yang digunakan untuk mengambil data kandidat
        val candidatesList = mutableListOf<Candidate>()  // List untuk menyimpan data kandidat yang diambil

        try {
            // Membuat koneksi ke URL API
            val url = URL(urlString)  // Membuat objek URL dari string
            val connection = url.openConnection() as HttpURLConnection  // Membuka koneksi HTTP
            connection.requestMethod = "GET"  // Menggunakan metode GET untuk meminta data
            connection.connect()  // Menjalin koneksi ke server

            // Membaca response dari API menggunakan BufferedReader
            val reader = BufferedReader(InputStreamReader(connection.inputStream))  // Membaca data dari input stream
            val response = StringBuilder()  // StringBuilder untuk menyimpan hasil response API

            var line: String?  // Variabel untuk menampung setiap baris response
            while (reader.readLine().also { line = it } != null) {  // Membaca data baris per baris
                response.append(line)  // Menambahkan setiap baris ke response
            }

            // Mengubah response menjadi string dan mengonversinya ke dalam format JSON
            val jsonResponse = response.toString()  // Mengubah response menjadi string
            val jsonArray = JSONArray(jsonResponse)  // Mengubah string JSON menjadi array JSON

            // Melakukan iterasi untuk setiap objek kandidat dalam array JSON
            for (i in 0 until jsonArray.length()) {
                val candidateObject = jsonArray.getJSONObject(i)  // Mengambil objek kandidat dari array JSON

                // Mengonversi data JSON kandidat menjadi objek Candidate
                val candidate = Candidate(
                    voting_candidate_id = candidateObject.getInt("voting_candidate_id"),  // Mengambil ID kandidat
                    name = candidateObject.getString("name"),  // Mengambil nama kandidat
                    division = candidateObject.getString("division"),  // Mengambil divisi kandidat
                    photo = candidateObject.getString("photo")  // Mengambil URL foto kandidat
                )
                candidatesList.add(candidate)  // Menambahkan objek kandidat ke dalam list
            }
        } catch (e: Exception) {
            e.printStackTrace()  // Menangani error jika terjadi kesalahan
        }

        return candidatesList  // Mengembalikan list kandidat yang sudah diambil
    }

    // onPostExecute dipanggil setelah data selesai diambil dan akan dijalankan di main thread
    override fun onPostExecute(result: List<Candidate>) {
        super.onPostExecute(result)
        callback(result)  // Memanggil callback dan mengirimkan hasil ke UI thread (untuk memperbarui UI)
    }
}
