package com.example.data.mapper

import com.example.data.common.imagepreprocessor.ImagePreprocessorService
import com.example.data.model.SubscribeDataDTO
import com.example.domain.mapper.Mapper
import com.example.domain.model.SubscribeDataEntity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import org.opencv.core.Mat


class SubscribeDataDTOMapperTest {

    private val imagePreprocessorService = mock<ImagePreprocessorService>()
    private lateinit var subscribeDataDTOMapper: Mapper<SubscribeDataEntity, SubscribeDataDTO>

    @Before
    fun setUp() {
        subscribeDataDTOMapper = SubscribeDataDTOMapper(imagePreprocessorService)
    }

    @Test
    fun `when mapping from entity to dto then correct data is returned`() {
        val mat = mock<Mat>()
        imagePreprocessorService.stub {
            on { processFrom<String>(mat) } doReturn "base64"
        }

        val subscribeDataEntity = SubscribeDataEntity(mat)
        val actual = subscribeDataDTOMapper.convert(subscribeDataEntity)
        val expected = SubscribeDataDTO(base64 = "base64")
        assertEquals(expected, actual)
    }
}