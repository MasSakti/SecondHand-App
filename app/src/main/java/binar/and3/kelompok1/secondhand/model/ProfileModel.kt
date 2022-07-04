package binar.and3.kelompok1.secondhand.model

data class ProfileModel(
    val id: Int,
    val full_name: String,
    val email: String,
    val password: String,
    val phoneNumber: Int,
    val city: String,
    val address: String,
    val imageUrl: String
)
