package org.ojacquemart.eurobets.firebase.management.league

import java.io.FileOutputStream
import java.util.*

object ImageWriter {

    fun write(img: ImageBase64) {
        println("Write image to fs")

        val data = Base64.getDecoder().decode(img.content)
        FileOutputStream("${img.getFilename()}").let { it -> it.write(data) }
    }

}
