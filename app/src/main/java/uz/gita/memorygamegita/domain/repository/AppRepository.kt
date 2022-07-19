package uz.gita.memorygamegita.domain.repository

import uz.gita.memorygamegita.data.model.GameData
import uz.gita.memorygamegita.data.model.LevelEnum

interface AppRepository {
    suspend fun getDataByLevel(level: LevelEnum, type: Int): List<GameData>
}