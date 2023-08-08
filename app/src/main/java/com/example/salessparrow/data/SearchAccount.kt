package com.example.salessparrow.data

data class Attribute(
    val type: String,
    val url: String
)

data class Record(
    val attributes: Attribute,
    val Id: String,
    val Name: String
)

data class Body(
    val totalSize: Int,
    val done: Boolean,
    val records: List<Record>
)

data class CompositeResponse(
    val body: Body,
    val httpHeaders: Map<String, String>,
    val httpStatusCode: Int,
    val referenceId: String
)

data class DataWrapper(
    val compositeResponse: List<CompositeResponse>
)

data class RecordInfo(val name: String, val attributes: Attribute)
