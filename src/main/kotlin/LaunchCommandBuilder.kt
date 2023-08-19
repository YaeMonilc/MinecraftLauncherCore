import entity.*
import java.io.File

class LaunchCommandBuilder(
    val gameConfig: GameConfig,
    val user: User,
    val memorySetting: MemorySetting = MemorySetting(),
    val launcherConfig: LauncherConfig = LauncherConfig()
) {
    private val launchCommand = LaunchCommand()
    private val jvmArgs: MutableList<String> = mutableListOf()
    private val minecraftArgs: LinkedHashMap<String, String> = LinkedHashMap()

    init {
        launchCommand.os = gameConfig.os

        jvmArgs.add("-Xmx${memorySetting.max}m")
        jvmArgs.add("-Xmn${memorySetting.min}m")
        jvmArgs.add("-Dfile.encoding=${gameConfig.charset}")
        jvmArgs.add("-Dsun.stdout.encoding=${gameConfig.charset}")
        jvmArgs.add("-Dsun.stderr.encoding=${gameConfig.charset}")
        jvmArgs.add("-Djava.rmi.server.useCodebaseOnly=true")
        jvmArgs.add("-Dcom.sun.jndi.rmi.object.trustURLCodebase=false")
        jvmArgs.add("-Dcom.sun.jndi.cosnaming.object.trustURLCodebase=false")
        jvmArgs.add("-XX:+UnlockExperimentalVMOptions")
        jvmArgs.add("-XX:+UseG1GC")
        jvmArgs.add("-XX:G1NewSizePercent=20")
        jvmArgs.add("-XX:G1ReservePercent=20")
        jvmArgs.add("-XX:MaxGCPauseMillis=50")
        jvmArgs.add("-XX:G1HeapRegionSize=32m")
        jvmArgs.add("-XX:-UseAdaptiveSizePolicy")
        jvmArgs.add("-XX:-OmitStackTraceInFastThrow")
        jvmArgs.add("-XX:-DontCompileHugeMethods")
        jvmArgs.add("-Dfml.ignoreInvalidMinecraftCertificates=true")
        jvmArgs.add("-Dfml.ignorePatchDiscrepancies=true")
        jvmArgs.add("-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump")
        jvmArgs.add("-Dminecraft.launcher.brand=${launcherConfig.name}")
        jvmArgs.add("-Dminecraft.launcher.version=${launcherConfig.version}")

        val versionJson = gameConfig.versionJson
        val versionPath = gameConfig.minecraftPath.getVersion(versionJson.id).absolutePath
        val gameJar = File(versionPath, "${versionJson.id}.jar").absolutePath
        val nativesPath = File(versionPath, "/natives").absolutePath

        jvmArgs.add("-Djava.library.path=$nativesPath")
        jvmArgs.add("-Djna.tmpdir=$nativesPath")
        jvmArgs.add("-Dorg.lwjgl.system.SharedLibraryExtractPath=$nativesPath")
        jvmArgs.add("-Dio.netty.native.workdir=$nativesPath")

        val loggingFile = File(versionPath, "log4j2.xml")
        if (loggingFile.exists()) {
            jvmArgs.add("-Dlog4j2.formatMsgNoLookups=true")
            jvmArgs.add("-Dlog4j.configurationFile=${loggingFile.absolutePath}")
        }

        jvmArgs.add("-Dminecraft.client.jar=$gameJar")

        val librariesString = StringBuilder()
        versionJson.libraries.filter { library ->
            library.rules?.any { (it.os?.name == gameConfig.os && it.action == "allow") ||
                    (it.action == "disallow" && it.os?.name != gameConfig.os) } ?: false || library.rules == null
        }.forEach { library ->
            library.downloads.artifact?.let {
                librariesString.append("${File(gameConfig.minecraftPath.getLibraries(), it.path).absolutePath};")
            }
            library.downloads.classifiers?.let { classifiers ->
                val artifact = when(gameConfig.os) {
                    OS.LINUX -> classifiers.nativesLinux
                    OS.OSX -> classifiers.nativesOSX
                    OS.WNIDOWS -> classifiers.nativesWindows
                    else -> null
                }
                artifact?.let {
                    librariesString.append("${File(gameConfig.minecraftPath.getLibraries(), it.path).absolutePath};")
                }
            }
        }
        jvmArgs.add("-cp")
        jvmArgs.add("$librariesString$gameJar")

        jvmArgs.add(versionJson.mainClass)

        minecraftArgs["--version"] = versionJson.id
        minecraftArgs["--gameDir"] = versionPath
        minecraftArgs["--assetsDir"] = gameConfig.minecraftPath.getAssets().absolutePath
        minecraftArgs["--assetIndex"] = versionJson.assetIndex.id

        minecraftArgs["--username"] = user.name
        minecraftArgs["--uuid"] = user.authUUID
        minecraftArgs["--accessToken"] = user.accessToken
        minecraftArgs["--userType"] = user.userType
        minecraftArgs["--versionType"] = "${launcherConfig.name} ${launcherConfig.version}"
    }

    fun build(): LaunchCommand {
        val command = StringBuilder()
        command.append(gameConfig.javaPath)
        jvmArgs.forEach {
            command.append(" '$it'")
        }
        minecraftArgs.forEach { (t, u) ->
            command.append("${if (t.isBlank()) "" else " '$t'"} '$u'")
        }
        launchCommand.command = command.toString()
        return launchCommand
    }
}