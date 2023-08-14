package entity

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