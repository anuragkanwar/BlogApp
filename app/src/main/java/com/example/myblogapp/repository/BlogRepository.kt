package com.example.myblogapp.repository

import android.util.Log
import com.example.myblogapp.data.DataOrException
import com.example.myblogapp.model.respond.*
import com.example.myblogapp.model.response.*
import com.example.myblogapp.network.BlogApi
import com.example.myblogapp.utils.SessionManager
import com.example.myblogapp.utils.isNetworkConnected
import javax.inject.Inject

class BlogRepository @Inject constructor(
    val blogApi: BlogApi,
    val sessionManager: SessionManager
) {

    // ==================CHECK=====================
    suspend fun check(): DataOrException<String, Boolean, Exception> {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            val result = blogApi.check()
            return if (result.isNotEmpty()) {
                DataOrException(data = result.toString())
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }

    // ==================USERS=====================
    suspend fun registerUser(
        user: RegisterUser
    ): DataOrException<User, Boolean, Exception> {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            val result = blogApi.createAccount(user)
            return if (result.success) {
                sessionManager.updateSession(
                    token = result.data!!.token,
                    name = result.data.user.firstName,
                    email = result.data.user.email,
                    imageUrl = result.data.user.imgUrl
                )
                DataOrException(data = result.data.user)
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }


    suspend fun loginUser(
        user: LoginUser
    ): DataOrException<User, Boolean, Exception> {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            val result = blogApi.login(user)
            return if (result.success) {
                sessionManager.updateSession(
                    token = result.data!!.token,
                    name = result.data.user.firstName,
                    email = result.data.user.email,
                    imageUrl = result.data.user.imgUrl
                )
                DataOrException(data = result.data.user)
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }


    suspend fun updateUser(
        user: UpdateUser
    ): DataOrException<User, Boolean, Exception> {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            val token = sessionManager.getCurrentToken()
                ?: return DataOrException(e = Exception("user not Logged In..."))
            val result = blogApi.updateUser(token = "Bearer $token", user = user)
            return if (result.success) {
                sessionManager.updateSession(
                    token = result.data!!.token,
                    name = result.data.user.firstName,
                    email = result.data.user.email,
                    imageUrl = result.data.user.imgUrl
                )
                DataOrException(data = result.data.user)
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }

    suspend fun deleteAccount(): DataOrException<String, Boolean, Exception> {
        return try {

            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No Internet connection"))
            }
            val token = sessionManager.getCurrentToken()
                ?: return DataOrException(e = Exception("user not Logged In..."))

            val response = blogApi.deleteUser(token = "Bearer $token")
            if (response.success) {
                sessionManager.logout()
                DataOrException(data = response.message)
            } else {
                DataOrException(e = Exception(response.message))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            DataOrException(e = e)
        }
    }

    // ==================BLOGS=====================

    suspend fun publishBlog(
        blog: CreateBlog
    ): DataOrException<String, Boolean, Exception> {
        try {
            val token = sessionManager.getCurrentToken()
                ?: return DataOrException(data = "Not logged in")
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No Internet Connection!"))
            }
            val result = blogApi.publishBlog(
                token = "Bearer $token",
                blog = blog
            )
            return if (result.success) {
                DataOrException(data = "Blog created")
            } else {
                DataOrException(e = Exception(result.message))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }

    suspend fun getAllBlogs(): DataOrException<List<MinBlog>, Boolean, Exception> {
        Log.d("TAG", "getAllBlogs: Enter")
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            Log.d("TAG", "getAllBlogs: network")
            val token = sessionManager.getCurrentToken()
                ?: return DataOrException(e = Exception("user not Logged In..."))
            Log.d("TAG", "getAllBlogs: token")
            val result = blogApi.getAllBlogs(
                token = "Bearer $token",
            )
            Log.d("TAG", "getAllBlogs: result")
            return if (result.success) {
                DataOrException(data = result.data)
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
    }


    suspend fun getAllBlogsByFilter(
        category: String
    ): DataOrException<List<MinBlog>, Boolean, Exception> {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            val token = sessionManager.getCurrentToken()
                ?: return DataOrException(e = Exception("user not Logged In..."))

            val result = blogApi.getAllBlogsByFilter(
                token = "Bearer $token",
                category = category
            )
            return if (result.success) {
                DataOrException(data = result.data)
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }

    suspend fun getAllFavBlogs(): DataOrException<List<MinBlog>, Boolean, Exception> {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            val token = sessionManager.getCurrentToken()
                ?: return DataOrException(e = Exception("user not Logged In..."))

            val result = blogApi.getAllFavBlogs(
                token = "Bearer $token",
            )

            return if (result.success) {
                DataOrException(data = result.data)
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }

    suspend fun getSingleBlogs(
        id: String
    ): DataOrException<FullBlog, Boolean, Exception> {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            val token = sessionManager.getCurrentToken()
                ?: return DataOrException(e = Exception("user not Logged In..."))

            val result = blogApi.getSingleBlogs(
                token = "Bearer $token",
                id = id
            )

            return if (result.success) {
                DataOrException(data = result.data)
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }

    // ==================FAV=======================

    suspend fun addFavBlog(
        id: String
    ): DataOrException<String, Boolean, Exception> {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            val token = sessionManager.getCurrentToken()
                ?: return DataOrException(e = Exception("user not Logged In..."))

            val result = blogApi.addFavBlog(
                token = "Bearer $token",
                id = id
            )

            return if (result.success) {
                DataOrException(data = result.message)
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }

    suspend fun deleteFavBlog(
        id: String
    ): DataOrException<String, Boolean, Exception> {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            val token = sessionManager.getCurrentToken()
                ?: return DataOrException(e = Exception("user not Logged In..."))

            val result = blogApi.deleteFavBlog(
                token = "Bearer $token",
                id = id
            )

            return if (result.success) {
                DataOrException(data = result.message)
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }

    // ==================COMMENT===================

    suspend fun getAllComments(
        id: Int
    ): DataOrException<List<CommentWithUser>, Boolean, Exception> {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            val token = sessionManager.getCurrentToken()
                ?: return DataOrException(e = Exception("user not Logged In..."))

            val result = blogApi.getComments(
                token = "Bearer $token",
                id = id
            )
            return if (result.success) {
                DataOrException(data = result.data)
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }


    suspend fun addComment(
        comment: AddComment
    ): DataOrException<CommentWithUser, Boolean, Exception> {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            val token = sessionManager.getCurrentToken()
                ?: return DataOrException(e = Exception("user not Logged In..."))

            val result = blogApi.addComment(
                token = "Bearer $token",
                comment = comment
            )
            return if (result.success) {
                DataOrException(data = result.data)
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }

    suspend fun updateComment(
        comment: UpdateComment
    ): DataOrException<String, Boolean, Exception> {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            val token = sessionManager.getCurrentToken()
                ?: return DataOrException(e = Exception("user not Logged In..."))

            val result = blogApi.updateComment(
                token = "Bearer $token",
                comment = comment
            )

            return if (result.success) {
                DataOrException(data = result.message)
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }

    suspend fun deleteComment(
        comment: DeleteComment
    ): DataOrException<String, Boolean, Exception> {
        try {
            if (!isNetworkConnected(sessionManager.context)) {
                return DataOrException(e = Exception("No internet connection"))
            }
            val token = sessionManager.getCurrentToken()
                ?: return DataOrException(e = Exception("user not Logged In..."))

            val result = blogApi.deleteComment(
                token = "Bearer $token",
                comment = comment
            )

            return if (result.success) {
                DataOrException(data = result.message)
            } else {
                DataOrException(e = Exception("not reachable"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataOrException(e = e)
        }
    }

    suspend fun isLoggedIn(): Boolean {
        return sessionManager.getCurrentToken() != null
    }


}