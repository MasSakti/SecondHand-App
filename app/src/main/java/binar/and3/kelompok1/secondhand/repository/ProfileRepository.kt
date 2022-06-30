package binar.and3.kelompok1.secondhand.repository

import binar.and3.kelompok1.secondhand.common.Resource
import binar.and3.kelompok1.secondhand.common.Status
import binar.and3.kelompok1.secondhand.data.api.auth.AuthAPI
import binar.and3.kelompok1.secondhand.data.api.auth.UpdateUserDataResponse
import binar.and3.kelompok1.secondhand.data.local.auth.UserDAO
import binar.and3.kelompok1.secondhand.data.local.auth.UserEntity
import binar.and3.kelompok1.secondhand.model.ProfileModel
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val authAPI: AuthAPI,
    private val dao: UserDAO
) {
    suspend fun getProfile(): ProfileModel {
        return dao.getUser().let {
            ProfileModel(
                id = it?.id.hashCode(),
                full_name = it?.fullName.toString(),
                email = it?.email.toString(),
                password = it?.password.toString(),
                phoneNumber = it?.phoneNumber.toString(),
                city = it?.city.toString(),
                address = it?.address.toString(),
                imageUrl = it?.imageUrl.toString()
            )
        }
    }

    suspend fun uploadImage(token: String, image: MultipartBody.Part): Resource<UpdateUserDataResponse> {
        authAPI.updateUser(
            token = token
        ).let {
            if (it.isSuccessful) {
                updateImageProfile(image = it.body()?.imageUrl.orEmpty())
                return Resource(
                    status = Status.SUCCESS,
                    data = it.body(),
                    message = null
                )
            } else {
                return Resource(
                    status = Status.ERROR,
                    data = null,
                    message = it.errorBody().toString()
                )
            }
        }
    }

    suspend fun updateImageProfile(image: String) {
        val profile = dao.getUser()
        val updateProfile = UserEntity(
            id = profile?.id.hashCode(),
            fullName = profile?.fullName.orEmpty(),
            email = profile?.email.orEmpty(),
            imageUrl = image,
            address = profile?.address.orEmpty(),
            city = profile?.city.orEmpty(),
            phoneNumber = profile?.phoneNumber.orEmpty(),
            password = profile?.password.orEmpty()
        )
    }

    suspend fun removeUser(): Int {
        val profile = dao.getUser()
        val removeProfile = UserEntity(
            id = profile?.id.hashCode(),
            fullName = profile?.fullName.toString(),
            email = profile?.email.toString(),
            password = profile?.password.toString(),
            phoneNumber = profile?.phoneNumber.toString(),
            city = profile?.city.toString(),
            address = profile?.address.toString(),
            imageUrl = profile?.imageUrl.toString()
        )
        return dao.removeUser(removeProfile)
    }
}