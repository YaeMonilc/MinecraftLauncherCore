package utils

import entity.GameConfig
import entity.OS
import java.io.File

object LibraryUtil {

    fun check(gameConfig: GameConfig): Boolean {
        gameConfig.versionJson.libraries.filter { library ->
            library.rules?.any { (it.os?.name == gameConfig.os && it.action == "allow") ||
                    (it.action == "disallow" && it.os?.name != gameConfig.os) } ?: false || library.rules == null
        }.forEach { library ->
            library.downloads.artifact?.let {
                if(!File(gameConfig.minecraftPath.getLibraries(), it.path).exists())
                    return false
            }
            library.downloads.classifiers?.let { classifiers ->
                val artifact = when(gameConfig.os) {
                    OS.LINUX -> classifiers.nativesLinux
                    OS.OSX -> classifiers.nativesOSX
                    OS.WNIDOWS -> classifiers.nativesWindows
                    else -> null
                }
                artifact?.let {
                    if(!File(gameConfig.minecraftPath.getLibraries(), it.path).exists())
                        return false
                }
            }
        }
        return true
    }
}
