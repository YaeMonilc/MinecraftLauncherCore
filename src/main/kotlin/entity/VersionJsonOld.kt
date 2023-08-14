package entity

/**
 * Old version.json format. Game version <= 1.13
 * @author YaeMonilc
 */
class VersionJsonOld(
    val id: String,
    val minecraftArguments: String,
    val mainClass: String,
    val jar: String,
    val assetIndex: AssetIndex,
    val assets: String,
    val complianceLevel: String,
    val javaVersion: JavaVersion,
    val libraries: Array<Library>,
    val downloads: Downloads,
    val type: String,
    val time: String,
    val releaseTime: String,
    val minimumLauncherVersion: Int,
    val root: Boolean
) {
    class Downloads(
        val client: Download,
        val server: Download,
    ) {
        class Download(
            val sha1: String,
            val size: Long,
            val url: String
        )
    }
}