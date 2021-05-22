package com.fefustub.rzdapp

import java.time.Instant
import java.time.OffsetDateTime
import java.util.*

class Incident(
    var STATUS: String? = null,
    var TICKETID: String? = null,
    var DESCRIPTION: String? = null,
    var ISKNOWNERRORDATE: String? = null,
    var TARGETFINISH: String?,
    var EXTSYSNAME: String? = null
) {
    var ERRORDATE: Date? = null
    var TARGETFINISHDATE: Date? = null

    init {
        if (ISKNOWNERRORDATE != null) ERRORDATE =
            Date.from(Instant.from(OffsetDateTime.parse(ISKNOWNERRORDATE!!)))
        if (TARGETFINISH != null) TARGETFINISHDATE =
            Date.from(Instant.from(OffsetDateTime.parse(TARGETFINISH!!)))
    }
}