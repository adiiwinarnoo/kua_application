package com.example.kuaapplication.localDb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "PengantinPria")
@Parcelize
data class DatabasePermohonan(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int? = 0,

    @ColumnInfo(name = "tanggal_nikah")
    val tanggalNikah : String = "",

    @ColumnInfo(name = "jam_nikah")
    val jamNikah : String = "",

    @ColumnInfo(name = "lokasi_nikah")
    val lokasiNikah : Int = 0,

    @ColumnInfo(name = "nama_pria")
    val namaPria : String = "",

    @ColumnInfo(name = "nomor_ktp_pria")
    val noKtpPria : Int = 0,

    @ColumnInfo(name = "tempat_tanggal_lahir_pria")
    val tempatTanggalLahirPria : String = "",

    @ColumnInfo(name = "alamat")
    val alamat : String = "",

    @ColumnInfo(name = "status_pria")
    val statusPria : Int = 0

) : Parcelable

@Entity(tableName = "PengantinWanita")
@Parcelize
data class PengantinWanita(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int? = 0,

    @ColumnInfo(name = "nama_wanita")
    val namaWanita : String = "",

    @ColumnInfo(name = "nomor_ktp_wanita")
    val noKtpWanita : Int = 0,

    @ColumnInfo(name = "tempat_tanggal_lahir_wanita")
    val tempatTanggalLahirWanita : String = "",

    @ColumnInfo(name = "alamat")
    val alamat : String = "",

    @ColumnInfo(name = "status_wanita")
    val statusWanita : Int = 0

) : Parcelable

@Entity(tableName = "Wali")
@Parcelize
data class Wali(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int? = 0,

    @ColumnInfo(name = "foto_pria")
    val fotoPria : String = "",

    @ColumnInfo(name = "foto_wanita")
    val fotoWanita : String = "",

    @ColumnInfo(name = "berkas_file")
    val berkasFile : String = "",

    @ColumnInfo(name = "mas_kawin")
    val masKawin : String = "",

    @ColumnInfo(name = "nama_wali")
    val namaWali : String = "",

    @ColumnInfo(name = "alamat_wali")
    val alamatWali : String = "",

    @ColumnInfo(name = "hubungan_wali")
    val hubunganWali : String = "",

) : Parcelable

@Entity(tableName = "Saksi")
@Parcelize
data class Saksi(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int? = 0,

    @ColumnInfo(name = "nama_saksi")
    val namaSaksi : String = "",

    @ColumnInfo(name = "tempat_tanggal_lahir_saksi")
    val tempatTanggalLahirSaksi : String = "",

    @ColumnInfo(name = "alamat_saksi")
    val alamat : String = "",

) : Parcelable
