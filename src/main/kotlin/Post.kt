package ru.netology

data class Post(
    val id: Int,
    val ownerId: Int,
    val fromId: Int? = null,
    val createdBy: Int? = null,
    val date: Int,
    val text: String?,
    val replyOwnerId: Int? = null,
    val replyPostId: Int? = null,
    val friendsOnly: Boolean = false,
    val likes: Likes? = null,
    val comments: Comments? = null,
    val canPin: Boolean = false,
    val isPinned: Boolean = false,
    val attachments: Array<Attachments> = emptyArray()
)

data class Likes(
    val count: Int,
    val userLikes: Boolean,
    val canLike: Boolean,
    val canPublish: Boolean
)

data class Comments(
    val count: Int,
    val canPost: Boolean,
    val groupsCanPost: Boolean,
    val canClose: Boolean,
    val canOpen: Boolean
)

object WallService {

    private var posts = emptyArray<Post>()
    private var postsId = 1

    fun add(post: Post): Post {
        posts += post.copy(id = postsId++)
        return posts.last()
    }

    fun update(newPost: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (post.id == newPost.id) {
                posts[index] = newPost.copy(
                    date = post.date,
                    likes = post.likes,
                    fromId = post.fromId,
                    comments = post.comments
                )
                return true
            }
        }
        return false
    }

    //сброс
    fun clear() {
        posts = emptyArray()
        postsId = 1
    }
}