package com.furkancoskun.marvelcharacters.data.model

data class CharacterData (
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: ArrayList<CharacterResult>
)