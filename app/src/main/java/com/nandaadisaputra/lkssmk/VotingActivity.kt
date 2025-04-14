package com.nandaadisaputra.lkssmk

import CandidateAdapter
import CandidateRepository
import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// VotingActivity adalah activity yang digunakan untuk menampilkan data kandidat di layar
class VotingActivity : AppCompatActivity() {

    // Mengambil instance dari CandidateViewModel dengan 'by viewModels()' yang memudahkan pengelolaan data
    private val candidateViewModel: CandidateViewModel by viewModels()  // Menggunakan ViewModel untuk mengelola data

    // onCreate dipanggil saat activity pertama kali dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting)  // Menentukan layout untuk activity ini

        // Menghubungkan RecyclerView dan ProgressBar dari layout menggunakan findViewById
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        // Menampilkan ProgressBar untuk memberi tahu pengguna bahwa data sedang dimuat
        progressBar.visibility = ProgressBar.VISIBLE

        // Mengamati perubahan data kandidat di ViewModel
        candidateViewModel.candidates.observe(this, Observer { candidates ->
            // Ketika data kandidat diterima (setelah pemanggilan API selesai),
            // sembunyikan ProgressBar dan tampilkan data di RecyclerView
            progressBar.visibility = ProgressBar.GONE  // Sembunyikan ProgressBar setelah data selesai dimuat

            // Membuat adapter untuk RecyclerView dan menghubungkannya dengan data kandidat
            val adapter = CandidateAdapter(candidates, this)
            recyclerView.layoutManager = LinearLayoutManager(this)  // Mengatur layout manager (vertikal)
            recyclerView.adapter = adapter  // Menetapkan adapter untuk RecyclerView
        })

        // Meminta ViewModel untuk mengambil data kandidat dari API
        candidateViewModel.fetchCandidates()  // Memanggil fungsi fetchCandidates() pada ViewModel untuk mendapatkan data
    }
}
