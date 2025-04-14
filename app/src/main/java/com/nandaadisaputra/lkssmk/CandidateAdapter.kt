import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nandaadisaputra.lkssmk.Candidate
import com.nandaadisaputra.lkssmk.R
import java.net.HttpURLConnection
import java.net.URL

// Adapter untuk menampilkan daftar kandidat dalam RecyclerView
class CandidateAdapter(private val candidates: List<Candidate>, private val context: Context) :
    RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>() {

    // Membuat ViewHolder untuk setiap item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_candidate, parent, false)
        return CandidateViewHolder(view)
    }

    // Mengisi data kandidat ke dalam tampilan item
    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        val candidate = candidates[position]

        // Mengisi nama dan divisi kandidat
        holder.nameTextView.text = candidate.name
        holder.divisionTextView.text = candidate.division

        // Menampilkan foto kandidat
        val imageUrl = "http://10.0.2.2:5000/images/${candidate.photo}" // URL gambar
        loadImageFromUrl(holder.photoImageView, imageUrl)
    }

    // Mengembalikan jumlah item dalam daftar
    override fun getItemCount(): Int = candidates.size

    // ViewHolder untuk item kandidat
    class CandidateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.candidate_name)
        val divisionTextView: TextView = itemView.findViewById(R.id.candidate_division)
        val photoImageView: ImageView = itemView.findViewById(R.id.candidate_photo)
    }

    // Fungsi untuk mengunduh gambar dari URL menggunakan HttpURLConnection
    private fun loadImageFromUrl(imageView: ImageView, urlString: String) {
        Thread {
            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val inputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Menampilkan gambar di UI thread
                imageView.post {
                    imageView.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace() // Menangani error jika terjadi masalah saat mengunduh gambar
            }
        }.start() // Menjalankan proses di background thread
    }
}
