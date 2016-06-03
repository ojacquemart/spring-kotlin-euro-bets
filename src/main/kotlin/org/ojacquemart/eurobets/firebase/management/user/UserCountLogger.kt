package org.ojacquemart.eurobets.firebase.management.user

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.rx.RxFirebase
import org.ojacquemart.eurobets.lang.loggerFor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
open class UserCountLogger(@Autowired val ref: FirebaseRef) {

    private val log = loggerFor<UserCountLogger>()

    @PostConstruct
    fun init() {
        log()
    }

    fun log() {
        RxFirebase.observe(this.ref.firebase.child(Collections.users))
                .map { ds -> ds!!.childrenCount }
                .subscribe { counter -> log.info("Users count: $counter") }
    }

}