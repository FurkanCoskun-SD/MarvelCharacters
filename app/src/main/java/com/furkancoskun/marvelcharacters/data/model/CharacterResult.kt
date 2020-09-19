package com.furkancoskun.marvelcharacters.data.model

data class CharacterResult (
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: CharacterThumbnail,
    val comics: CharacterComics
)