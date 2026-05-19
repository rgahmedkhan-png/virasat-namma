package com.example.virasatnamma.model

data class Site(
    val id: String,
    val name: String,
    val location: String,
    val distance: String,
    val era: String,
    val description: String,
    val hiddenFact: String,
    val tags: List<String>,
    var checkedIn: Boolean = false,
    val icon: String
)

val HERITAGE_SITES = listOf(
    Site(
        id = "KT001",
        name = "Hampi Virupaksha Temple",
        location = "Hampi, Ballari",
        distance = "0.8 km",
        era = "Vijayanagara Empire (14th C)",
        description = "The living temple of Lord Shiva, still actively worshipped since the 7th century. Inscriptions on its walls tell the story of great emperors and poets.",
        hiddenFact = "The temple's gopuram casts a perfect inverted shadow inside the pillared hall at dawn — a deliberate architectural feat by ancient builders.",
        tags = listOf("Temple", "UNESCO", "Shiva"),
        icon = "🛕"
    ),
    Site(
        id = "KT002",
        name = "Belur Chennakeshava",
        location = "Belur, Hassan",
        distance = "1.2 km",
        era = "Hoysala Dynasty (1117 AD)",
        description = "Built over 103 years by King Vishnuvardhana, every inch of this temple is covered in intricate sculptures depicting celestial dancers, mythological scenes, and wildlife.",
        hiddenFact = "The 'Shilabhalika' figures (bracket figures) each have a unique pose — no two are alike across the entire temple.",
        tags = listOf("Temple", "Hoysala", "Sculpture"),
        icon = "⛩️"
    ),
    Site(
        id = "KT003",
        name = "Aihole Rock Inscriptions",
        location = "Aihole, Bagalkot",
        distance = "2.4 km",
        era = "Chalukya Period (634 AD)",
        description = "These inscriptions by poet Ravikirti are the earliest dated literary document in Kannada and Sanskrit, praising King Pulakesi II's victory over Harsha.",
        hiddenFact = "Aihole contains 125 temples making it the 'cradle of temple architecture' — a living laboratory where builders experimented with new styles.",
        tags = listOf("Inscription", "Chalukya", "Literature"),
        icon = "📜"
    ),
    Site(
        id = "KT004",
        name = "Mysore Chamundi Hill",
        location = "Mysuru",
        distance = "3.1 km",
        era = "Wodeyar Dynasty (12th C)",
        description = "Atop Chamundi Hill, 1000 steps lead to the temple of Goddess Chamundeshwari. Along the way, a 5-metre Nandi bull carved from a single rock guards the path.",
        hiddenFact = "The Nandi statue was carved in 1659 AD under Dodda Devaraja Wodeyar — visitors traditionally worship Nandi before ascending to the goddess.",
        tags = listOf("Temple", "Wodeyar", "Pilgrimage"),
        icon = "🏔️"
    )
)
