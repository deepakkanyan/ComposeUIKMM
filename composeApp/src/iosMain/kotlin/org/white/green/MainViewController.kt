package org.white.green

import androidx.compose.ui.window.ComposeUIViewController
import initKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(configure = { }) {
    initKoin()
    App()
}