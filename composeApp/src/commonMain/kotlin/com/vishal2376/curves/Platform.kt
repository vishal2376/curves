package com.vishal2376.curves

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform