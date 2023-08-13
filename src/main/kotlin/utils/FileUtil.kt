import java.io.File



internal fun getAllFile(dir: File): List<File> {
    val list = mutableListOf<File>()
    return getDirAllFile(dir, list)
}

private fun getDirAllFile(dir: File, list: MutableList<File>): MutableList<File> {
    if (dir.exists() && dir.isDirectory) {
        dir.listFiles()?.forEach {
            if (it.isDirectory) {
                getDirAllFile(it, list)
            } else {
                list.add(it)
            }
        }
    }
    return list
}