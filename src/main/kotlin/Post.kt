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
    val comments: CommentsInfo? = null,
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

data class CommentsInfo(
    val count: Int,
    val canPost: Boolean,
    val groupsCanPost: Boolean,
    val canClose: Boolean,
    val canOpen: Boolean
)

object WallService {

    private var posts = emptyArray<Post>()
    private var postsId = 1
    private var comments = emptyArray<Comment>()
    private var commentsId = 1
    private var reports = emptyArray<Report>()

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

    fun createComment(postId: Int, comment: Comment): Comment {
        var postFound = false

        for (post in posts) {
            if (post.id == postId) {
                postFound = true
                break
            }
        }

        if (!postFound) {
            throw PostNotFoundException("Пост с таким ID не найден")
        }

        val newComment = comment.copy(id = commentsId++, postId = postId)
        comments += newComment
        return newComment
    }

    fun reportComment(ownerId: Int, commentId: Int, reason: Int): Boolean {
        val comment = comments.firstOrNull { it.id == commentId }
        if (comment == null) {
            throw CommentNotFoundException("Комментарий с таким ID не найден")
        }

        val validReasons = listOf(0, 1, 2, 3, 4, 5, 6, 8)
        if (reason !in validReasons) {
            throw InvalidReasonException("Неверное значение причины reason")
        }

        // Добавляем репорт
        if (reports.none { it.commentId == commentId && it.reason == reason }) {
            reports += Report(commentId = commentId, reason = reason)
            return true
        }

        return false // репорт уже есть
    }

    //сброс
    fun clear() {
        posts = emptyArray()
        comments = emptyArray()
        reports = emptyArray()

        postsId = 1
        commentsId = 1
    }
}