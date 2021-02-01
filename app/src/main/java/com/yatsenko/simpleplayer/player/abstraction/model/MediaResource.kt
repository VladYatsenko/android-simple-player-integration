package com.yatsenko.simpleplayer.player.abstraction.model

interface MediaResource {

    fun getMediaResourceId(): String //entity id

    fun getMediaResourceType(): String //type as video, audio etc

    fun getMediaResourceTitle(): String //name of resource

    fun getMediaResourceSource(): String //source - uri, url etc

}