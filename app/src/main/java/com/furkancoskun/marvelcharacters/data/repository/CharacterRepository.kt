package com.furkancoskun.marvelcharacters.data.repository

import com.furkancoskun.marvelcharacters.data.api.ApiFactory

object CharacterRepository {

    suspend fun getCharacters(ts: String, apikey: String, hash: String, limit: String) =
        ApiFactory.characterService.getCharacters(ts, apikey, hash, limit)

}