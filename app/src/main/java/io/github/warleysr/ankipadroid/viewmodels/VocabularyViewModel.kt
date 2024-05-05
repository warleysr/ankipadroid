package io.github.warleysr.ankipadroid.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.github.warleysr.ankipadroid.api.OpenCV
import io.github.warleysr.ankipadroid.screens.settings.HSVColor


class VocabularyViewModel: ViewModel() {

    var bitmap: MutableState<Bitmap?> = mutableStateOf(null)
        private set

    var text: MutableState<String?> = mutableStateOf(null)
        private set

    fun processBitmap(bitmap: Bitmap, rotation: Int) {
        this.bitmap.value = bitmap
        OpenCV.processImage(this.bitmap.value!!, rotation, onSuccess = { text.value = it })
    }

    fun applyAdjustment(bitmap: Bitmap, lower: HSVColor, upper: HSVColor, onResult: (Bitmap) -> Unit) {
        val processedImage = OpenCV.applyMaskToImage(bitmap, lower.toScalar(), upper.toScalar())
        onResult(processedImage)
    }

}