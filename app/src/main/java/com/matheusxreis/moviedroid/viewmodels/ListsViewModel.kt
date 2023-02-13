package com.matheusxreis.moviedroid.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.matheusxreis.moviedroid.data.Repository
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {


    val lists = repository.localDataSource.readLists().asLiveData()


    fun addList(name:String) = viewModelScope.launch(Dispatchers.IO){

        val newList = ListEntity(
            name = name,
            coverUrl = ""
        )

        repository.localDataSource.insertList(newList)
    }

    fun deleteAllLists() = viewModelScope.launch (Dispatchers.IO){
        repository.localDataSource.deleteAllLists()
    }
    fun deleteList(id:String) = viewModelScope.launch (Dispatchers.IO) {
        repository.localDataSource.deleteList(id)
    }

    fun updateListName(listEntity: ListEntity) = viewModelScope.launch (Dispatchers.IO){
        repository.localDataSource.updateNameList(listEntity)
    }
}