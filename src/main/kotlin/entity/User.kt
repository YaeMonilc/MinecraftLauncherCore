package entity

class User(
    val name: String,
    val authUUID: String,
    val accessToken: String,
    val userType: String
) {
    object UserType {
        val msa: String = "msa"
    }
}