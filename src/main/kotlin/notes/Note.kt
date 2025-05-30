package ru.netology.notes

data class Note(
    override var id: Int = 0,
    val title: String,
    val text: String,
    override var deleted: Boolean = false
): Identification

data class NoteComments(
    override var id: Int = 0,
    val noteId: Int,
    val text: String,
    override var deleted: Boolean = false
): Identification

object NoteService {

    private val notes = GenericRepository<Note>()
    private val comments = GenericRepository<NoteComments>()

    //Заметки
    fun add(note: Note): Note = notes.add(note)

    fun get(): List<Note> = notes.getAll()

    fun getById(id: Int): Note? = notes.getById(id)

    fun update(note: Note): Boolean = notes.update(note)

    fun deleteNote(id: Int): Boolean = notes.delete(id)

    //Комменты
    fun createComment(noteId: Int, comment: NoteComments): NoteComments =
        comments.add(comment.copy(noteId = noteId))

    fun getComments(noteId: Int): List<NoteComments> =
        comments.getAll().filter { it.noteId == noteId }

    fun editComment(comment: NoteComments): Boolean = comments.update(comment)

    fun deleteComment(id: Int): Boolean = comments.delete(id)

    fun restoreComment(id: Int): Boolean = comments.restore(id)

    fun clear() {
        notes.clear()
        comments.clear()
    }
}