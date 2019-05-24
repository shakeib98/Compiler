package lexicalPackage

class Operators{
    companion object {
        val plusMinusClass = hashSetOf("+", "-")
        val multiplyDivideModulusClass = hashSetOf("*", "/", "%")
        val comparisionOpClass = hashSetOf("<", ">", "<=", ">=", "==", "!=")
        val assignmentOpClass = hashSetOf("+=", "-=", "*=", "/=", "%=")
        const val equalsToClass = "="
        val incDecClass = hashSetOf("++", "--")
        const val logicalOpAnd = "&" //before it was &&
        const val logicalOpOr = "|" //before it was ||
        const val logicalOpNot = "!"
        const val terminator = ";"
    }
}