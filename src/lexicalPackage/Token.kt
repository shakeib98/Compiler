package lexicalPackage

data class Token(
    var classPart: String,
    var valuePart: String,
    var lineNumber: Int
)