package com.example.salessparrow.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authorization_table")
data class AuthorizationEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "authCode")
    val authCode: String
)
