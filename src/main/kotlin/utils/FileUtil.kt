import java.io.File

/**
 * Read a file return the string
 * @author YaeMonilc
 * @param file File
 * @return String
 */
fun readFileString(file: File): String {
    return file.readText()
}

/**
 * Read a file return the byte array
 * @author YaeMonilc
 * @param file File
 * @return ByteArray
 */
fun readFileByteArray(file: File): ByteArray {
    return file.readBytes()
}

/**
 * Write a file by string
 * @author YaeMonilc
 * @param file File
 * @param content String
 */
fun writeFileString(file: File, content: String) {
    return file.writeText(content)
}

/**
 * Write a file by byte array
 * @author YaeMonilc
 * @param file File
 * @param content ByteArray
 */
fun writeFileByteArray(file: File, content: ByteArray) {
    return file.writeBytes(content)
}