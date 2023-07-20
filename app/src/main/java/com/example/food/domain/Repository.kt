package com.example.food.domain

import com.example.food.domain.remote.RemoteDataResource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataResource: RemoteDataResource,
    localDataSource: localDataSource
) {
    val remote = remoteDataResource
    val local = localDataSource
}