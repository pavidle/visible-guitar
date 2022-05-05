package com.example.visible_guitar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.interactor.ReceiveImageUseCase
import com.example.domain.interactor.SendImageUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.ImageResponseEntity
import com.example.domain.model.SubscribeDataEntity
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.model.ReceiveData
import com.example.visible_guitar.model.SubscribeData
import com.example.visible_guitar.viewmodel.base.BaseDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.opencv.core.Mat
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val sendImageUseCase: SendImageUseCase,
    private val receiveImageUseCase: ReceiveImageUseCase,
    private val subscribeDataMapper: Mapper<SubscribeData, SubscribeDataEntity>,
    private val receiveDataMapper: Mapper<ImageResponseEntity, ReceiveData>
) : BaseDetailViewModel() {

    private val _updatedData = MutableLiveData<ReceiveData>()
    val updatedData: LiveData<ReceiveData> = _updatedData

    init {
        launchObservers()
    }

    fun subscribeOnUpdate(mat: Mat) {
        launchCoroutine {
            sendImageUseCase(
                subscribeDataMapper.convert(
                    SubscribeData(mat)
                )
            )
        }
    }


    private fun launchObservers() {
        launchCoroutine {
            receiveImageUseCase(Unit).collect {
                _updatedData.value = receiveDataMapper.convert(it)
            }
        }
    }
}