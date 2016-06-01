package org.ojacquemart.eurobets.firebase.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "scheduling")
open class SchedulingSettings {

    var cronStarted: String = ""
    var cronMatches: String = ""

}
