@file:Suppress("UNCHECKED_CAST", "unused")

package parser4k

import dev.forkhandles.tuples.Tuple2
import dev.forkhandles.tuples.Tuple3
import dev.forkhandles.tuples.Tuple4
import dev.forkhandles.tuples.Tuple5
import dev.forkhandles.tuples.Tuple6
import dev.forkhandles.tuples.Tuple7
import dev.forkhandles.tuples.Tuple8

/////////////////////////////////////////////////////
// This file was generated by generate-source-code.kt
/////////////////////////////////////////////////////

class InOrder2<T1, T2>(val parser1: Parser<T1>, val parser2: Parser<T2>) : Parser<Tuple2<T1, T2>> {
    private val parser = InOrder(listOf(parser1, parser2)).map { Tuple2(it[0] as T1, it[1] as T2) }
    override fun parse(input: Input): Output<Tuple2<T1, T2>>? = parser.parse(input)
}

class InOrder3<T1, T2, T3>(val parser1: Parser<T1>, val parser2: Parser<T2>, val parser3: Parser<T3>) : Parser<Tuple3<T1, T2, T3>> {
    private val parser = InOrder(listOf(parser1, parser2, parser3)).map { Tuple3(it[0] as T1, it[1] as T2, it[2] as T3) }
    override fun parse(input: Input): Output<Tuple3<T1, T2, T3>>? = parser.parse(input)
}

class InOrder4<T1, T2, T3, T4>(val parser1: Parser<T1>, val parser2: Parser<T2>, val parser3: Parser<T3>, val parser4: Parser<T4>) : Parser<Tuple4<T1, T2, T3, T4>> {
    private val parser = InOrder(listOf(parser1, parser2, parser3, parser4)).map { Tuple4(it[0] as T1, it[1] as T2, it[2] as T3, it[3] as T4) }
    override fun parse(input: Input): Output<Tuple4<T1, T2, T3, T4>>? = parser.parse(input)
}

class InOrder5<T1, T2, T3, T4, T5>(val parser1: Parser<T1>, val parser2: Parser<T2>, val parser3: Parser<T3>, val parser4: Parser<T4>, val parser5: Parser<T5>) : Parser<Tuple5<T1, T2, T3, T4, T5>> {
    private val parser = InOrder(listOf(parser1, parser2, parser3, parser4, parser5)).map { Tuple5(it[0] as T1, it[1] as T2, it[2] as T3, it[3] as T4, it[4] as T5) }
    override fun parse(input: Input): Output<Tuple5<T1, T2, T3, T4, T5>>? = parser.parse(input)
}

class InOrder6<T1, T2, T3, T4, T5, T6>(val parser1: Parser<T1>, val parser2: Parser<T2>, val parser3: Parser<T3>, val parser4: Parser<T4>, val parser5: Parser<T5>, val parser6: Parser<T6>) : Parser<Tuple6<T1, T2, T3, T4, T5, T6>> {
    private val parser = InOrder(listOf(parser1, parser2, parser3, parser4, parser5, parser6)).map { Tuple6(it[0] as T1, it[1] as T2, it[2] as T3, it[3] as T4, it[4] as T5, it[5] as T6) }
    override fun parse(input: Input): Output<Tuple6<T1, T2, T3, T4, T5, T6>>? = parser.parse(input)
}

class InOrder7<T1, T2, T3, T4, T5, T6, T7>(val parser1: Parser<T1>, val parser2: Parser<T2>, val parser3: Parser<T3>, val parser4: Parser<T4>, val parser5: Parser<T5>, val parser6: Parser<T6>, val parser7: Parser<T7>) : Parser<Tuple7<T1, T2, T3, T4, T5, T6, T7>> {
    private val parser = InOrder(listOf(parser1, parser2, parser3, parser4, parser5, parser6, parser7)).map { Tuple7(it[0] as T1, it[1] as T2, it[2] as T3, it[3] as T4, it[4] as T5, it[5] as T6, it[6] as T7) }
    override fun parse(input: Input): Output<Tuple7<T1, T2, T3, T4, T5, T6, T7>>? = parser.parse(input)
}

class InOrder8<T1, T2, T3, T4, T5, T6, T7, T8>(val parser1: Parser<T1>, val parser2: Parser<T2>, val parser3: Parser<T3>, val parser4: Parser<T4>, val parser5: Parser<T5>, val parser6: Parser<T6>, val parser7: Parser<T7>, val parser8: Parser<T8>) : Parser<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> {
    private val parser = InOrder(listOf(parser1, parser2, parser3, parser4, parser5, parser6, parser7, parser8)).map { Tuple8(it[0] as T1, it[1] as T2, it[2] as T3, it[3] as T4, it[4] as T5, it[5] as T6, it[6] as T7, it[7] as T8) }
    override fun parse(input: Input): Output<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>>? = parser.parse(input)
}

fun <T1, T2> inOrder(parser1: Parser<T1>, parser2: Parser<T2>): InOrder2<T1, T2> =
    InOrder2(parser1, parser2)

fun <T1, T2, T3> inOrder(parser1: Parser<T1>, parser2: Parser<T2>, parser3: Parser<T3>): InOrder3<T1, T2, T3> =
    InOrder3(parser1, parser2, parser3)

fun <T1, T2, T3, T4> inOrder(parser1: Parser<T1>, parser2: Parser<T2>, parser3: Parser<T3>, parser4: Parser<T4>): InOrder4<T1, T2, T3, T4> =
    InOrder4(parser1, parser2, parser3, parser4)

fun <T1, T2, T3, T4, T5> inOrder(parser1: Parser<T1>, parser2: Parser<T2>, parser3: Parser<T3>, parser4: Parser<T4>, parser5: Parser<T5>): InOrder5<T1, T2, T3, T4, T5> =
    InOrder5(parser1, parser2, parser3, parser4, parser5)

fun <T1, T2, T3, T4, T5, T6> inOrder(parser1: Parser<T1>, parser2: Parser<T2>, parser3: Parser<T3>, parser4: Parser<T4>, parser5: Parser<T5>, parser6: Parser<T6>): InOrder6<T1, T2, T3, T4, T5, T6> =
    InOrder6(parser1, parser2, parser3, parser4, parser5, parser6)

fun <T1, T2, T3, T4, T5, T6, T7> inOrder(parser1: Parser<T1>, parser2: Parser<T2>, parser3: Parser<T3>, parser4: Parser<T4>, parser5: Parser<T5>, parser6: Parser<T6>, parser7: Parser<T7>): InOrder7<T1, T2, T3, T4, T5, T6, T7> =
    InOrder7(parser1, parser2, parser3, parser4, parser5, parser6, parser7)

fun <T1, T2, T3, T4, T5, T6, T7, T8> inOrder(parser1: Parser<T1>, parser2: Parser<T2>, parser3: Parser<T3>, parser4: Parser<T4>, parser5: Parser<T5>, parser6: Parser<T6>, parser7: Parser<T7>, parser8: Parser<T8>): InOrder8<T1, T2, T3, T4, T5, T6, T7, T8> =
    InOrder8(parser1, parser2, parser3, parser4, parser5, parser6, parser7, parser8)

