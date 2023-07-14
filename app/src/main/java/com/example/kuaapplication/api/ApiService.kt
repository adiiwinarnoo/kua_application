package com.example.kuaapplication.api

import com.example.kuaapplication.model.ResponseAddPemohon
import com.example.kuaapplication.model.ResponseLogin
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("/api/login")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<ResponseLogin>

    @FormUrlEncoded
    @POST("/api/register")
    fun register (
        @Field("name") name : String,
        @Field("username") userName : String,
        @Field("email") email : String,
        @Field("phone") phone : String,
        @Field("password") password : String,
        @Field("alamat") alamat : String,
        @Field("jenis_kelamin") jenisKelamin : String
    ) : Call<ResponseLogin>

    @Multipart
    @POST("/api/createPemohon")
    fun addPermohonan(
        @Part("tanggal_nikah") tanggalNikah : String,
        @Part("jam_nikah") jamNikah : String,
        @Part("lokasi_id") idLokasi : Int,
        @Part("nama_pria") namaPria : String,
        @Part("no_ktp_pria") noKtpPria : Int,
        @Part("tempat_tanggal_lahir") tempatTanggalLahir : String,
        @Part("alamat") alamat : String,
        @Part("id_status_pria") idStatusPria : Int,
        @Part("nama_wanita") namaWanita : String,
        @Part("no_ktp_wanita") noKtpWanita : Int,
        @Part("tempat_tanggal_lahir_wanita") tempatTanggalLahirWanita : String,
        @Part("alamat_wanita") alamatWanita : String,
        @Part("id_status_wanita") idStatusWanita : Int,
        @Part("nama_wali") namaWali : String,
        @Part("status_wali") statusWali : String,
        @Part("mas_kawin") masKawin : String,
        @Part("nama_saksi") namaSaksi : String,
        @Part("tanggal_lahir_saksi") tanggalLahirSaksi : String,
        @Part("alamat_saksi") alamatSaksi : String,
        @Part berkas_persyaratan : MultipartBody.Part,
        @Part foto_wanita : MultipartBody.Part,
        @Part foto_pria : MultipartBody.Part
        ) : Call<ResponseAddPemohon>

}