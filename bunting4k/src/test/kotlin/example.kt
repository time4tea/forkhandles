import dev.forkhandles.bunting.Bunting
import dev.forkhandles.bunting.enum
import dev.forkhandles.bunting.int
import dev.forkhandles.bunting.use

class ListFlags(args: Array<String>) : Bunting(args) {
    val includeDates by switch("Switch relevant to this mode")
}

class DeleteFlags(args: Array<String>) : Bunting(args)

class MyGreatFlags(args: Array<String>) : Bunting(args) {
    val list by command(::ListFlags, "list things")
    val delete by command(::DeleteFlags, "delete things")

    enum class LogLevel {
        debug, warn
    }

    val insecure by switch("This is a switch")
    val user by option("This is a required option")
    val password by option("This is another required option")
    val version by option().int().defaultsTo("0")
    val level by option().enum<LogLevel>().defaultsTo("warn")
}

object SingleOption {
    @JvmStatic
    // run the main with: java (...) SingleOption --user foo --password bar
    fun main(ignored: Array<String>) = MyGreatFlags(arrayOf("--user", "foo", "-p", "bar")).use {
        println(insecure)   // false    <-- because not set
        println(user)       // foo      <-- passed value (full name)
        println(password)   // bar      <-- passed value (short name)
        println(version)    // 0        <-- defaulted value
        println(level)      // warn        <-- defaulted value
    }
}

object SubCommands {
    @JvmStatic
    // run the main with: java (...) MultiOptionKt --command list --user foo --password bar
    fun main(ignored: Array<String>) = MyGreatFlags(arrayOf("delete", "--user", "foo", "-p", "bar")).use {
        list.use {
            println(insecure)       // false    <-- because not set
            println(user)           // foo      <-- passed value (full name)
            println(includeDates)   // false    <-- local switch
        }

        delete.use {
            println(password)           // bar      <-- passed value (short name)
            println(version)            // 0        <-- defaulted value
        }
    }
}

object AskForHelp {
    @JvmStatic
    // run the main with: java (...) AskForHelp --help
    fun main(ignored: Array<String>) = MyGreatFlags(arrayOf("--help")).use {
        // doesn't matter
    }
}
