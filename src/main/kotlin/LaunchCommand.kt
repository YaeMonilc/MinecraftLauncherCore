
class LaunchCommand internal constructor(){
    internal lateinit var javaPath: String
    internal lateinit var minecraftPath: String
    internal var args: LinkedHashMap<String, String> = LinkedHashMap()
}