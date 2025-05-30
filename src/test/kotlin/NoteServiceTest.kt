import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import ru.netology.notes.Note
import ru.netology.notes.NoteComments
import ru.netology.notes.NoteNotFoundException
import ru.netology.notes.NoteService
import ru.netology.post.*
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class NoteServiceTest {

    @Before
    fun clearBeforeTest() {
        NoteService.clear()
    }

    //добавляем айдишник
    @Test
    fun addNoteWithNewId() {
        val note = Note(title = "Заголовок", text = "Текст")
        val added = NoteService.add(note)

        assertEquals(1, added.id)
        assertEquals(1, NoteService.get().size)
    }

    //возврат по айдишнику
    @Test
    fun getByNoteId() {
        val note = NoteService.add(Note(title = "Заметка", text = "Текст"))
        val found = NoteService.getById(note.id)

        assertNotNull(found)
        assertEquals("Заметка", found.title)
    }

    //метка, что заметка удалена
    @Test
    fun noteMarkedAsDeleted() {
        val note = NoteService.add(Note(title = "Заметка", text = "Текст"))
        NoteService.deleteNote(note.id)

        assertNull(NoteService.getById(note.id))
    }

    //добавление коммента к заметке
    @Test
    fun createCommentToExistingNote() {
        val note = NoteService.add(Note(title = "Заметка", text = "Текст"))
        val comment = NoteComments(text = "Хорошая заметка!", noteId = note.id)
        val added = NoteService.createComment(note.id, comment)

        assertEquals(1, added.id)
        assertEquals(1, NoteService.getComments(note.id).size)
    }

    //заметка не найдена, коммент некуда добавлять
    @Test(expected = NoteNotFoundException::class)
    fun createCommentWhenNoteNotFound() {
        NoteService.createComment(999, NoteComments(text = "Это не должно добавиться", noteId = 999))
    }

    //редактирование коммента
    @Test
    fun editComment() {
        val note = NoteService.add(Note(title = "Заметка", text = "Текст"))
        val comment = NoteService.createComment(note.id, NoteComments(text = "Старый текст", noteId = note.id))
        val updated = comment.copy(text = "Новый текст")

        val result = NoteService.editComment(updated)

        assertTrue(result)
        assertEquals("Новый текст", NoteService.getComments(note.id).first().text)
    }

    //метка, что коммент удален
    @Test
    fun deleteCommentFromList() {
        val note = NoteService.add(Note(title = "Заметка", text = "Текст"))
        val comment = NoteService.createComment(note.id, NoteComments(text = "Комментарий", noteId = note.id))

        NoteService.deleteComment(comment.id)

        assertTrue(NoteService.getComments(note.id).isEmpty())
    }

    //попытка двойного удаления
    @Test(expected = CommentNotFoundException::class)
    fun deleteCommentIfAlreadyDeleted() {
        val note = NoteService.add(Note(title = "Заметка", text = "Текст"))
        val comment = NoteService.createComment(note.id, NoteComments(text = "Комментарий", noteId = note.id))

        NoteService.deleteComment(comment.id)
        NoteService.deleteComment(comment.id) //вторая попытка удалить → ошибка
    }

    //восстанавливает удалённый комментарий
    @Test
    fun restoreDeletedComment() {
        val note = NoteService.add(Note(title = "Заметка", text = "Текст"))
        val comment = NoteService.createComment(note.id, NoteComments(text = "Комментарий", noteId = note.id))

        NoteService.deleteComment(comment.id)
        NoteService.restoreComment(comment.id)

        assertEquals(1, NoteService.getComments(note.id).size)
    }
}