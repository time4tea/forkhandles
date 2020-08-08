package parser4k

import java.io.File

fun main() {
    val basePath = "parser4k/src/main/kotlin/parser4k"

    File("$basePath/in-order-generated.kt").printWriter().use { writer ->
        Generator(println = { writer.println(it) }, maxN = 8).apply {
            generateHeader()
            generateInOrderParsers()
            generateInOrderFunctions()
        }
    }
    File("$basePath/associativity-generated.kt").printWriter().use { writer ->
        Generator(println = { writer.println(it) }, maxN = 8).apply {
            generateHeader()
            generateMapLeftAssoc()
        }
    }
    File("$basePath/util-generated.kt").printWriter().use { writer ->
        Generator(println = { writer.println(it) }, maxN = 8).apply {
            generateHeader()
            generateSkipFirst()
            generateSkipLast()
            generateSkipWrapper()
        }
    }
}

private data class Generator(
    val println: (String) -> Unit,
    val maxN: Int = 8
)

private fun Generator.generateHeader() {
    println("""@file:Suppress("UNCHECKED_CAST", "unused")""")
    println("")
    println("package parser4k")
    println("")
    println("import dev.forkhandles.tuples.*")
    println("")
    println("/////////////////////////////////////////////////////")
    println("// This file was generated by generate-source-code.kt")
    println("/////////////////////////////////////////////////////")
    println("")
}

private fun Generator.generateInOrderParsers() {
    // For example:
    // class InOrder2<T1, T2>(val parser1: Parser<T1>, val parser2: Parser<T2>) : Parser<Tuple2<T1, T2>> {
    //     override fun parse(input: Input): Output<Tuple2<T1, T2>>? =
    //         InOrder(listOf(parser1, parser2)).map { Tuple2(it[0] as T1, it[1] as T2) }.parse(input)
    // }

    (2..maxN).forEach { n ->
        val ts = (1..n).joinToString { "T$it" }
        val parserVals = (1..n).joinToString { "val parser$it: Parser<T$it>" }
        val parsers = (1..n).joinToString { "parser$it" }
        val itAsTs = (1..n).joinToString { "it[${it - 1}] as T$it" }
        println("""
                class InOrder$n<$ts>($parserVals) : Parser<Tuple$n<$ts>> {
                    private val parser = InOrder(listOf($parsers)).map { Tuple$n($itAsTs) }
                    override fun parse(input: Input): Output<Tuple$n<$ts>>? = parser.parse(input)
                }
            """.trimIndent())
    }
    println("")
}

private fun Generator.generateInOrderFunctions() {
    // For example:
    // fun <T1, T2> inOrder(parser1: Parser<T1>, parser2: Parser<T2>): InOrder2<T1, T2> =
    //     InOrder2(parser1, parser2)

    (2..maxN).forEach { n ->
        val ts = (1..n).joinToString { "T$it" }
        val parserVals = (1..n).joinToString { "parser$it: Parser<T$it>" }
        val parsers = (1..n).joinToString { "parser$it" }
        println("""
                fun <$ts> inOrder($parserVals): InOrder$n<$ts> =
                    InOrder$n($parsers)
            """.trimIndent())
    }
    println("")
}

private fun Generator.generateMapLeftAssoc() {
    // For example:
    // fun <T1, T2, T3> InOrder3<T1, T2, T3>.leftAssoc(transform: (Tuple3<T1, T2, T3>) -> T1): Parser<T1> =
    //    InOrder(listOf(parser1, parser2, parser3))
    //        .leftAssoc { (it1, it2, it3) -> transform(Tuple3(it1 as T1, it2 as T2, it3 as T3)) } as Parser<T1>

    (2..maxN).forEach { n ->
        val ts = (1..n).joinToString { "T$it" }
        val its = (1..n).joinToString { "it$it" }
        val itsAsTs = (1..n).joinToString { "it$it as T$it" }
        val parsers = (1..n).joinToString { "parser$it" }
        println("""
                fun <$ts> InOrder$n<$ts>.mapLeftAssoc(transform: (Tuple$n<$ts>) -> T1): Parser<T1> =
                    InOrder(listOf($parsers))
                        .mapLeftAssoc { ($its) -> transform(Tuple$n($itsAsTs)) } as Parser<T1>
            """.trimIndent())
    }
    println("")
}

private fun Generator.generateSkipFirst() {
    // For example:
    // fun <T2, T3> InOrder3<*, T2, T3>.skipFirst(): Parser<Tuple2<T2, T3>> = map { (_, it2, it3) -> Tuple2(it2, it3) }

    println("fun <T2> InOrder2<*, T2>.skipFirst(): Parser<T2> = map { (_, it2) -> it2 }") // Special case because returning List1 is pointless
    (3..maxN).forEach { n ->
        val ts = (2..n).joinToString { "T$it" }
        val its = (2..n).joinToString { "it$it" }
        println("fun <$ts> InOrder$n<*, $ts>.skipFirst(): Parser<Tuple${n - 1}<$ts>> = map { (_, $its) -> Tuple${n - 1}($its) }")
    }
    println("")
}

private fun Generator.generateSkipLast() {
    // For example:
    // fun <T1, T2> InOrder3<T1, T2, *>.skipLast(): Parser<Tuple2<T1, T2>> = map { (it1, it2, _) -> Tuple2(it1, it2) }

    println("fun <T1> InOrder2<T1, *>.skipLast(): Parser<T1> = map { (it1, _) -> it1 }") // Special case because returning Tuple1 is pointless
    (2 until maxN).forEach { n ->
        val ts = (1..n).joinToString { "T$it" }
        val its = (1..n).joinToString { "it$it" }
        println("fun <$ts> InOrder${n + 1}<$ts, *>.skipLast(): Parser<Tuple$n<$ts>> = map { ($its, _) -> Tuple$n($its) }")
    }
    println("")
}

private fun Generator.generateSkipWrapper() {
    // For example:
    // fun <T2, T3> InOrder4<*, T2, T3, *>.skipWrapper(): Parser<Tuple2<T2, T3>> = map { (_, it2, it3, _) -> Tuple2(it2, it3) }

    println("fun <T2> InOrder3<*, T2, *>.skipWrapper(): Parser<T2> = map { (_, it2, _) -> it2 }") // Special case because returning Tuple1 is pointless
    (3 until maxN).forEach { n ->
        val ts = (2..n).joinToString { "T$it" }
        val its = (2..n).joinToString { "it$it" }
        println("fun <$ts> InOrder${n + 1}<*, $ts, *>.skipWrapper(): Parser<Tuple${n - 1}<$ts>> = map { (_, $its, _) -> Tuple${n - 1}($its) }")
    }
    println("")
}

