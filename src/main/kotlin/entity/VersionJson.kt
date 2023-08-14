package entity

import com.google.gson.annotations.SerializedName

/**
 * version.json format. Game version > 1.13
 * @author YaeMonilc
 */
class VersionJson(
    val arguments: Arguments,
    val assetIndex: AssetIndex,
    val assets: String,
    val complianceLevel: Int,
    val downloads: Downloads,
    val id: String,
    val javaVersion: JavaVersion,
    val libraries: List<Library>,
    val logging: Logging,
    val mainClass: String,
    val minimumLauncherVersion: String,
    val releaseTime: String,
    val time: String,
    val type: String
) {
    class Arguments(
        val game: List<Any>,
        val jvm: List<Any>
    )
    class Downloads(
        val client: Download,
        @SerializedName("client_mappings")
        val clientMappings: Download,
        val server: Download,
        @SerializedName("server_mappings")
        val serverMappings: Download
    ) {
        class Download(
            val sha1: String,
            val size: Long,
            val url: String
        )
    }
}