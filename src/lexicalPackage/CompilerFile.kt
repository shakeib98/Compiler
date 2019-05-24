package lexicalPackage

import listOfTokens
import java.io.FileReader
import java.io.FileWriter
import java.lang.NumberFormatException

var lineNo = 0

var classPart = ""

fun breakWords() {
    val fileReader = FileReader("code.txt")
    val code = fileReader.readText()
    val codeLines = code.split("\n")
    fileReader.close()

    var temp = ""

    var stringFlag = false
    var singleLineCommentFlag = false
    var multilineCommentFlag = false
    var operatorFlag = false
    var floatCheckingFlag = false

    for (line in codeLines) {

        lineNo++

        for (index in 0 until line.length) {
            if (line[index].toString() == "") {
                println("NEW LINE")
            }
            if (multilineCommentFlag) {
                if (line[index].toString() == "*" && line[index + 1].toString() == "/") {
                    multilineCommentFlag = false
                    operatorFlag = true
                }
                continue
            }
            if (singleLineCommentFlag) {
                if (index == line.length - 1) {
                    singleLineCommentFlag = false
                }
                continue
            }
            if (stringFlag) {

                if (index != 0 && line[index].toString() == "\"" && line[index - 1].toString() == "\\" && line[index - 2].toString() == "\\") {
                    stringFlag = false
                    temp += line[index]
                    checkConditions(temp)
                    temp = ""
                    continue
                }
                if (index != 0 && line[index].toString() == "\"" && line[index - 1].toString() != "\\") {
                    stringFlag = false
                    temp += line[index]
                    checkConditions(temp)
                    temp = ""
                    continue
                }
                if (index == line.length - 1) {
                    stringFlag = false
                    temp += line[index]
                    checkConditions(temp)
                    temp = ""
                    continue
                }
                temp += line[index]
                continue
            }
            if (operatorFlag) {
                operatorFlag = false
                continue
            }
            if (floatCheckingFlag) {
                floatCheckingFlag = false
                continue
            }
            if (punctuatorList.contains(line[index].toString()) || Operators.plusMinusClass.contains(line[index].toString())
                || Operators.comparisionOpClass.contains(line[index].toString()) || line[index].toString() == Operators.logicalOpAnd
                || line[index].toString() == Operators.logicalOpNot || line[index].toString() == Operators.logicalOpOr
                || line[index].toString() == Operators.equalsToClass || Operators.multiplyDivideModulusClass.contains(
                    line[index].toString()
                )
                || line[index].toString() == " " || line[index].toString() == "\"" || line[index].toString() == "/"
            ) {
                if (line[index].toString() == "\"") {
                    if (!temp.isEmpty()) {
                        stringFlag = true
                        checkConditions(temp)
                        temp = ""
                        temp += line[index]
                        continue
                    }
                    stringFlag = true
                    temp += line[index]
                    continue
                }
                if (line[index].toString() == " ") {
                    if (temp.isEmpty()) {
                        continue
                    } else {
                        checkConditions(temp)
                        temp = ""
                        continue
                    }
                }
                if (line[index].toString() == "/" && line[index + 1].toString() == "/") {
                    singleLineCommentFlag = true
                    continue
                }
                if (line[index].toString() == "/" && line[index + 1].toString() == "*") {
                    multilineCommentFlag = true
                    continue
                }
                if (line[index].toString() == Operators.equalsToClass || Operators.comparisionOpClass.contains(line[index].toString())) {
                    if (!temp.isEmpty()) {
                        checkConditions(temp)
                        temp = ""
                    }
                    temp += line[index]
                    if (index != line.length - 1) {
                        val tempString = temp + line[index + 1]
                        if (Operators.comparisionOpClass.contains(tempString)) {
                            operatorFlag = true
                            temp += line[index + 1]
                            checkConditions(temp)
                            temp = ""
                            continue
                        }
                    }
                    checkConditions(temp)
                    temp = ""
                    continue
                }
                if (punctuatorList.contains(line[index].toString())) {
                    if (line[index].toString() == ".") {
                        try {
                            Integer.parseInt(temp)
                            try {
                                Integer.parseInt(line[index + 1].toString())
                                temp += line[index]
                                continue
                            } catch (e: NumberFormatException) {
                                checkConditions(temp)
                                temp = ""
                                temp += line[index]
                                checkConditions(temp)
                                temp = ""
                                continue
                            }
                        } catch (e: NumberFormatException) {
                            checkConditions(temp)
                            temp = ""
                            temp += line[index]
                            try {
                                Integer.parseInt(line[index + 1].toString())
                                temp += line[index + 1]
                                floatCheckingFlag = true
                                continue
                            } catch (e: NumberFormatException) {
                                checkConditions(temp)
                                temp = ""
                                continue
                            }
                        }
                    } else {
                        if (!temp.isEmpty()) {
                            checkConditions(temp)
                            temp = ""
                            temp += line[index]
                            /**
                             * ye 2 lines pange karhin thin idk why :3
                             */
                            checkConditions(temp)
                            temp = ""
                            continue
                        } else if (temp.isEmpty()) {
                            temp += line[index]
                            checkConditions(temp)
                            temp = ""
                            continue
                        }
                    }
                }
                if (line[index].toString() == Operators.logicalOpAnd) {
                    if (!temp.isEmpty()) {
                        checkConditions(temp)
                        temp = ""
                        temp += line[index]
                        checkConditions(temp)
                        temp = ""
                    } else if (temp.isEmpty()) {
                        temp += line[index]
                        checkConditions(temp)
                        temp = ""
                        continue
                    }
                }
                if (line[index].toString() == Operators.logicalOpOr) {
                    if (!temp.isEmpty()) {
                        checkConditions(temp)
                        temp = ""
                        temp += line[index]
                        checkConditions(temp)
                        temp = ""
                    } else if (temp.isEmpty()) {
                        temp += line[index]
                        checkConditions(temp)
                        temp = ""
                        continue
                    }
                }
                if (line[index].toString() == Operators.logicalOpNot) {
                    if (!temp.isEmpty()) {
                        checkConditions(temp)
                        temp = ""
                        temp += line[index]
                        if (line[index].toString() == Operators.logicalOpNot && line[index + 1].toString() == Operators.equalsToClass) {
                            operatorFlag = true
                            temp += line[index + 1]
                            checkConditions(temp)
                            temp = ""
                            continue
                        }
                        checkConditions(temp)
                        temp = ""
                    } else if (temp.isEmpty()) {
                        temp += line[index]
                        if (line[index].toString() == Operators.logicalOpNot && line[index + 1].toString() == Operators.equalsToClass) {
                            operatorFlag = true
                            temp += line[index + 1]
                            checkConditions(temp)
                            temp = ""
                            continue
                        }
                        checkConditions(temp)
                        temp = ""
                        continue
                    }
                }
                if (Operators.plusMinusClass.contains(line[index].toString())) {
                    if (!temp.isEmpty()) {
                        checkConditions(temp)
                        temp = ""
                        temp += line[index]
                        if (index != line.length - 1) {
                            val tempString = temp + line[index + 1]
                            if (Operators.incDecClass.contains(tempString) || Operators.assignmentOpClass.contains(
                                    tempString
                                )
                            ) {
                                operatorFlag = true
                                temp = tempString
                                checkConditions(temp)
                                temp = ""
                                continue
                            }
                        }
                        checkConditions(temp)
                        temp = ""
                        continue
                    } else if (temp.isEmpty()) {
                        temp += line[index]
                        if (index != line.length - 1) {
                            val tempString = temp + line[index + 1]
                            if (Operators.incDecClass.contains(tempString) || Operators.assignmentOpClass.contains(
                                    tempString
                                )
                            ) {
                                operatorFlag = true
                                temp = tempString
                                checkConditions(temp)
                                temp = ""
                                continue
                            }
                            val tempStringTemp = line[index + 1].toString()
                            try {
                                Integer.parseInt(tempStringTemp)
                                temp += line[index + 1]
                                operatorFlag = true
                                continue
                            } catch (e: NumberFormatException) {

                            }
                        }

                    }

                }
                if (Operators.multiplyDivideModulusClass.contains(line[index].toString())) {
                    if (!temp.isEmpty()) {
                        checkConditions(temp)
                        temp = ""
                        temp += line[index]
                        if (index != line.length - 1) {
                            val tempString = temp + line[index + 1]
                            if (Operators.assignmentOpClass.contains(tempString)) {
                                operatorFlag = true
                                temp = tempString
                                checkConditions(temp)
                                temp = ""
                                continue
                            }
                        }
                        checkConditions(temp)
                        temp = ""
                        continue
                    } else if (temp.isEmpty()) {
                        temp += line[index]
                        if (index != line.length - 1) {
                            val tempString = temp + line[index + 1]
                            if (Operators.assignmentOpClass.contains(tempString)) {
                                operatorFlag = true
                                temp = tempString
                                checkConditions(temp)
                                temp = ""
                                continue
                            }
                        }
                        checkConditions(temp)
                        temp = ""
                        continue
                    }

                }


            } else {
                temp += line[index]
            }
        }
        if (!temp.isEmpty()) {
            checkConditions(temp)
        }
        temp = ""


    }
}

fun checkConditions(temp: String) {
    var temp = temp
    when {
        isKeyword(temp) -> {
            if (classPart == temp) {
                temp = ""
            }
            listOfTokens.add(Token(classPart, temp, lineNo))
        }
        isOperator(temp) -> {
            if (classPart == temp) {
                temp = ""
            }
            listOfTokens.add(Token(classPart, temp, lineNo))
        }
        isPunctuator(temp) -> {
            if (classPart == temp) {
                temp = ""
            }
            listOfTokens.add(Token(classPart, temp, lineNo))
        }
        isFloatConstatn(temp) -> {
            listOfTokens.add(Token(classPart, temp, lineNo))
        }
        isIntConstant(temp) -> {
            listOfTokens.add(Token(classPart, temp, lineNo))
        }
        isStringConstant(temp) -> {
            //Nothing to do here, temp is added in the method
        }
        isIdentifier(temp) -> {
            listOfTokens.add(Token(classPart, temp, lineNo))
        }
        else -> {
            classPart = ConstantClass.INVALID_LEXENE
            listOfTokens.add(Token(classPart, temp, lineNo))
        }
    }
}

fun isKeyword(temp: String): Boolean {
    when {
        Keywords.dataTypesClasses.contains(temp) -> {
            classPart = ConstantClass.DATA_TYPE
            return true
        }
        temp == Keywords.mainClass -> {
            classPart = ConstantClass.MAIN_CLASS
            return true
        }
        temp == Keywords.mainMethodClass -> {
            classPart = ConstantClass.MAIN_METHOD
            return true
        }
        temp == Keywords.ifClass -> {
            classPart = ConstantClass.IF
            return true
        }
        temp == Keywords.elseClass -> {
            classPart = ConstantClass.ELSE
            return true
        }
        temp == Keywords.whenClass -> {
            classPart = ConstantClass.WHEN
            return true
        }
        temp == Keywords.defaultClass -> {
            classPart = ConstantClass.DEFAULT
            return true
        }
        Keywords.continueBreakClasses.contains(temp) -> {
            classPart = ConstantClass.CONTINUE_BREAK
            return true
        }
        temp == Keywords.caseClass -> {
            classPart = ConstantClass.CASE
            return true
        }
        temp == Keywords.loopClass -> {
            classPart = ConstantClass.LOOP
            return true
        }
        temp == Keywords.doClass -> {
            classPart = ConstantClass.DO
            return true
        }
        temp == Keywords.whileClass -> {
            classPart = ConstantClass.WHILE
            return true
        }
        Keywords.varConsClass.contains(temp) -> {
            classPart = ConstantClass.VAR_CONS
            return true
        }
        temp == Keywords.funClass -> {
            classPart = ConstantClass.FUN
            return true
        }
        temp == Keywords.classClass -> {
            classPart = ConstantClass.CLASS
            return true
        }
        temp == Keywords.abstractClass -> {
            classPart = ConstantClass.ABSTRACT
            return true
        }
        temp == Keywords.interfaceClass -> {
            classPart = ConstantClass.INTERFACE
            return true
        }
        temp == Keywords.overrideClass -> {
            classPart = ConstantClass.OVERRIDE
            return true
        }
        Keywords.accessModifiersClass.contains(temp) -> {
            if (temp == ConstantClass.PUBLIC_CLASS) {
                classPart = ConstantClass.PUBLIC_CLASS
            } else if (temp == ConstantClass.PRIVATE_CLASS) {
                classPart = ConstantClass.PRIVATE_CLASS
            } else if (temp == ConstantClass.PROTECTED_CLASS) {
                classPart = ConstantClass.PROTECTED_CLASS
            }
            return true
        }
        temp == Keywords.staticClass -> {
            classPart = ConstantClass.STATIC
            return true
        }
        /*temp == Keywords.thisClass -> {
            classPart = ConstantClass.THIS
            return true
        }*/
        temp == Keywords.constructorClass -> {
            classPart = ConstantClass.CONSTRUCTOR
            return true
        }
        temp == Keywords.returnClass -> {
            classPart = ConstantClass.RETURN
            return true
        }
        /*temp == Keywords.superClass -> {
            classPart = ConstantClass.SUPER
            return true
        }*/
        Keywords.implementExtendClass.contains(temp) -> {
            //if(temp == "ext"){
            classPart = ConstantClass.EXTEND
            //}
            /*else if(temp == "imp"){
                classPart = ConstantClass.IMPLEMENET
            }*/
            return true
        }
        temp == Keywords.nullClass -> {
            classPart = ConstantClass.NULL
            return true
        }
        Keywords.trueFalseClass.contains(temp) -> {
            classPart = ConstantClass.TRUE_FALSE
            return true
        }
        /*temp == Keywords.unitClass -> {
            classPart = ConstantClass.UNIT
            return true
        }*/
        temp == Keywords.openClass -> {
            classPart = ConstantClass.OPEN
            return true
        }
        else -> return false
    }
}

