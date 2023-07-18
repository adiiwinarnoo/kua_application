package com.example.kuaapplication.api

import com.example.kuaapplication.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @GET("/api/get-pelatihan")
    fun getJadwalPelatihan() : Call<ResponseGetJadwalPelatihan>

    @GET("/api/jawdwal-nikah")
    fun getJadwalNikah() : Call<ResponseGetJadwalNikah>

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
    @POST("/api/payment")
    fun uploadPayment(
        @Part("user_id") userId : RequestBody?,
        @Part url_file : MultipartBody.Part,
        @Part("nominal") nominal : RequestBody?,
        @Part("tanggal_bayar") tanggalBayar : RequestBody?
    ) : Call<ResponsePayment>

    @Multipart
    @POST("/api/createPemohon")
    fun addPermohonan(
        @Part("tanggal_nikah") tanggalNikah : RequestBody?,
        @Part("jam_nikah") jamNikah : RequestBody?,
        @Part("lokasi_id") idLokasi : RequestBody?,
        @Part("nama_pria") namaPria : RequestBody?,
        @Part("no_ktp_pria") noKtpPria : RequestBody?,
        @Part("tempat_tanggal_lahir_pria") tempatTanggalLahir : RequestBody?,
        @Part("alamat") alamat : RequestBody?,
        @Part("id_status_pria") idStatusPria : RequestBody?,
        @Part("nama_wanita") namaWanita : RequestBody?,
        @Part("nomor_ktp_wanita") noKtpWanita : RequestBody?,
        @Part("tempat_tanggal_lahir_wanita") tempatTanggalLahirWanita : RequestBody?,
        @Part("alamat_wanita") alamatWanita : RequestBody?,
        @Part("id_status_wanita") idStatusWanita : RequestBody?,
        @Part("nama_wali") namaWali : RequestBody?,
        @Part("status_wali") statusWali : RequestBody?,
        @Part("mas_kawin") masKawin : RequestBody?,
        @Part("nama_saksi") namaSaksi : RequestBody?,
        @Part("tanggal_lahir_saksi") tanggalLahirSaksi : RequestBody?,
        @Part("alamat") alamatSaksi : RequestBody?,
        @Part berkas_persyaratan : MultipartBody.Part,
        @Part foto_wanita : MultipartBody.Part,
        @Part foto_pria : MultipartBody.Part
        ) : Call<ResponseAddPemohon>

}