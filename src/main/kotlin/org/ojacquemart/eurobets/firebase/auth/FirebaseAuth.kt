package org.ojacquemart.eurobets.firebase.auth

import com.firebase.client.AuthData
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import org.ojacquemart.eurobets.firebase.config.FirebaseRef
import rx.Observable
import rx.lang.kotlin.onError

object FirebaseAuth {

    /**
     * Blocks the auth while the state is not resolved
     * It is necessary to be sure that all services calls will be authed to firebase.
     *
     * @see https://www.firebase.com/docs/web/guide/login/custom.html
     */
    fun blockingAuthWithCustomToken(ref: FirebaseRef) {
        authWithCustomToken(ref)
            .onError { System.exit(-1) }
            .toBlocking().first()
    }

    fun authWithCustomToken(ref: FirebaseRef): Observable<AuthData> {
        return Observable.create { subscriber ->
            ref.firebase.authWithCustomToken(ref.settings.secret, object : Firebase.AuthResultHandler {
                override fun onAuthenticated(authData: AuthData) {
                    println("Auth OK on ${ref.settings.app}!")
                    subscriber.onNext(authData)
                }

                override fun onAuthenticationError(firebaseError: FirebaseError) {
                    println("Auth KO on ${ref.settings.app}!")
                    subscriber.onError(firebaseError.toException())
                }
            })
        }
    }

}