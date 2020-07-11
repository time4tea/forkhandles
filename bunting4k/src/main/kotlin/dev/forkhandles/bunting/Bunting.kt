package dev.forkhandles.bunting

import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

open class Bunting(internal val args: Array<String>, private val description: String? = null, internal val baseCommand: String = System.getProperty("sun.java.command")) {
    fun switch(description: String = "") = Switch(description)
    fun option(description: String = "") = Option({ it }, description, null)
    fun <T : Bunting> command(fn: BuntingConstructor<T>, description: String = "") = Command(args.drop(1), description, fn)

    fun usage(): String = "$baseCommand [flags] [options]"

    internal fun description(indent: Int = 0): String =
        listOfNotNull(description, commandDescriptions(indent), optionDescriptions(indent)).joinToString("\n")

    private fun commandDescriptions(indent: Int): String? {
        val commandDescriptions = members { p, c: Command<*> ->
            p.name to c.description + "\n" +
                c.getValue(
                    Bunting(arrayOf(p.name), description, "$baseCommand ${p.name}"), p
                ).description(indent + 2)
        }

        return commandDescriptions
            .takeIf { it.isNotEmpty() }
            ?.let {
                indent(indent) + (if (indent == 0) "[flags]" else "[sub-flags]") + ":\n" +
                    it.joinToString("\n") {
                        val base = "${indent(indent)}  ${it.first}"
                        base.indented(it.second)
                    }
            }
    }

    private fun optionDescriptions(indent: Int): String? {
        val switches = members { p, s: Switch -> p.name to s.description }
        val options = members { p, o: Option<*> -> p.name to "${o.description} (${p.typeDescription()})" }

        val sortedOptions = (switches + options).sortedBy { it.first }
        val allOptions = if (indent > 0) sortedOptions else sortedOptions + listOf("help" to "Show this message and exit")

        return allOptions.takeIf { it.isNotEmpty() }?.describeOptions(indent)
    }
}

typealias BuntingConstructor<T> = (Array<String>) -> T

fun <T : Bunting> T?.use(out: (String) -> Unit = ::println, fn: T.() -> Unit) {
    this?.apply {
        try {
            if (args.contains("--help") || args.contains("-h")) throw Help(description())
            fn(this)
        } catch (e: BuntingException) {
            out("Usage: ${usage()}\n" + e.localizedMessage)
        }
    }
}

private inline fun <reified F : BuntingFlag<*>> Bunting.members(fn: (KProperty<*>, F) -> Pair<String, String>): List<Pair<String, String>> =
    this::class.members.filterIsInstance<KProperty<F>>().mapNotNull { p ->
        (p.javaField!!.apply { trySetAccessible() }[this@members] as? F)
            ?.let {
                fn(p, it)
            }
    }

private fun indent(indent: Int) = "  ".repeat(indent)

private fun List<Pair<String, String>>.describeOptions(indent: Int) = indent(indent) + "[options]:" + "\n" +
    joinToString("\n") {
        (indent(indent + 1) + "-${it.first.take(1)}, --${it.first}").indented(it.second)
    }

private fun String.indented(second: String) = this + " ".repeat(maxOf(40 - length, 4)) + second
