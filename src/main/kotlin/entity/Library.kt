package entity

import com.google.gson.annotations.SerializedName

/**
 * Game libraries
 * @author YaeMonilc
 */
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