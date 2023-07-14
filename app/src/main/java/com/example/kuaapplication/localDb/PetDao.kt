package com.example.kuaapplication.localDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.*
import com.example.kuaapplication.model.PengantinPria
import com.example.kuaapplication.model.PengantinWanita


@Dao
interface PetDao {
    // query for DatabasePetLocal
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item : DatabasePermohonan)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWanita(item : com.example.kuaapplication.localDb.PengantinWanita)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWali(item : Wali)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSaksi(item : Saksi)

    @Query("SELECT * FROM PengantinPria")
    fun getAllDataPria() : List<DatabasePermohonan>

    @Query("SELECT * FROM PengantinWanita")
    fun getAllDataWanita() : List<com.example.kuaapplication.localDb.PengantinWanita>

    @Query("SELECT * FROM Wali")
    fun getAllDataWali() : List<com.example.kuaapplication.localDb.Wali>

    @Query("SELECT * FROM Saksi")
    fun getAllDataSaksi() : List<com.example.kuaapplication.localDb.Saksi>

}