package org.ojacquemart.eurobets.firebase.management.league

import org.ojacquemart.eurobets.firebase.Collections
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import org.ojacquemart.eurobets.firebase.config.FirebaseSettings
import org.ojacquemart.eurobets.firebase.rx.RxFirebase
import org.springframework.stereotype.Component
import rx.Observable
import javax.annotation.PostConstruct

@Component
class ImageModeration(val ref: FirebaseRef, val mailSender: MailSender<League>) {

    @PostConstruct
    fun init() {
        moderate()
    }

    private fun moderate() {
        println("Leagues images moderation")

        RxFirebase.observeList(this.ref.firebase.child(Collections.leagues), League::class.java)
                .flatMapIterable { it -> it }
                .filter { it -> it.needsModeration() }
                .flatMap { it -> copyWithImageAsDataUrl(it) }
                .map { it -> getMessage(ref.settings, it) }
                .subscribe { it ->
                    println("Send mail for ${it.item.name}")
                    mailSender.sendMail(it)
                }
    }

    fun copyWithImageAsDataUrl(league: League): Observable<League> {
        return RxFirebase.observe(this.ref.firebase.child(Collections.leaguesImages).child("${league.image}"))
                .map { ds -> ds.value.toString() }
                .map { value -> league.copy(image = value) }
    }

    fun getMessage(firebaseSettings: FirebaseSettings, league: League): Mail<League> {
        return Mail(subject = "[euro-bets] ${league.slug} moderation",
                content = """
                This team needs a moderation operation.
                <br />
                <a href="https://${firebaseSettings.app}.firebaseio.com/leagues/${league.slug}/imageModerated">Moderate</a>
                """,
                item = league, imageBase64 = ImageBase64.create(league.image, "/tmp/", league.slug))
    }

}

