package com.fefustub.rzdapp.datatypes

import android.text.SpannableString
import java.time.Instant
import java.time.OffsetDateTime
import java.util.*

class Incident(
    var STATUS: String? = null,
    var TICKETID: String? = null,
    var DESCRIPTION: String? = null,
    var ISKNOWNERRORDATE: String? = null,
    var TARGETFINISH: String? = null,
    var EXTSYSNAME: String? = null,
    var REPORTEDBY: String? = null,
    var CRITIC_LEVEL: String? = null,
    var NORM: String? = null,
    var CLASSIDMAIN: String? = null,
    var LNORM: String? = null
) {
    var searchableDesc : SpannableString = SpannableString.valueOf(DESCRIPTION)
    var ERRORDATE: Date? = null
    var TARGETFINISHDATE: Date? = null

    init {
        if (ISKNOWNERRORDATE != null) ERRORDATE =
            Date.from(Instant.from(OffsetDateTime.parse(ISKNOWNERRORDATE!!)))
        if (TARGETFINISH != null) TARGETFINISHDATE =
            Date.from(Instant.from(OffsetDateTime.parse(TARGETFINISH!!)))
    }
}