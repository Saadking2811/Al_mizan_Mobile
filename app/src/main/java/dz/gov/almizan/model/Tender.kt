package dz.gov.almizan.model

data class Tender(
    val id: String,
    val title: String,
    val reference: String,
    val budget: String,
    val deadline: String,
    val status: String
)
