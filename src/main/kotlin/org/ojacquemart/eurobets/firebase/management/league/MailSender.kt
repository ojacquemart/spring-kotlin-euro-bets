package org.ojacquemart.eurobets.firebase.management.league

import org.ojacquemart.eurobets.firebase.config.MailingSettings
import org.springframework.stereotype.Component
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.Message
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

@Component
class MailSender<T>(val mailingSettings: MailingSettings) {

    fun sendMail(mail: Mail<T>) {
        val session = mailingSettings.session()

        try {
            val mimeMessage = MimeMessage(session)
            mimeMessage.setFrom(InternetAddress(mailingSettings.from))
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailingSettings.to))
            mimeMessage.setSubject(mail.subject)
            mimeMessage.setContent(mail.content, "text/html charset=utf8")
            mimeMessage.setContent(getMultipart(mail))

            Transport.send(mimeMessage)
            println("Email sent!")

        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun getMultipart(mail: Mail<T>): MimeMultipart {
        val messageBodyPart = MimeBodyPart()
        messageBodyPart.setContent(mail.content, "text/html")

        val multipart = MimeMultipart()
        multipart.addBodyPart(messageBodyPart)

        attachImageIfPresent(multipart, mail)

        return multipart
    }

    fun attachImageIfPresent(multipart: MimeMultipart, mail: Mail<T>) {
        mail.imageBase64?.let { image ->
            ImageWriter.write(image)

            val imagePart = MimeBodyPart()

            val imageFilename = image.getFilename()
            val source = FileDataSource(imageFilename)
            imagePart.setDataHandler(DataHandler(source))
            imagePart.setFileName(imageFilename)

            // Trick is to add the content-id header here
            imagePart.setHeader("Content-ID", "image_id")

            multipart.addBodyPart(imagePart)

            val attachmentPart = MimeBodyPart()
            attachmentPart.setContent("Attachment <img src='cid:image_id'>", "text/html")

            multipart.addBodyPart(attachmentPart)
        }
    }

}