fun isOperator(temp: String): Boolean {
    when {
        Operators.plusMinusClass.contains(temp) -> {
            classPart = ConstantClass.PLUS_MINUS
            return true
        }
        Operators.multiplyDivideModulusClass.contains(temp) -> {
            classPart = ConstantClass.MUL_DIV_MOD
            return true
        }
        Operators.comparisionOpClass.contains(temp) -> {
            classPart = ConstantClass.COMPARISON_OP
            return true
        }
        Operators.assignmentOpClass.contains(temp) -> {
            classPart = ConstantClass.ASSIGNMENT_OP
            return true
        }
        temp == Operators.equalsToClass -> {
            classPart = ConstantClass.EQUALS
            return true
        }
        Operators.incDecClass.contains(temp) -> {
            classPart = ConstantClass.INC_DEC
            return true
        }
        temp == Operators.logicalOpAnd -> {
            classPart = ConstantClass.AND
            return true
        }
        temp == Operators.logicalOpOr -> {
            classPart = ConstantClass.OR
            return true
        }
        temp == Operators.logicalOpNot -> {
            classPart = ConstantClass.NOT
            return true
        }
        else -> return false
    }
}

fun isPunctuator(temp: String): Boolean {
    if (punctuatorList.contains(temp)) {
        when (temp) {
            "." -> {
                classPart = ConstantClass.DOT
                return true
            }
            "(" -> {
                classPart = ConstantClass.ROUND_BRAC_OPEN
                return true
            }
            ")" -> {
                classPart = ConstantClass.ROUND_BRAC_CLOSE
                return true
            }
            "{" -> {
                classPart = ConstantClass.CURL_BRAC_OPEN
                return true
            }
            "}" -> {
                classPart = ConstantClass.CURL_BRAC_CLOSE
                return true
            }
            "[" -> {
                classPart = ConstantClass.SQ_BRAC_OPEN
                return true
            }
            "]" -> {
                classPart = ConstantClass.SQ_BRAC_CLOSE
                return true
            }
            "," -> {
                classPart = ConstantClass.COMMA
                return true
            }
            ";" -> {
                classPart = ConstantClass.SEMI_COLON
                return true
            }
            ":" -> {
                classPart = ConstantClass.COLON
                return true
            }
        }
    }
    return false
}

