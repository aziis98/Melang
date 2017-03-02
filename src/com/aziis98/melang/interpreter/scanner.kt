package com.aziis98.melang.interpreter

/**
 * Created by aziis98 on 03/03/2017.
 */

class BasicScanner(val source: String, var carret: Int = 0) {

    fun jumpAll(predicate: (Char) -> Boolean): String {
        return StringBuilder().apply {
            var current = seeNextChar()
            while (predicate(current)) {
                append(current)
                carret++
                if (hasMore())
                    current = seeNextChar()
                else
                    break
            }
        }.toString()
    }

    fun seeNextChar(): Char {
        return source[carret]
    }

    fun assertNext(string: String) {
        assert(source.substring(carret, carret + string.length) == string)
        carret += string.length
    }

    fun getUntil(terminator: String): String {
        return source.substring(carret, source.indexOf(terminator, carret)).apply {
            carret += length + terminator.length
        }
    }

    fun isNext(string: String): Boolean {
        if (source.length < carret + string.length)
            return false
        else
            return source.substring(carret, carret + string.length) == string
    }

    fun hasMore(): Boolean {
        return carret < source.length
    }

    override fun toString(): String {
        return "BasicScanner(distanceFromEnd=${source.length - carret})"
    }
}