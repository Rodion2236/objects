import org.junit.Test
import ru.netology.Post
import ru.netology.WallSeervice
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class WallSeerviceTest {

@Test
 fun add() {
 val post1 = Post(
  1,
  5,
  23,
  26,
  date = "08.05.2025".length,
  "text",
  20,
  4,
  true,
  18,
  canPin = true,
  isPinned = true
 )
 val result = WallSeervice.add(post1)
 assertNotEquals(post1.id, result.id)
 }

@Test
 fun updateTrue() {
 val post1 = Post(
  1,
  5,
  23,
  26,
  date = "08.05.2025".length,
  "text",
  20,
  4,
  true,
  18,
  canPin = true,
  isPinned = true
 )

 val idPost = WallSeervice.add(post1).id
 val post2 = Post(
  idPost,
  5,
  23,
  26,
  date = "08.05.2025".length,
  "text",
  20,
  4,
  true,
  18,
  canPin = true,
  isPinned = true
 )
 val result = WallSeervice.update(post2)
 assertEquals(true, result)
 }

 @Test
 fun updateFalse() {
  val post1 = Post(
   1,
   5,
   23,
   26,
   date = "08.05.2025".length,
   "text",
   20,
   4,
   true,
   18,
   canPin = true,
   isPinned = true
  )

  val idPost = WallSeervice.add(post1).id
  val post2 = Post(
   idPost + 1,
   5,
   23,
   26,
   date = "08.05.2025".length,
   "text",
   20,
   4,
   true,
   18,
   canPin = true,
   isPinned = true
  )
  val result = WallSeervice.update(post2)
  assertEquals(false, result)
 }
}