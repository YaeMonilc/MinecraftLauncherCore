package entity

import utils.MinecraftPath
import java.io.File

class GameConfig {
    internal lateinit var javaPath: File
    internal lateinit var minecraftPath: MinecraftPath
    internal lateinit var versionJson: VersionJson
    internal lateinit var charset: String
    internal lateinit var os: String
}