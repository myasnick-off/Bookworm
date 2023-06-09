package com.dev.miasnikoff.feature_tabs.domain.model

enum class QueryFields(val type: String) {
    IN_TITLE(type = "intitle:"),
    IN_AUTHOR(type = "inauthor:"),
    IN_PUBLISHER(type = "inpublisher:"),
    SUBJECT(type = "subject:")
}