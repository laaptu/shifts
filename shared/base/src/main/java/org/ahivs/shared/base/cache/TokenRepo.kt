package org.ahivs.shared.base.cache

import javax.inject.Inject

class TokenRepo @Inject constructor() {
    fun getAuthenticatedHeader(): Map<String, String> = mapOf(
        "Content-Type" to "application/json",
        "Authorization" to "Deputy 117a211f76f0aa5fbc9b7143b793fdbaf7088c03"
    )
}

