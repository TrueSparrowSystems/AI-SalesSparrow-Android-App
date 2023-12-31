package com.truesparrow.sales.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.truesparrow.sales.entity.AuthorizationEntity

@Dao
interface AuthorizationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthorization(authorizationEntity: AuthorizationEntity)

    @Query("SELECT * FROM authorization_table LIMIT 1")
    fun getAuthorization(): LiveData<AuthorizationEntity>
}
