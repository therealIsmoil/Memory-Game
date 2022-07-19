package uz.gita.memorygamegita.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.memorygamegita.data.model.GameData
import uz.gita.memorygamegita.data.model.LevelEnum

interface GetDataByLevelUseCase {
    fun getDataByLevel(level: LevelEnum, type: Int): Flow<List<GameData>>
}