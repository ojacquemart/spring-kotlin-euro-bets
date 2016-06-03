package org.ojacquemart.eurobets.firebase.management.league

import org.ojacquemart.eurobets.lang.loggerFor
import java.io.FileOutputStream
import java.util.*

object ImageWriter {

    private val log = loggerFor<ImageWriter>()

    fun write(img: ImageBase64) {
        log.debug("Write image to fs")

        val data = Base64.getDecoder().decode(img.content)
        FileOutputStream("${img.getFilename()}").let { it -> it.write(data) }
    }

}
