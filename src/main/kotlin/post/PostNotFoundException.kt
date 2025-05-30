package ru.netology.post

class PostNotFoundException(message: String) : Exception(message)
class CommentNotFoundException(message: String) : Exception(message)
class InvalidReasonException(message: String) : Exception(message)