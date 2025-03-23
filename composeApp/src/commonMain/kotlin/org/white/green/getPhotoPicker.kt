package org.white.green

interface PhotoPicker {
    fun pickPhoto(onPhotoSelected: (String) -> Unit)
}
