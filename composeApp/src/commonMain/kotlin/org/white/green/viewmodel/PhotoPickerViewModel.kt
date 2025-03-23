package org.white.green.viewmodel

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.white.green.PhotoPicker

class PhotoPickerViewModel : KoinComponent {
    private val photoPicker: PhotoPicker by inject()

    fun selectPhoto(onPhotoSelected: (String) -> Unit) {
        photoPicker.pickPhoto(onPhotoSelected)
    }
} 