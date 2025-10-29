package com.example.gamelog.data.model

enum class GameStatus(val value: Int, val displayName: String) {
    JOGANDO(1, "Jogando"),
    BACKLOG(2, "Backlog"),
    LISTA_DE_DESEJOS(3, "Lista de Desejos"),
    FINALIZADO(4, "Finalizado"),
    ABANDONADO(5, "Abandonado");

    companion object {
        fun fromValue(value: Any): GameStatus? {
            return entries.find { it.value == value }
        }
    }
}
