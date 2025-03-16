package org.white.green.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.white.green.profile.ProfileViewModel
import org.white.green.profile.ui.basicProfile.BasicProfileRepository
import org.white.green.profile.ui.basicProfile.BasicProfileViewModel
import org.white.green.profile.ui.family.FamilyRepository
import org.white.green.profile.ui.family.FamilyViewModel
import org.white.green.profile.ui.personal.PersonalRepository
import org.white.green.profile.ui.personal.PreferencesViewModel

val appModule = module {
    single { PersonalRepository() }
    single { FamilyRepository() }
    single { BasicProfileRepository() }
    viewModel { FamilyViewModel(get()) }
    viewModel { PreferencesViewModel(get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { BasicProfileViewModel(get()) }
}
