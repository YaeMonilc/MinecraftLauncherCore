import entity.*
import java.io.File

/**
 * The Launch Command Object Builder
 * @author YaeMonilc
 */
class LaunchCommandBuilder(
    val config: GameConfig,
    val user: User,
    val memorySetting: MemorySetting = MemorySetting(),
    val launcherConfig: LauncherConfig = LauncherConfig()
) {
    private val launchCommand = LaunchCommand()
    private val jvmArgs: MutableList<String> = mutableListOf()
    private val minecraftArgs: LinkedHashMap<String, String> = LinkedHashMap()

    init {
        launchCommand.os = config.os

        val minecraftPath = "${config.minecraftPath}\\.minecraft"

        jvmArgs.add("-Xmx${memorySetting.max}m")
        jvmArgs.add("-Xmn${memorySetting.min}m")
        jvmArgs.add("-Dfile.encoding=${config.charset}")
        jvmArgs.add("-Dsun.stdout.encoding=${config.charset}")
        jvmArgs.add("-Dsun.stderr.encoding=${config.charset}")
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

        if (config.versionJson is VersionJson) {
            val versionJson = config.versionJson as VersionJson
            val versionPath = "$minecraftPath\\versions\\${versionJson.id}"
            val gameJar = "$versionPath\\${versionJson.id}.jar"

            jvmArgs.add("-Djava.library.path=$versionPath\\natives")
            jvmArgs.add("-Djna.tmpdir=$versionPath\\natives")
            jvmArgs.add("-Dorg.lwjgl.system.SharedLibraryExtractPath=$versionPath\\natives")
            jvmArgs.add("-Dio.netty.native.workdir=$versionPath\\natives")

            val loggingFile = File("$versionPath\\log4j2.xml")
            if (loggingFile.exists()) {
                jvmArgs.add("-Dlog4j2.formatMsgNoLookups=true")
                jvmArgs.add("-Dlog4j.configurationFile=${loggingFile.absolutePath}")
            }

            jvmArgs.add("-Dminecraft.client.jar=$gameJar")

            val librariesString = StringBuilder()
            versionJson.libraries.filter { library ->
                library.rules?.any { (it.os?.name == config.os && it.action == "allow") ||
                        (it.action == "disallow" && it.os?.name != config.os) } ?: false || library.rules == null
            }.forEach { library ->
                println(library.name)
                library.downloads.artifact?.let {
                    librariesString.append("$minecraftPath\\libraries\\${it.path};")
                }
                library.downloads.classifiers?.let { classifiers ->
                    val artifact = when(config.os) {
                        OS.LINUX -> classifiers.nativesLinux
                        OS.OSX -> classifiers.nativesOSX
                        OS.WNIDOWS -> classifiers.nativesWindows
                        else -> null
                    }
                    artifact?.let {
                        librariesString.append("$minecraftPath\\libraries\\${it.path};")
                    }
                }
            }
            jvmArgs.add("-cp")
            jvmArgs.add("$librariesString$gameJar")

            jvmArgs.add(versionJson.mainClass)

            minecraftArgs["--version"] = versionJson.id
            minecraftArgs["--gameDir"] = versionPath
            minecraftArgs["--assetsDir"] = "$minecraftPath\\assets"
            minecraftArgs["--assetIndex"] = versionJson.assetIndex.id
        }
        else if (config.versionJson is VersionJsonOld) {
            val versionJson = config.versionJson as VersionJsonOld
            val versionPath = "$minecraftPath\\versions\\${versionJson.id}"
            val gameJar = "$versionPath\\${versionJson.id}.jar"

            jvmArgs.add("-Djava.library.path=$versionPath\\natives")
            jvmArgs.add("-Djna.tmpdir=$versionPath\\natives")
            jvmArgs.add("-Dorg.lwjgl.system.SharedLibraryExtractPath=$versionPath\\natives")
            jvmArgs.add("-Dio.netty.native.workdir=$versionPath\\natives")

            val loggingFile = File("$versionPath\\log4j2.xml")
            if (loggingFile.exists()) {
                jvmArgs.add("-Dlog4j2.formatMsgNoLookups=true")
                jvmArgs.add("-Dlog4j.configurationFile=${loggingFile.absolutePath}")
            }

            jvmArgs.add("-Dminecraft.client.jar=$gameJar")

            val librariesString = StringBuilder()
            versionJson.libraries.filter { library ->
                library.rules?.any { (it.os?.name == config.os && it.action == "allow") ||
                        (it.action == "disallow" && it.os?.name != config.os) } ?: false || library.rules == null
            }.forEach { library ->
                println(library.name)
                library.downloads.artifact?.let {
                    librariesString.append("$minecraftPath\\libraries\\${it.path};")
                }
                library.downloads.classifiers?.let { classifiers ->
                    val artifact = when(config.os) {
                        OS.LINUX -> classifiers.nativesLinux
                        OS.OSX -> classifiers.nativesOSX
                        OS.WNIDOWS -> classifiers.nativesWindows
                        else -> null
                    }
                    artifact?.let {
                        librariesString.append("$minecraftPath\\libraries\\${it.path};")
                    }
                }
            }
            jvmArgs.add("-cp")
            jvmArgs.add("$librariesString$gameJar")

            jvmArgs.add(versionJson.mainClass)

            minecraftArgs["--version"] = versionJson.id
            minecraftArgs["--gameDir"] = versionPath
            minecraftArgs["--assetsDir"] = "$minecraftPath\\assets"
            minecraftArgs["--assetIndex"] = versionJson.assetIndex.id
        }

        minecraftArgs["--username"] = user.name
        minecraftArgs["--uuid"] = user.authUUID
        minecraftArgs["--accessToken"] = user.accessToken
        minecraftArgs["--userType"] = user.userType
        minecraftArgs["--versionType"] = "${launcherConfig.name} ${launcherConfig.version}"
    }

    /**
     * @author YaeMonilc
     * @return LaunchCommand
     */
    fun build(): LaunchCommand {
        val command = StringBuilder()
        command.append(config.javaPath)
        jvmArgs.forEach {
            command.append(" '$it'")
        }
        minecraftArgs.forEach { t, u ->
            command.append("${if (t.isBlank()) "" else " '$t'"} '$u'")
        }
        launchCommand.command = command.toString()
        return launchCommand
    }
}