fun isFloatConstatn(temp: String): Boolean {
    if (temp.matches(patternFloat.toRegex())) {
        classPart = ConstantClass.FLOAT_CONSTANT
        return true
    }
    return false
}

fun isIntConstant(temp: String): Boolean {
    if (temp.matches(patternInt.toRegex())) {
        classPart = ConstantClass.INT_CONSTANT
        return true
    }
    return false
}

fun isStringConstant(temp: String): Boolean {
    if (temp.matches(patternString.toRegex())) {
        classPart = ConstantClass.STRING_CONSTANT
        listOfTokens.add(
            Token(
                classPart, temp.substring(1, temp.length - 1),
                lineNo
            )
        )
        return true
    }
    return false
}

fun isIdentifier(temp: String): Boolean {
    if (temp.matches(patternIdentifier.toRegex())) {
        classPart = ConstantClass.IDENTIFIER
        return true
    }
    return false
}

fun writeInFile() {
    val fileWriter = FileWriter("tokens")
    listOfTokens.add(Token("$", "", ++lineNo))
    for (tokens in listOfTokens) {
        val tempToken = "<${tokens.classPart} , ${tokens.valuePart} , ${tokens.lineNumber}>"
        fileWriter.write(tempToken)
        fileWriter.write("\n")
    }
    fileWriter.flush()
    fileWriter.close()
}