import com.google.gson.GsonBuilder
import entity.VersionJson
import entity.VersionJsonOld
import exception.VersionJsonError
import exception.VersionJsonNotFound
import java.io.File

/**
 * The GameConfig Object Builder
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
        gameConfig.minecraftPath = minecraftPath //C:\Users\Administrator\Desktop\MC\
        gameConfig.versionJsonFile = File(gameConfig.minecraftPath, ".minecraft\\versions\\$version\\$version.json")
        gameConfig.charset = charset
        gameConfig.os = os
    }

    /**
     * @author YaeMonilc
     * @exception VersionJsonNotFound
     * @exception VersionJsonError
     * @return GameConfig
     */
    @Throws(VersionJsonNotFound::class, VersionJsonError::class)
    fun build(): GameConfig {
        if (!gameConfig.versionJsonFile.exists())
            throw VersionJsonNotFound("$version.json not found")

        val versionJsonString = readFileString(gameConfig.versionJsonFile)
        if (versionJsonString.isBlank())
            throw VersionJsonError("read $version.json error")

        val gson = GsonBuilder().create()
        gameConfig.versionJson =
        if (versionJsonString.contains("arguments"))
            gson.fromJson(versionJsonString, VersionJson::class.java)
        else
            gson.fromJson(versionJsonString, VersionJsonOld::class.java)

        return gameConfig
    }

}