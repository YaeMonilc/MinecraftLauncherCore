package entity

import entity.VersionJson
import utils.MinecraftPath
import java.io.File

/**
 * @author YaeMonilc
 * @see GameConfigBuilder
 */
class GameConfig internal constructor(){
    internal lateinit var javaPath: File
    internal lateinit var minecraftPath: MinecraftPath
    internal lateinit var versionJson: VersionJson
    internal lateinit var charset: String
    internal lateinit var os: String
}