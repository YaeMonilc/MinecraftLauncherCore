import java.util.Scanner

class MinecraftLauncherCore(
    val command: LaunchCommand
) {
    fun launch() {
        val args = StringBuilder()
        command.args.values.forEach {
            args.append(" $it")
        }

        val commandString = "${ command.javaPath }$args"

        println(commandString)

        val processBuilder = ProcessBuilder("powershell.exe", commandString)
        val process = processBuilder.start()
        val scanner = Scanner(process.inputStream)

        while (scanner.hasNext()) {
            println(scanner.nextLine())
        }

    }
}