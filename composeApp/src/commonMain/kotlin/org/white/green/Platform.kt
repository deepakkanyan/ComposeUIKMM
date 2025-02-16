package org.white.green

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform