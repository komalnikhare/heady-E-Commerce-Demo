package com.heady.myproducts.viewModels

import android.arch.lifecycle.ViewModel
import com.heady.myproducts.dbConfig.dao.RankingDao
import com.heady.myproducts.dbConfig.models.Ranking
import io.reactivex.Completable

class RankingViewModel(private val rankingDao: RankingDao) : ViewModel() {
    fun insert(ranking : Ranking) : Completable{
        return Completable.fromAction {
            rankingDao.insert(ranking);
        }
    }

    fun deleteRanking() :Completable{
        return Completable.fromAction {
            rankingDao.deleteAll()
        }
    }
}