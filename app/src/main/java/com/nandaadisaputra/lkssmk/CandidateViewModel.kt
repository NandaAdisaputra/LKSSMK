package com.nandaadisaputra.lkssmk

import CandidateRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// CandidateViewModel bertanggung jawab untuk mengelola data kandidat
// dan menyediakan data tersebut kepada UI (Activity/Fragment)
class CandidateViewModel : ViewModel() {

    // LiveData digunakan untuk menyimpan data kandidat yang dapat diamati
    // MutableLiveData memungkinkan data untuk diubah, sedangkan LiveData hanya bisa dibaca
    private val _candidates = MutableLiveData<List<Candidate>>()

    // Public getter untuk _candidates, memberikan akses data kandidat sebagai LiveData
    val candidates: LiveData<List<Candidate>> get() = _candidates

    // Fungsi untuk mengambil data kandidat dari API
    fun fetchCandidates() {
        // Memanggil CandidateRepository untuk mengambil data kandidat di background thread
        // CandidateRepository akan menangani pengambilan data dari API
        CandidateRepository { candidateList ->
            // Setelah data kandidat diterima, posting data ke LiveData
            // _candidates.postValue() akan mengupdate LiveData secara thread-safe
            _candidates.postValue(candidateList)  // Mengubah nilai LiveData dengan data kandidat
        }.execute()  // Menjalankan operasi asinkron untuk mengambil data
    }
}
