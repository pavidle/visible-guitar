package com.example.visible_guitar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.mapper.Mapper
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.viewmodel.base.BaseDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.opencv.core.Mat
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(

) : BaseDetailViewModel() {

//    private val _updatedData = MutableLiveData<ReceiveData>()
//    val updatedData: LiveData<ReceiveData> = _updatedData
//
//    init {
//        launchObservers()
//    }

//    fun subscribeOnUpdate(mat: Mat) {
//        sendImageUseCase(
//            subscribeDataMapper.convert(
//                SubscribeData(mat)
//            )
//        )
//    }
//
//    private fun launchObservers() {
//        launchCoroutine {
//            receiveImageUseCase(Unit).collect {
//                _updatedData.value = receiveDataMapper.convert(it)
//            }
//        }
//    }
}