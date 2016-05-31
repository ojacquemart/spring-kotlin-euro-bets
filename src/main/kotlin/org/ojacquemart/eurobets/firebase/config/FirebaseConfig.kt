package org.ojacquemart.eurobets.firebase.config

import com.firebase.client.Firebase
import org.ojacquemart.eurobets.firebase.auth.FirebaseAuth
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.Assert

@Configuration
open class FirebaseConfig : InitializingBean {

    @Autowired
    lateinit var settings: FirebaseSettings

    @Bean
    open fun firebase(): Firebase {
        Assert.notNull(settings.app)
        Assert.notNull(settings.secret)

        return Firebase("https://${settings.app}.firebaseio.com")
    }

    @Bean
    open fun firebaseRef(): FirebaseRef {
        return FirebaseRef(firebase(), settings)
    }

    override fun afterPropertiesSet() {
        println("Auth state init...")

        FirebaseAuth.blockingAuthWithCustomToken(firebaseRef())
    }
}