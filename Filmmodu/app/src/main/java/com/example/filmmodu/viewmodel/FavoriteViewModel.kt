package com.example.filmmodu.viewmodel

import androidx.lifecycle.ViewModel
import com.example.filmmodu.repository.FavRepository
import com.example.filmmodu.repository.HomeRepository
import javax.inject.Inject

class FavoriteViewModel@Inject constructor(
    val homeRepository: HomeRepository,
    val favRepository: FavRepository
): ViewModel() {


}