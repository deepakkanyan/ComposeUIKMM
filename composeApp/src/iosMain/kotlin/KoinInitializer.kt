import org.koin.core.context.startKoin
import org.white.green.di.appModule

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}