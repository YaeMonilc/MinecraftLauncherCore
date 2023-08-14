import java.util.Scanner

/**
 * Launch Minecraft
 * @author YaeMonilc
 */
class MinecraftLauncherCore(
    private val launchCommand: LaunchCommand
) {
    fun launch() {
        val processBuilder = ProcessBuilder("powershell.exe", launchCommand.command)
        val process = processBuilder.start()
        val scanner = Scanner(process.inputStream)

        while (scanner.hasNext()) {
            println(scanner.nextLine())
        }
    }
}