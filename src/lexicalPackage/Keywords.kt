package lexicalPackage

class Keywords {
    companion object {
        val dataTypesClasses=
            hashSetOf("double", "float", "int", "string", "bool","unit") //long has been removed
        const val ifClass = "if"
        const val elseClass = "else"
        const val whenClass = "when"
        const val defaultClass = "default"
        val continueBreakClasses = hashSetOf("continue", "break")
        const val caseClass = "case"
        const val loopClass = "loop"
        const val doClass = "do"
        const val whileClass = "while"
        val varConsClass = hashSetOf("var", "cons")
        const val funClass = "fun"
        const val classClass = "class"
        const val abstractClass = "abstract"
        const val interfaceClass = "interface"
        const val overrideClass = "override"
        val accessModifiersClass = hashSetOf("public", "private", "protected")
        const val staticClass = "static"
        const val returnClass = "return"
        const val thisClass = "this"
        const val constructorClass = "constructor"
        const val superClass = "super"
        val implementExtendClass = hashSetOf("ext") //imp removed
        const val nullClass = "null"
        val trueFalseClass = hashSetOf("true", "false")
       // const val unitClass = "unit"
        const val openClass = "open"
        const val mainMethodClass = "main"
        const val mainClass = "mainclass"
    }
}