package binar.and3.kelompok1.secondhand.model

data class ProductDetailModel(
    val id: Int,
    val name: String,
    val basePrice: Int,
    val imageUrl: String,
    val imageName: String,
    val location: String,
    val user: User,
    val categories: List<Categories>
) {
    data class Categories(
        val id: Int,
        val name: String
    )
    data class User(
        val id: Int,
        val fullName: String,
        val imageUrl: String,
        val city: String
    )
}