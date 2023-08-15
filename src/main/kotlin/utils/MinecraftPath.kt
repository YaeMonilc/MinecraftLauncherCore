package utils

import java.io.File

class MinecraftPath(
    val path: File
) {
    fun getRoot(): File {
        return path
    }

    fun getVersions(): File {
        return File(path, "/versions")
    }

    fun getVersion(version: String): File {
        return File(path, "/versions/$version/")
    }

    fun getLibraries(): File {
        return File(path, "/libraries")
    }

    fun getAssets(): File {
        return File(path, "/assets")
    }
}