package uz.gita.memorygame.domain.repository

import uz.gita.memorygame.data.model.GameData
import uz.gita.memorygame.data.model.LevelEnum

interface AppRepository {
    suspend fun getDataByLevel(level: LevelEnum, type: Int): List<GameData>
}