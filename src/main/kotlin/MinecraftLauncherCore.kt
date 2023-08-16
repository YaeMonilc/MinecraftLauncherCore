import entity.LaunchCommand
import entity.OS
import java.util.Scanner

/**
 * Launch Minecraft
 * @author YaeMonilc
 */
class MinecraftLauncherCore(
    private val launchCommand: LaunchCommand
) {
    fun launch() {

        val process: Process = when(launchCommand.os) {
            OS.WNIDOWS -> {
                val processBuilder = ProcessBuilder("powershell.exe", launchCommand.command)
                processBuilder.start()
            }

            OS.LINUX -> {
                Runtime.getRuntime().exec(launchCommand.command)
            }

            else -> return
        }


        val scanner = Scanner(process.inputStream)

        while (scanner.hasNext()) {
            println(scanner.nextLine())
        }
    }
}