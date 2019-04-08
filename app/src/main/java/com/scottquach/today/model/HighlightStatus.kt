package com.scottquach.today.model

enum class HighlightStatus(val status: String) {
    COMPLETED("COMPLETED") {
        override fun toString(): String {
            return "COMPLETED"
        }
    },
    INCOMPLETE("INCOMPLETE") {
        override fun toString(): String {
            return "INCOMPLETE"
        }
    },
    PENDING("PENDING") {
        override fun toString(): String {
            return "PENDING"
        }
    },
    NONE("NONE") {
        override fun toString(): String {
            return "NONE"
        }
    }
}