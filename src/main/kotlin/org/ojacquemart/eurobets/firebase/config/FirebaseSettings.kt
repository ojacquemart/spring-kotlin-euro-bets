package org.ojacquemart.eurobets.firebase.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "firebase")
open class FirebaseSettings {

    var app: String = ""
    var secret = ""

    override fun toString(): String {
        return "FirebaseProperties(app='$app',secret='$secret')"
    }

}
