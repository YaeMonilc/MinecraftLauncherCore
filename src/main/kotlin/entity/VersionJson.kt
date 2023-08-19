package entity

import com.google.gson.annotations.SerializedName
import exception.VersionJsonError
import exception.VersionJsonNotFound
import readFileString
import utils.JsonUtil
import utils.MinecraftPath
import java.io.File

open class VersionJson {
    lateinit var assetIndex: AssetIndex
    lateinit var assets: String
    var complianceLevel: Int = 0
    lateinit var downloads: Downloads
    lateinit var id: String
    lateinit var javaVersion: JavaVersion
    lateinit var libraries: List<Library>
    lateinit var logging: Logging
    lateinit var mainClass: String
    lateinit var minimumLauncherVersion: String
    lateinit var releaseTime: String
    lateinit var time: String
    lateinit var type: String

    companion object {
        fun fromFile(minecraftPath: MinecraftPath, version: String): VersionJson {
            val versionJsonFile = File(minecraftPath.getVersion(version), "$version.json")

            if (!versionJsonFile.exists())
                throw VersionJsonNotFound("$version.json not found")

            val versionJsonString = readFileString(versionJsonFile)
            if (versionJsonString.isBlank())
                throw VersionJsonError("read $version.json error")

            return JsonUtil.gson.fromJson(versionJsonString, VersionJson::class.java)
        }
    }

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

    class Logging(
        val client: Client
    ) {
        class Client(
            val argument: String,
            val file: File,
            val type: String
        ) {
            class File(
                val id: String,
                val sha1: String,
                val size: Long,
                val url: String
            )
        }
    }

    class Library(
        val downloads: Download,
        val name: String,
        val rules: List<Rule>?
    ) {
        class Download(
            val artifact: Artifact?,
            val classifiers: Classifiers?
        ) {
            class Artifact(
                val path: String,
                val sha1: String,
                val size: Long,
                val url: String,
            )
            class Classifiers(
                @SerializedName("natives-linux")
                val nativesLinux: Artifact,
                @SerializedName("natives-osx")
                val nativesOSX: Artifact,
                @SerializedName("natives-windows")
                val nativesWindows: Artifact
            )
        }
        class Rule(
            val action: String,
            val os: Os?
        ) {
            class Os(
                val name: String
            )
        }
    }

    class AssetIndex(
        val id: String,
        val sha1: String,
        val size: Long,
        val totalSize: Long,
        val url: String
    )

    class JavaVersion(
        val component: String,
        val majorVersion: Int
    )
}