import com.google.gson.GsonBuilder
import entity.GameConfig
import entity.VersionJson
import exception.VersionJsonError
import exception.VersionJsonNotFound
import utils.MinecraftPath
import java.io.File

/**
 * The entity.GameConfig Object Builder
 * @author YaeMonilc
 */
class GameConfigBuilder(
    val javaPath: File,
    val minecraftPath: File,
    val version: String,
    val charset: String,
    val os: String
) {
    private val gameConfig = GameConfig()

    init {
        gameConfig.javaPath = javaPath
        gameConfig.minecraftPath = MinecraftPath(minecraftPath)
        gameConfig.charset = charset
        gameConfig.os = os
    }

    /**
     * @author YaeMonilc
     * @exception VersionJsonNotFound
     * @exception VersionJsonError
     * @return entity.GameConfig
     */
    @Throws(VersionJsonNotFound::class, VersionJsonError::class)
    fun build(): GameConfig {
        gameConfig.versionJson = VersionJson.fromFile(gameConfig.minecraftPath, version)
        return gameConfig
    }

}