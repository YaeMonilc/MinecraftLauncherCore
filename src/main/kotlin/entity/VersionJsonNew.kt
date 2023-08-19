package entity

class VersionJsonNew: VersionJson() {
    lateinit var arguments: Arguments
    class Arguments(
        val game: List<Any>,
        val jvm: List<Any>
    )
}