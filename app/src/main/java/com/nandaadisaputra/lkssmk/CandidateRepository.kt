import android.os.AsyncTask
import com.nandaadisaputra.lkssmk.Candidate
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

// Repository untuk mengambil data kandidat dari API
class CandidateRepository(val callback: (List<Candidate>) -> Unit) : AsyncTask<Void, Void, List<Candidate>>() {

    // Fungsi untuk mengambil data kandidat di background thread
    override fun doInBackground(vararg params: Void?): List<Candidate> {
        val urlString = "http://10.0.2.2:5000/api/voting-candidates/1"  // URL API
        val candidatesList = mutableListOf<Candidate>()  // List untuk menyimpan data kandidat

        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"  // Menggunakan metode GET
            connection.connect()  // Menjalin koneksi ke API

            // Membaca data response dari API
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            // Mengubah response menjadi JSON array
            val jsonResponse = response.toString()
            val jsonArray = JSONArray(jsonResponse)

            // Mengonversi setiap item di JSON menjadi objek Candidate
            for (i in 0 until jsonArray.length()) {
                val candidateObject = jsonArray.getJSONObject(i)
                val candidate = Candidate(
                    voting_candidate_id = candidateObject.getInt("voting_candidate_id"),
                    name = candidateObject.getString("name"),
                    division = candidateObject.getString("division"),
                    photo = candidateObject.getString("photo")
                )
                candidatesList.add(candidate)  // Menambahkan kandidat ke list
            }

        } catch (e: Exception) {
            e.printStackTrace()  // Menangani error jika terjadi masalah
        }

        return candidatesList  // Mengembalikan list kandidat
    }

    // Setelah data selesai diambil, menyampaikan hasil ke UI thread
    override fun onPostExecute(result: List<Candidate>) {
        super.onPostExecute(result)
        callback(result)  // Mengirimkan data ke UI melalui callback
    }
}
