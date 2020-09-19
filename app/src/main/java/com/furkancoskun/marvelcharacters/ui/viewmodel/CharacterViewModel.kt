package com.furkancoskun.marvelcharacters.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.furkancoskun.marvelcharacters.data.repository.CharacterRepository
import com.furkancoskun.marvelcharacters.utils.Resource
import com.furkancoskun.marvelcharacters.utils.ResponseHandler
import kotlinx.coroutines.Dispatchers

class CharacterViewModel : ViewModel() {
    //val selectedAddress = MutableLiveData<Character>()

    fun getAllCharacter(ts: String, apikey: String, hash: String, limit: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = CharacterRepository.getCharacters(ts, apikey, hash, limit)))
        } catch (exception: Exception) {
            // emit(ResponseHandler().handleException(exception))
        }
    }

}