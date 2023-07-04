package com.example.food.domain

import com.example.food.domain.remote.RemoteDataResource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataResource: RemoteDataResource
) {
     val remote=remoteDataResource
}