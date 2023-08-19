import entity.*
import exception.OsNotFound

object MinecraftLauncherCore {

    private fun launch(
        launchCommand: LaunchCommand
    ): Process {
        val process: Process = when(launchCommand.os) {
            OS.WNIDOWS -> {
                val processBuilder = ProcessBuilder("powershell.exe", launchCommand.command)
                processBuilder.start()
            }
            OS.LINUX -> {
                Runtime.getRuntime().exec(launchCommand.command)
            }

            else -> throw OsNotFound("${launchCommand.os} not found")
        }

        return process
    }

    fun launch(
        gameConfig: GameConfig,
        user: User,
        memorySetting: MemorySetting = MemorySetting(),
        launcherConfig: LauncherConfig = LauncherConfig()
    ): Game {

        val launchCommand = LaunchCommandBuilder(
            gameConfig = gameConfig,
            user = user,
            memorySetting = memorySetting,
            launcherConfig = launcherConfig
        ).build()

        return Game(launch(launchCommand), gameConfig)
    }
}