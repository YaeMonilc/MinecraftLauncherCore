package entity

/**
 * version.json format. entity.Game version > 1.13
 * @author YaeMonilc
 */
class VersionJsonNew: VersionJson() {
    lateinit var arguments: Arguments
    class Arguments(
        val game: List<Any>,
        val jvm: List<Any>
    )
}