package com.example.sikoma.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sikoma.data.local.UserPreferences
import com.example.sikoma.data.models.Admin
import com.example.sikoma.data.remote.config.ApiService
import com.example.sikoma.data.remote.response.GenericResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminRepository(
    private val apiService: ApiService,
    private val pref: UserPreferences
) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllAdmins(): LiveData<GenericResponse<List<Admin>>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<List<Admin>>>()
        val client = apiService.getAllAdmins()

        client.enqueue(object : Callback<GenericResponse<List<Admin>>> {
            override fun onResponse(
                call: Call<GenericResponse<List<Admin>>>,
                response: Response<GenericResponse<List<Admin>>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<List<Admin>>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
        return resultLiveData
    }

    fun getAdminByName(organizationName: String) : LiveData<GenericResponse<Admin>>{
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Admin>>()
        val client = apiService.getAdminByName(organizationName = organizationName)

        client.enqueue(object : Callback<GenericResponse<Admin>> {
            override fun onResponse(
                call: Call<GenericResponse<Admin>>,
                response: Response<GenericResponse<Admin>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Admin>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
        return resultLiveData
    }

    fun getAdminById(id: String): LiveData<GenericResponse<Admin>> {
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Admin>>()
        val client = apiService.getAdminById(id)

        client.enqueue(object : Callback<GenericResponse<Admin>> {
            override fun onResponse(
                call: Call<GenericResponse<Admin>>,
                response: Response<GenericResponse<Admin>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Admin>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    fun updateAdmin(id: String, admin: Admin) :LiveData<GenericResponse<Admin>>{
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Admin>>()
        val client = apiService.updateAdmin(id = id, updateAdminRequest = admin)

        client.enqueue(object : Callback<GenericResponse<Admin>> {
            override fun onResponse(
                call: Call<GenericResponse<Admin>>,
                response: Response<GenericResponse<Admin>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                    CoroutineScope(Dispatchers.IO).launch {
                        response.body()?.data?.let {
                            pref.saveAdmin(it)
                        }
                    }
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Admin>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })
        return resultLiveData
    }

    fun updateAdminProfilePic(id: String, image: MultipartBody.Part?) :LiveData<GenericResponse<Admin>>{
        _isLoading.value = true
        val resultLiveData = MutableLiveData<GenericResponse<Admin>>()
        val client = apiService.updateAdminProfilePic(id = id, image = image)

        client.enqueue(object : Callback<GenericResponse<Admin>> {
            override fun onResponse(
                call: Call<GenericResponse<Admin>>,
                response: Response<GenericResponse<Admin>>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                    CoroutineScope(Dispatchers.IO).launch {
                        response.body()?.data?.let {
                            pref.saveAdmin(it)
                        }
                    }
                } else {
                    resultLiveData.value = GenericResponse(
                        message = response.code().toString(),
                        status = response.body()?.status,
                        data = null
                    )
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<GenericResponse<Admin>>, t: Throwable) {
                resultLiveData.value = GenericResponse(
                    message = "500",
                    status = "error",
                    data = null
                )
                _isLoading.value = false
            }
        })

        return resultLiveData
    }

    companion object {
        @Volatile
        private var instance: AdminRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): AdminRepository =
            instance ?: synchronized(this) {
                instance ?: AdminRepository(apiService, pref)
            }.also { instance = it }
    }
}
