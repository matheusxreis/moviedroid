package com.matheusxreis.moviedroid.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.matheusxreis.moviedroid.data.Repository
import com.matheusxreis.moviedroid.data.database.entities.ListEntity
import com.matheusxreis.moviedroid.data.database.entities.ListItemEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {


    val lists = repository.localDataSource.readLists().asLiveData()
    val favorites = repository.localDataSource.readFavorite()
    val selectedAddLists: MutableLiveData<ArrayList<ListEntity>> = MutableLiveData()
    //val items = repository.localDataSource

    // List
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
    fun updateListValue(listEntity: ListEntity) = viewModelScope.launch(Dispatchers.IO){
        repository.localDataSource.updateNameList(listEntity)
    }


    // List item

    fun addListItem(listItemEntity: ListItemEntity) = viewModelScope.launch(Dispatchers.IO){
        repository.localDataSource.insertItem(listItemEntity)
    }

    fun readListItem(listCode:String) = repository.localDataSource.readListItem(listCode).asLiveData()



    fun updateFavoritesValues(favoriteList:ListEntity) = viewModelScope.launch (Dispatchers.IO){
                repository.localDataSource.updateAmmountAndCover(
                    listEntity = favoriteList
                )

    }

}