package org.ojacquemart.eurobets.firebase.management.league

import org.ojacquemart.eurobets.lang.loggerFor
import java.util.regex.Pattern

class ImageBase64(val content: String, val directory: String, val name: String, val extension: String) {


    fun getFilename() = "$directory$name.$extension"

    companion object {
        private val log = loggerFor<ImageBase64>()

        fun create(imageDataUrl: String, directory: String, name: String): ImageBase64? {
            val imgParts = imageDataUrl.split(Pattern.compile(","))
            if (imgParts.size != 2) {
                log.debug("$imageDataUrl is not a valid base 64 image data url")

                return null
            }

            // when base 64 data as url, it is necessary to parse the data:image/ part to extract the extension
            // and to isolate the base 64 image content
            val imgMeta = imgParts[0]
            val imgExtension = imgMeta.split(Pattern.compile(";"))[0].replace("data:image/", "")
            val imgBase64 = imgParts[1]
            log.trace("Extract $imgMeta from the base 64 image data url")

            return ImageBase64(imgBase64, directory, name, imgExtension)
        }
    }

}
