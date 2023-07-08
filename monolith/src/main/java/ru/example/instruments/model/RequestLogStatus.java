package ru.example.instruments.model;

public enum RequestLogStatus {
    PENDING, //ожидает таймаута для получения данных
    PROCESSING_FINISHED, // ответ получен и провалидирован, сформировано сообщение AMQP
    BLOOMBERG_ERROR, //ошибка в формате ответа Bloomberg
    INTERNAL_ERROR, //ответ от Bloomberg корректен, но произошла ошибка при разборе ответа и формировании сообщения AMQP
    TRIES_NUMBER_EXCEEDED //превышено количество попыток
}
