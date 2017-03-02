package com.aziis98.melang.interpreter

/**
 * Created by aziis98 on 03/03/2017.
 */

interface MelangContext {
    fun getBySymbol(name: String)
}

sealed class MelengExpression {

    class ExpressionCall(val expression: MelengExpression, val arguments: List<MelengExpression>) : MelengExpression() {
        override fun evaluate() = Litteral.Nil
    }

    class LambdaFunction()

    sealed class Litteral<out T>(val value: T) : MelengExpression() {
        class Number(value: Double) : Litteral<Double>(value)
        class Text(value: String) : Litteral<String>(value)

        class Symbol(value: String) : Litteral<String>(value)
        object Nil : Litteral<Unit>(Unit)

        override fun evaluate() = this
    }

    abstract fun evaluate(): MelengExpression

}


object Melang {



    private fun tokenize(source: String): List<String> {
        val result = mutableListOf(source[0].toString())

        val table = listOf(
                Pair(Char::isDigit, Char::isDigit),
                Pair(Char::isDigit, { it: Char -> it == '%' }),
                Pair(Char::isLetter, Char::isLetterOrDigit),
                Pair(Char::isWhitespace, Char::isWhitespace),
                Pair(Char::isLetter, { it: Char -> it == '-' }),
                Pair({ it: Char -> it == '-' }, Char::isLetter),
                Pair(Char::isDigit, { it: Char -> it == '.' }),
                Pair({ it: Char -> it == '.' }, Char::isDigit)
        )

        fun testFn(a: Char, b: Char): Boolean {
            return table.any { (lft, rgt) -> lft(a) && rgt(b) }
        }

        for (i in 1 .. source.lastIndex) {
            val lastStr = result.last()

            if (testFn(lastStr.last(), source[i])) {
                result[result.lastIndex] = result.last() + source[i]
            }
            else {
                result.add(source[i].toString())
            }
        }

        return result
    }

}