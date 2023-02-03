package com.matheusxreis.moviedroid.data

import com.matheusxreis.moviedroid.data.network.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    val remoteDataSource: RemoteDataSource
){
}