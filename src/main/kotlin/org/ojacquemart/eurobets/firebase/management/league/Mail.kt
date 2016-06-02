package org.ojacquemart.eurobets.firebase.management.league

data class Mail<T>(val subject: String, val content: String, val imageBase64: ImageBase64?, val item: T)