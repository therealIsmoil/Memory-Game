package uz.gita.memorygamegita.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.memorygamegita.data.model.GameData
import uz.gita.memorygamegita.data.model.LevelEnum
import uz.gita.memorygamegita.domain.repository.AppRepository
import uz.gita.memorygamegita.domain.usecase.GetDataByLevelUseCase
import javax.inject.Inject

class GetDataByLevelUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : GetDataByLevelUseCase {
    override fun getDataByLevel(level: LevelEnum, type: Int): Flow<List<GameData>> = flow {
        val useCaseData = ArrayList<GameData>()
        useCaseData.addAll(repository.getDataByLevel(level, type))
        useCaseData.addAll(useCaseData)
        useCaseData.shuffle()
        emit(useCaseData)
    }

}