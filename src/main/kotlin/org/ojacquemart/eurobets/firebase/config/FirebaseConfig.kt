package org.ojacquemart.eurobets.firebase.config

import com.firebase.client.Firebase
import org.ojacquemart.eurobets.firebase.auth.FirebaseAuth
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.Assert

@Configuration
open class FirebaseConfig : InitializingBean {

    private val log = loggerFor<FirebaseConfig>()

    @Autowired
    lateinit var settings: FirebaseSettings

    @Bean
    open fun firebase(): Firebase {
        Assert.notNull(settings.app)
        Assert.notNull(settings.secret)

        log.debug("Setup firebase bean for ${settings.app}")

        return Firebase("https://${settings.app}.firebaseio.com")
    }

    @Bean
    open fun firebaseRef(): FirebaseRef {
        return FirebaseRef(firebase(), settings)
    }

    override fun afterPropertiesSet() {
        log.debug("Auth state init...")

        FirebaseAuth.blockingAuthWithCustomToken(firebaseRef())
    }
}