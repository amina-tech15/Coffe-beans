package com.example.model

enum class SeatingZone(val title: String, val description: String, val idealFor: String) {
    WINDOW_HIGH_TOP("Window High-Top", "Floor-to-ceiling glass street view of Neelam Commercial", "Solo coffee lovers & people watchers"),
    LEATHER_BOOTH("Plush Leather Booth", "Intimate corner banquette with warm fluted wood backdrop", "Couples & private conversations"),
    STUDY_WORKSTATION("Study Workstation", "Ergonomic oak desk, multi-plug AC power & USB-C ports", "University students & remote work"),
    COMMUNITY_LOUNGE("Central Community Table", "Spacious solid walnut table near the espresso bar", "Student study groups & team huddles")
}

data class TableReservation(
    val id: String,
    val guestName: String,
    val contactNumber: String,
    val zone: SeatingZone,
    val partySize: Int,
    val dateString: String,
    val timeSlot: String,
    val specialOccasion: String = "Casual Visit",
    val confirmedTime: Long = System.currentTimeMillis()
)

data class CustomerReview(
    val author: String,
    val role: String,
    val comment: String,
    val rating: Int,
    val favoriteDrink: String
)
