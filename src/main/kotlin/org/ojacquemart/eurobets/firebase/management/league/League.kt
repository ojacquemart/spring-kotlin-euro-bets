package org.ojacquemart.eurobets.firebase.management.league

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class League(val slug: String = "",
                  val name: String = "",
                  val description: String = "",
                  val image: String = "",
                  val imageModerated: Boolean = false,
                  val members: Map<String, Boolean> = mapOf()) {

    fun needsModeration() = this.image.isNotEmpty() && this.imageModerated.not()

}
