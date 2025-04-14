package com.nandaadisaputra.lkssmk

// Data class untuk menyimpan informasi User
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val division: String
)

// Data class untuk menyimpan informasi kandidat voting
data class Candidate(
    val voting_candidate_id: Int,
    val name: String,
    val division: String,
    val photo: String
)
