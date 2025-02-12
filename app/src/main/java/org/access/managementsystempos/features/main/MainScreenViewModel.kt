package org.access.managementsystempos.features.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.graphics.createBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.thibseisel.identikon.Identicon
import io.github.thibseisel.identikon.drawToBitmap
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.access.managementsystempos.domain.models.Preference
import org.access.managementsystempos.domain.models.PreferenceKey
import org.access.managementsystempos.domain.models.ResponseType
import org.access.managementsystempos.domain.repository.LocalRepository
import org.access.managementsystempos.domain.repository.RemoteRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
class MainScreenViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModel() {
    var error by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var loginToken by mutableStateOf("")
    var name by mutableStateOf("")
    var loginTime by mutableStateOf(Clock.System.now())
    var role by mutableStateOf("")
    var avatar by mutableStateOf(createBitmap(128, 128))

    init {
        Identicon.fromValue(Uuid.random(), 128).drawToBitmap(avatar)

        viewModelScope.launch {
            loginToken = localRepository.getPreference(PreferenceKey.LOGIN_TOKEN)!!.value
            name = localRepository.getPreference(PreferenceKey.CASHIER_NAME)!!.value.lowercase()
            loginTime =
                Instant.parse(localRepository.getPreference(PreferenceKey.LOGIN_TIME)!!.value)
            role = remoteRepository.getRole().message.lowercase()
        }
    }

    fun onLogout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            val loginToken = localRepository.getPreference(PreferenceKey.LOGIN_TOKEN)!!.value
            val logout = remoteRepository.logout(loginToken)

            when (logout.responseType) {
                ResponseType.SUCCESS -> {
                    localRepository.savePreference(Preference(PreferenceKey.LOGIN_TOKEN.name, ""))
                    localRepository.savePreference(Preference(PreferenceKey.CASHIER_NAME.name, ""))
                    localRepository.savePreference(Preference(PreferenceKey.LOGIN_TIME.name, ""))
                    localRepository.savePreference(Preference(PreferenceKey.ROLE.name, ""))
                    onLogoutComplete()
                }

                ResponseType.ERROR -> {
                    error = true
                    errorMessage = logout.message
                }
            }
        }
    }

    fun getLoginTimeFormatted(): String {
        return loginTime.toLocalDateTime(TimeZone.currentSystemDefault())
            .format(LocalDateTime.Format {
                date(
                    LocalDate.Format {
                        monthName(MonthNames.ENGLISH_ABBREVIATED)
                        char(' ')
                        dayOfMonth()
                        chars(", ")
                        year()
                    }
                )
                chars(" - ")
                time(
                    LocalTime.Format {
                        hour(); char(':'); minute()
                    }
                )
            })
    }
}