import java.io.File
import java.util.*

class LaunchCommandBuilder(
    javaPath: String,
    minecraftPath: String,
    version: String,
    charset: String
) {
    private val launchCommand = LaunchCommand()

    init {
        launchCommand.javaPath = javaPath
        launchCommand.minecraftPath = minecraftPath

        launchCommand.args["-Xmx"] = "'-Xmx2048m'"
        launchCommand.args["-Dsun.stdout.encoding"] = "'-Dsun.stdout.encoding=$charset'"
        launchCommand.args["-Dsun.stderr.encoding"] = "'-Dsun.stderr.encoding=$charset'"
        launchCommand.args["-Djava.rmi.server.useCodebaseOnly"] = "'-Djava.rmi.server.useCodebaseOnly=true'"
        launchCommand.args["-Dcom.sun.jndi.rmi.object.trustURLCodebase"] = "'-Dcom.sun.jndi.rmi.object.trustURLCodebase=false'"
        launchCommand.args["-Dcom.sun.jndi.cosnaming.object.trustURLCodebase"] = "'-Dcom.sun.jndi.cosnaming.object.trustURLCodebase=false'"
        launchCommand.args["-Dminecraft.client.jar"] = "'-Dminecraft.client.jar=.minecraft\\versions\\$version\\$version.jar'"
        launchCommand.args["-XX:+UnlockExperimentalVMOptions"] = "'-XX:+UnlockExperimentalVMOptions'"
        launchCommand.args["-XX:+UseG1GC"] = "'-XX:+UseG1GC'"
        launchCommand.args["-XX:G1NewSizePercent"] = "'-XX:G1NewSizePercent=20'"
        launchCommand.args["-XX:G1ReservePercent"] = "'-XX:G1ReservePercent=20'"
        launchCommand.args["-XX:MaxGCPauseMillis"] = "'-XX:MaxGCPauseMillis=50'"
        launchCommand.args["-XX:G1HeapRegionSize"] = "'-XX:G1HeapRegionSize=32m'"
        launchCommand.args["-XX:-UseAdaptiveSizePolicy"] = "'-XX:-UseAdaptiveSizePolicy'"
        launchCommand.args["-XX:-OmitStackTraceInFastThrow"] = "'-XX:-OmitStackTraceInFastThrow'"
        launchCommand.args["-XX:-DontCompileHugeMethods"] = "'-XX:-DontCompileHugeMethods'"
        launchCommand.args["-Dfml.ignoreInvalidMinecraftCertificates"] = "'-Dfml.ignoreInvalidMinecraftCertificates=true'"
        launchCommand.args["-Dfml.ignorePatchDiscrepancies"] = "'-Dfml.ignorePatchDiscrepancies=true'"
        launchCommand.args["-XX:HeapDumpPath"] = "'-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump'"
        launchCommand.args["-Djava.library.path"] = "'-Djava.library.path=$minecraftPath\\.minecraft\\versions\\$version\\natives-windows-x86_64'"
        launchCommand.args["-Djna.tmpdir"] = "'-Djna.tmpdir=$minecraftPath\\.minecraft\\versions\\$version\\natives-windows-x86_64'"
        launchCommand.args["-Dorg.lwjgl.system.SharedLibraryExtractPath"] = "'-Dorg.lwjgl.system.SharedLibraryExtractPath=$minecraftPath\\.minecraft\\versions\\\$version\\natives-windows-x86_64'"
        launchCommand.args["-Dio.netty.native.workdir"] = "'-Dio.netty.native.workdir=$minecraftPath\\.minecraft\\versions\\\$version\\natives-windows-x86_64'"
        launchCommand.args["-Dminecraft.launcher.brand"] = "'-Dminecraft.launcher.brand=MinecraftLaunchCore'"
        launchCommand.args["-Dminecraft.launcher.version"] = "'-Dminecraft.launcher.version=1.0.0'"

        val libraries = StringBuilder()
        val librariesDir = File("$minecraftPath\\.minecraft\\libraries\\")
        getAllFile(librariesDir).forEach {
            libraries.append("$it;")
        }
        launchCommand.args["-cp"] = "'-cp' '$libraries;$minecraftPath\\.minecraft\\versions\\$version\\$version.jar'"

        launchCommand.args["net.minecraft.client.main.Main"] = "'net.minecraft.client.main.Main'"
        launchCommand.args["--username"] = "'--username' 'USERNAME'"
        launchCommand.args["--version"] = "'--version' '$version'"
        launchCommand.args["'--gameDir"] = "'--gameDir' '$minecraftPath\\.minecraft'"
        launchCommand.args["--assetsDir"] = "'--assetsDir' '$minecraftPath\\.minecraft\\assets'"
        launchCommand.args["--assetIndex"] = "'--assetIndex' '5'"
        launchCommand.args["--uuid"] = "'--uuid' '${ UUID.randomUUID() }'"
        launchCommand.args["--accessToken"] = "'--accessToken' '${ UUID.randomUUID() }'"
        launchCommand.args["--clientId"] = "'--clientId' '\${clientId}'"
        launchCommand.args["--xuid"] = "--xuid '\${auth_xuid}'"
        launchCommand.args["--userType"] = "'--userType' 'msa'"
        launchCommand.args["--versionType"] = "'--versionType' 'MinecraftLaunchCore 1.0.0'"
        launchCommand.args["--width"] = "'--width' '854'"
        launchCommand.args["--height"] = "'--height' '480'"
    }

    fun build(): LaunchCommand {
        return launchCommand
    }

}