package com.example.kuaapplication.model

import android.os.Parcel
import android.os.Parcelable

data class PengantinPria(
    val tanggalNikah : String?,
    val jamNikah : String?,
    val lokasiNikah : Int?,
    val namaPria : String?,
    val nomorKtpPria : Int?,
    val tempatTanggalLahir : String?,
    val alamatPria : String?,
    val statusPria : Int?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun describeContents(): Int {
        return  0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(tanggalNikah)
        parcel.writeString(jamNikah)
        parcel.writeString(lokasiNikah.toString())
        parcel.writeString(namaPria)
        parcel.writeString(nomorKtpPria.toString())
        parcel.writeString(tempatTanggalLahir)
        parcel.writeString(alamatPria)
        parcel.writeString(statusPria.toString())
    }

    companion object CREATOR : Parcelable.Creator<PengantinPria> {
        override fun createFromParcel(parcel: Parcel): PengantinPria {
            return PengantinPria(parcel)
        }

        override fun newArray(size: Int): Array<PengantinPria?> {
            return arrayOfNulls(size)
        }
    }

}


data class PengantinWanita(
    val namaWanita : String?,
    val nomorKtpWanita : Int,
    val tempatTanggalLahirWanita : String?,
    val alamatWanita : String?,
    val statusWanita : Int
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(namaWanita)
        parcel.writeString(nomorKtpWanita.toString())
        parcel.writeString(tempatTanggalLahirWanita)
        parcel.writeString(alamatWanita)
        parcel.writeString(statusWanita.toString())
    }

    companion object CREATOR : Parcelable.Creator<PengantinWanita> {
        override fun createFromParcel(parcel: Parcel): PengantinWanita {
            return PengantinWanita(parcel)
        }

        override fun newArray(size: Int): Array<PengantinWanita?> {
            return arrayOfNulls(size)
        }
    }

}


data class Saksi(
    val namaSaksi : String?,
    val tempatTanggalLahirSaksi : String?,
    val alamatSaksi : String?,
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(namaSaksi)
        parcel.writeString(tempatTanggalLahirSaksi)
        parcel.writeString(alamatSaksi)
    }

    companion object CREATOR : Parcelable.Creator<Saksi> {
        override fun createFromParcel(parcel: Parcel): Saksi {
            return Saksi(parcel)
        }

        override fun newArray(size: Int): Array<Saksi?> {
            return arrayOfNulls(size)
        }
    }

}
data class Wali(
    val fotoPria: String?,
    val fotoWanita: String?,
    val berkas: String?,
    val masKawin: String?,
    val namaWali: String?,
    val alamatWali: String?,
    val hubunganWali: String?
) : Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(fotoPria)
        parcel.writeString(fotoWanita)
        parcel.writeString(berkas)
        parcel.writeString(masKawin)
        parcel.writeString(namaWali)
        parcel.writeString(alamatWali)
        parcel.writeString(hubunganWali)
    }

    companion object CREATOR : Parcelable.Creator<Wali> {
        override fun createFromParcel(parcel: Parcel): Wali {
            return Wali(parcel)
        }

        override fun newArray(size: Int): Array<Wali?> {
            return arrayOfNulls(size)
        }
    }

}
