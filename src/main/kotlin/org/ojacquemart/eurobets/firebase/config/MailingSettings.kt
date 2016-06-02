package org.ojacquemart.eurobets.firebase.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.*
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import javax.mail.Session

@Component
@ConfigurationProperties(prefix = "mailing.smtp")
open class MailingSettings {

    var host = ""
    var auth = true
    var port = -1
    var socketFactoryClass = "javax.net.ssl.SSLSocketFactory"
    var socketFactoryPort = -1
    var from = ""
    var to = ""
    var userName = ""
    var password = ""

    open fun session(): Session {
        val props = getProperties()
        val authenticator: Authenticator = getAuthenticator()

        return Session.getDefaultInstance (props, authenticator);
    }

    private fun getProperties(): Properties {
        val props = Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", socketFactoryPort);
        props.put("mail.smtp.socketFactory.class", socketFactoryClass);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.port", port);

        return props
    }

    private fun getAuthenticator(): Authenticator {
        val authenticator: Authenticator = object : Authenticator () {
            override fun getPasswordAuthentication(): PasswordAuthentication? {
                return PasswordAuthentication(userName, password)
            }
        }

        return authenticator
    }

}