import java.io.File

fun readFileString(file: File): String {
    return file.readText()
}

fun readFileByteArray(file: File): ByteArray {
    return file.readBytes()
}

fun writeFileString(file: File, content: String) {
    return file.writeText(content)
}

fun writeFileByteArray(file: File, content: ByteArray) {
    return file.writeBytes(content)
}