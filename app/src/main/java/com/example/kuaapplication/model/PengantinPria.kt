package com.example.kuaapplication.model

data class PengantinPria(
    val tanggalNikah : String,
    val jamNikah : String,
    val lokasiNikah : Int,
    val namaPria : String,
    val nomorKtpPria : Int,
    val tempatTanggalLahir : String,
    val alamatPria : String,
    val statusPria : String
)
data class PengatinWanita(
    val namaWanita : String,
    val nomorKtpWanita : Int,
    val tempatTanggalLahirWanita : String,
    val alamatWanita : String,
    val statusWanita : String
)
data class Saksi(
    val namaSaksi : String,
    val tempatTanggalLahirSaksi : String,
    val alamatSaksi : String,
)
data class Wali(
    val fotoPria : String,
    val fotoWanita : String,
    val berkas : String,
    val masKawin : String,
    val namaWali : String,
    val alamatWali : String,
    val hubunganWali : String
)
