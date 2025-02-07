package com.example.myapplication.adblocker

fun sanitizeUrl(urlString: String): String {
    var sanitizedUrlString = urlString
    sanitizedUrlString = SanitizeUrlHelper.sanitizeTrackingQueries(sanitizedUrlString)
    sanitizedUrlString = SanitizeUrlHelper.sanitizeAmpRedirects(sanitizedUrlString)
    return sanitizedUrlString
}


private val trackingQueriesList = listOf(
    "__hsfp=",             // HubSpot.
    "__hssc=",             // HubSpot.
    "__hstc=",             // HubSpot.
    "__s=",                // Drip.com.
    "_hsenc=",             // HubSpot.
    "_openstat=",          // Yandex.
    "dclid=",              // DoubleClick ID.
    "fbadid=",             // FaceBook Ad ID.
    "fbclid=",             // FaceBook Click ID.
    "gclid=",              // Google Click ID.
    "hsCtaTracking=",      // HubSpot.
    "igshid=",             // Instagram.
    "mc_eid=",             // MailChimp Email ID.
    "?mkt_tok=",           // Adobe Marketo.
    "ml_subscriber=",      // MailerLite.
    "ml_subscriber_hash=", // MailerLite.
    "msclkid=",            // Microsoft Click ID.
    "oly_anon_id=",        // Omeda Anonymous ID.
    "oly_enc_id=",         // Omeda ID.
    "rb_clickid=",         // Unknown tracker.
    "s_cid=",              // Adobe Site Catalyst.
    "utm_",                // Google Analytics.
    "vero_conv=",          // Vero.
    "vero_id=",            // Vero ID.
    "wickedid=",           // Wicked Reports ID.
    "yclid="               // Yandex Click ID.
)

object SanitizeUrlHelper {
    fun sanitizeTrackingQueries(inputUrl: String): String {
        // Make a copy of the input URL so that it can be modified.
        var url = inputUrl

        // Remove each tracking query from the URL.
        trackingQueriesList.forEach {
            if (url.contains("?$it")) {  // Check for an initial query
                // Remove the first query and anything after it.
                url = url.substring(0, url.indexOf("?$it"))
            } else if (url.contains("&$it")) {  // Check for a subsequent query.
                // Remove the query and anything after it.
                url = url.substring(0, url.indexOf("&$it"))
            }
        }

        // Return the sanitized URL.
        return url
    }

    fun sanitizeAmpRedirects(inputUrl: String): String {
        // Make a copy of the input URL so that it can be modified.
        var url = inputUrl

        // Remove Twitter `amp=1`.
        if (url.contains("?amp"))
            url = url.substring(0, url.indexOf("?amp"))
        else if (url.contains("&amp"))
            url = url.substring(0, url.indexOf("&amp"))

        // Return the sanitized URL.
        return url
    }
}
