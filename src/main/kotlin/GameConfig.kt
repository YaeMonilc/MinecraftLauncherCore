import java.io.File

/**
 * @author YaeMonilc
 * @see GameConfigBuilder
 */
class GameConfig internal constructor(){
    internal lateinit var javaPath: File
    internal lateinit var minecraftPath: File
    internal lateinit var versionJsonFile: File
    internal lateinit var versionJson: Any
    internal lateinit var charset: String
    internal lateinit var os: String
}