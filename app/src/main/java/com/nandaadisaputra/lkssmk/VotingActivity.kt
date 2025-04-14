package com.nandaadisaputra.lkssmk

import CandidateAdapter
import CandidateRepository
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VotingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting)

        // Menghubungkan RecyclerView dan ProgressBar dari layout
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        // Menampilkan ProgressBar saat data sedang dimuat
        progressBar.visibility = ProgressBar.VISIBLE

        // Mengambil data kandidat dari API menggunakan CandidateRepository
        CandidateRepository { candidates ->
            // Setelah data selesai dimuat, sembunyikan ProgressBar
            progressBar.visibility = ProgressBar.GONE

            // Menampilkan data kandidat di RecyclerView
            val adapter = CandidateAdapter(candidates, this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }.execute()
    }
}
