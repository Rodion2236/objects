import org.junit.Before
import org.junit.Test
import ru.netology.Comments
import ru.netology.Likes
import ru.netology.Post
import ru.netology.WallSeervice
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class WallSeerviceTest {

 @Before
 fun clearBeforeTest() {
  WallSeervice.clear()
 }

 //минус дублирования кода
 private fun createPostForTest(
  id: Int = 0,
  text: String = "text",
  likes: Likes = Likes(0, false, true, true),
  comments: Comments = Comments(0, true, true, false, false)
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
 val result = WallSeervice.add(post1)
 assertNotEquals(0, result.id)
 }

@Test
 fun updateTrue() {

 val post = WallSeervice.add(createPostForTest())

 val updatePost = WallSeervice.update(
  createPostForTest(
   id = post.id,
   text = "update"
  )
 )

 assertTrue(updatePost)
 }

 @Test
 fun updateFalse() {

  WallSeervice.add(createPostForTest())
  val updatePost = WallSeervice.update(createPostForTest(id = 12345))

  assertFalse(updatePost)
 }
}