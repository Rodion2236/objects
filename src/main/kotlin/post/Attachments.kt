package ru.netology.post

sealed class Attachments(val type: String)

data class PhotoAttachments(val photo: Photo) : Attachments("photo")
data class AudioAttachments(val audio: Audio) : Attachments("audio")
data class VideoAttachments(val video: Video) : Attachments("video")
data class FileAttachments(val file: File) : Attachments("file")
data class LinkAttachments(val attachedLink: AttachedLink) : Attachments("attached Link")

//Фото
data class Photo(
    val id: Int,
    val ownerId: Int,
    val text: String,
    val date: Int
)

//Видео
data class Video(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val duration: Int
)

//Аудио
data class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    val title: String,
    val duration: Int
)

//Файл
data class File(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val size: Int,
    val url: String
)

//Прикрепленная ссылка
data class AttachedLink(
    val url: String,
    val title: String,
    val description: String
)

//Коммент
data class Comment(
    val id: Int,
    val postId: Int,
    val text: String
)

//репорты
data class Report(
    val commentId: Int,
    val reason: Int
)