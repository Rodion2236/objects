import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import ru.netology.post.*
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class WallServiceTest {

 @Before
 fun clearBeforeTest() {
  WallService.clear()
 }

 //минус дублирования кода
 private fun createPostForTest(
  id: Int = 0,
  text: String = "text",
  likes: Likes = Likes(0, false, true, true),
  comments: CommentsInfo = CommentsInfo(0, true, true, false, false)
 ): Post {
  return Post(
   id = id,
   ownerId = 5,
   fromId = 23,
   createdBy = 26,
   date = 8052025,
   text = text,
   replyOwnerId = 20,
   replyPostId = 4,
   friendsOnly = true,
   likes = likes,
   comments = comments,
   canPin = true,
   isPinned = true
  )
 }

@Test
 fun add() {

 val post1 = createPostForTest()
 val result = WallService.add(post1)
 assertNotEquals(0, result.id)
 }

@Test
 fun updateTrue() {

 val post = WallService.add(createPostForTest())

 val updatePost = WallService.update(
  createPostForTest(
   id = post.id,
   text = "update"
  )
 )

 assertTrue(updatePost)
 }

 @Test
 fun updateFalse() {

  WallService.add(createPostForTest())
  val updatePost = WallService.update(createPostForTest(id = 12345))

  assertFalse(updatePost)
 }

 @Test
 fun shouldThrow() {

  val post = WallService.add(createPostForTest())

  val comment = Comment(id = 0, postId = post.id, text = "Новый коммент")

  val createdComment = WallService.createComment(post.id, comment)

  assertEquals(post.id, createdComment.postId)
  assertEquals("Новый коммент", createdComment.text)
  assertNotEquals(0, createdComment.id)
 }

 @Test(expected = PostNotFoundException::class)
 fun shouldThrowFalse() {

  val comment = Comment(id = 0, postId =12345, text = "Новый коммент выкинул ошибку")
  WallService.createComment(comment.postId, comment)
 }

 // репорт успешен
 @Test
 fun AddReportToComment() {
  val post = WallService.add(createPostForTest())
  val comment = Comment(id = 0, postId = post.id, text = "Оскорбление")
  val addedComment = WallService.createComment(post.id, comment)

  val result = WallService.reportComment(ownerId = 100, commentId = addedComment.id, reason = 6)

  assertTrue(result)
 }

 // комментарий не найден
 @Test(expected = CommentNotFoundException::class)
 fun reportCommentNotFound() {
  WallService.reportComment(ownerId = 100, commentId = 999, reason = 0)
 }

 // неверная причина
 @Test
 fun reportCommentWhenInvalidReason() {
  val post = WallService.add(createPostForTest())
  val comment = WallService.createComment(
   post.id,
   Comment(id = 0, postId = post.id, text = "Очередной коммент")
  )

  assertFailsWith<InvalidReasonException> {
   WallService.reportComment(ownerId = 100, commentId = comment.id, reason = 7)
  }
 }

 // дубликат репорта
 @Test
 fun reportCommentWhenAlreadyReported() {

  val post = WallService.add(createPostForTest())
  val comment = WallService.createComment(
   post.id,
   Comment(id = 0, postId = post.id, text = "Спам")
  )

  // Первый репорт
  val firstReport = WallService.reportComment(ownerId = 100, commentId = comment.id, reason = 0)
  assertTrue(firstReport)

  // второй репорт(дубликат)
  val secondReport = WallService.reportComment(ownerId = 100, commentId = comment.id, reason = 0)
  assertFalse(secondReport)
 }
}