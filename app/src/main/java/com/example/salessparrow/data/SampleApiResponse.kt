package com.example.salessparrow.data

fun sampleApiResponse(): DataWrapper {
    val recordList = listOf(
        Record(
            attributes = Attribute("Account", "/services/data/v58.0/sobjects/Account/0011e00000bVSCyAAO"),
            Id = "0011e00000bVSCyAAO",
            Name = "Bookkeeper360"
        ),
        Record(
            attributes = Attribute("Account", "/services/data/v58.0/sobjects/Account/0011e00000bVSCFAA4"),
            Id = "0011e00000bVSCFAA4",
            Name = "Bikky",

        ),
        Record(
            attributes = Attribute("Account", "/services/data/v58.0/sobjects/Account/0011e00000bVRp8AAG"),
            Id = "0011e00000bVRp8AAG",
            Name = "Pikkit"
        ),
        Record(
            attributes = Attribute("Account", "/services/data/v58.0/sobjects/Account/0011e00000bVRqDAAW"),
            Id = "0011e00000bVRqDAAW",
            Name = "Dukkantek"
        ),
        Record(
            attributes = Attribute("Account", "/services/data/v58.0/sobjects/Account/0011e00000bVRilAAG"),
            Id = "0011e00000bVRilAAG",
            Name = "Akkio"
        )
    )

    val body = Body(
        totalSize = recordList.size,
        done = true,
        records = recordList
    )

    val compositeResponse = CompositeResponse(
        body = body,
        httpHeaders = emptyMap(),
        httpStatusCode = 200,
        referenceId = "getAccounts"
    )

    return DataWrapper(compositeResponse = listOf(compositeResponse))
}
