package com.example.myapplication.ui

import com.example.myapplication.data.Email
import com.example.myapplication.data.MailboxType
import com.example.myapplication.data.local.LocalEmailsDataProvider

data class ReplyUiState(
    val mailboxes: Map<MailboxType, List<Email>> = emptyMap(),
    val currentMailbox: MailboxType = MailboxType.Inbox,
    val currentSelectedEmail: Email = LocalEmailsDataProvider.defaultEmail,
    val isShowingHomepage: Boolean = true
){
    val currentMailboxEmails: List<Email> by lazy { mailboxes[currentMailbox]!! }
}
