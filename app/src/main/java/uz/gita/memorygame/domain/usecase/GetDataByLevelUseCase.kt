package uz.gita.memorygame.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.memorygame.data.model.GameData
import uz.gita.memorygame.data.model.LevelEnum

interface GetDataByLevelUseCase {
    fun getDataByLevel(level: LevelEnum, type: Int): Flow<List<GameData>>
}