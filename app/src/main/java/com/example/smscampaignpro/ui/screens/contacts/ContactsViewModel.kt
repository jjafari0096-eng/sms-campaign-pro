package com.example.smscampaignpro.ui.screens.contacts

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.local.entity.Contact
import com.example.core.domain.repository.ContactRepository
import com.example.core.data.util.ExcelParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val excelParser: ExcelParser
) : ViewModel() {
    val contacts: StateFlow<List<Contact>> = contactRepository.getAllContacts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun importContactsFromExcel(uri: Uri) {
        viewModelScope.launch {
            val contacts = excelParser.parseContactsFromExcel(uri)
            contactRepository.insertContacts(contacts)
        }
    }
}