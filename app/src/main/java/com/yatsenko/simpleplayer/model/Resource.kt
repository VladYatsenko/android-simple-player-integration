package com.yatsenko.simpleplayer.model

import com.yatsenko.simpleplayer.player.abstraction.model.MediaResource

class Resource constructor(

    val id:String,

    val type: String,

    val title: String,

    val source: String

): MediaResource {

    override fun getMediaResourceId(): String = id

    override fun getMediaResourceType(): String = type

    override fun getMediaResourceTitle(): String = title

    override fun getMediaResourceSource(): String = source

}