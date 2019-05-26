import lexicalPackage.ConstantClass
import semanticPackage.*

var result = false
var tokenNo = 0
var flag = false
var classTableCounter = 0
var mainClassFlag = false

var referenceTableModel = ReferenceTableModel()
var classDataTableModel = ClassDataTableModel()

var parameter = ""

fun enter(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.INTERFACE
        || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
        || listOfTokens[tokenNo].classPart == ConstantClass.CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.MAIN_CLASS
        || listOfTokens[tokenNo].classPart == "$"
    ) {
        if (top()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.MAIN_CLASS) {

                if (mainClass()) {
                    if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                        tokenNo++
                        if (listOfTokens[tokenNo].classPart == ConstantClass.INTERFACE
                            || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                            || listOfTokens[tokenNo].classPart == ConstantClass.CLASS
                            || listOfTokens[tokenNo].classPart == ConstantClass.OPEN
                            || listOfTokens[tokenNo].classPart == ConstantClass.MAIN_CLASS
                            || listOfTokens[tokenNo].classPart == "$"
                        ) {
                            if (top()) {
                                println("TRUE")

                            } else {
                                println("FALSE ERROR AT LINE# ${listOfTokens[tokenNo].lineNumber}")
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }

                } else {
                    result = false
                }
            } else {
                println("FALSE ERROR AT LINE# ${listOfTokens[tokenNo].lineNumber} AND ${listOfTokens[tokenNo].classPart}")
                result = false
            }
        } else {
            println("FALSE ERROR AT LINE# ${listOfTokens[tokenNo].lineNumber} AND ${listOfTokens[tokenNo].classPart}")

            result = false
        }
    } else {
        result = false
    }


    return result
}

fun top(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.INTERFACE) {
        if (interfaceCfg()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                tokenNo++
                if (listOfTokens[tokenNo].classPart == ConstantClass.INTERFACE
                    || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                    || listOfTokens[tokenNo].classPart == ConstantClass.CLASS
                    || listOfTokens[tokenNo].classPart == ConstantClass.OPEN
                    || listOfTokens[tokenNo].classPart == ConstantClass.MAIN_CLASS
                    || listOfTokens[tokenNo].classPart == "$"
                ) {
                    if (top()) {
                        result = true
                        return result
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        }

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.CLASS || listOfTokens[tokenNo].classPart == ConstantClass.OPEN) {
        if (normalClass()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                tokenNo++
                if (listOfTokens[tokenNo].classPart == ConstantClass.INTERFACE
                    || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                    || listOfTokens[tokenNo].classPart == ConstantClass.CLASS
                    || listOfTokens[tokenNo].classPart == ConstantClass.OPEN
                    || listOfTokens[tokenNo].classPart == ConstantClass.MAIN_CLASS
                    || listOfTokens[tokenNo].classPart == "$"
                ) {
                    if (top()) {
                        result = true
                        return result
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }

        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT) {
        if (abstractClass()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                tokenNo++
                if (listOfTokens[tokenNo].classPart == ConstantClass.INTERFACE
                    || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                    || listOfTokens[tokenNo].classPart == ConstantClass.CLASS
                    || listOfTokens[tokenNo].classPart == ConstantClass.OPEN
                    || listOfTokens[tokenNo].classPart == ConstantClass.MAIN_CLASS
                    || listOfTokens[tokenNo].classPart == "$"
                ) {
                    if (top()) {
                        result = true
                        return result
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.MAIN_CLASS) {
        if (!flag) {
            flag = true
            result = true
            return result
        } else {
            result = false
        }

    } else if (listOfTokens[tokenNo].classPart == "$") {
        result = true
        return result
    } else {
        result = false
    }
    return result
}

fun mainClass(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.MAIN_CLASS) {
        referenceTableModel.type = SemanticConstants.MAIN_CLASS_TYPE
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
            referenceTableModel.name = listOfTokens[tokenNo].valuePart
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.EXTEND
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN
            ) {
                if (ext()) {
                    // referenceTableModel.reference = classTableCounter++
                    referenceTableModel.reference = classTableCounter
                    if (insertRefTable(referenceTableModel)) {
                        //classTableList.add(classDataTableModel)
                        referenceTable.add(referenceTableModel)
                        referenceTableModel = ReferenceTableModel()
                    } else {
                        referenceTableModel = ReferenceTableModel()
                        println("ALREADY DECLARED")
                        System.exit(0)
                    }
                    if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                        tokenNo++
                        if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                            || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                            || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                            || listOfTokens[tokenNo].classPart == ConstantClass.CONSTRUCTOR
                            || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                            || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                            || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                            || listOfTokens[tokenNo].classPart == ConstantClass.OPEN
                            || listOfTokens[tokenNo].classPart == ConstantClass.OVERRIDE
                            || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                        ) {
                            if (mainClassBody()) {
                                if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {/*
                                    classTableList.add(classDataTableModel)
                                    classTableCounter++*/
                                    result = true
                                    return result
                                } else {
                                    result = false
                                }
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }

                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun normalClass(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.MAIN_CLASS) {
        result = true
        return result
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.CLASS
    ) {
        if (o()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.CLASS) {
                referenceTableModel.type = SemanticConstants.NORMAL_CLASS_TYPE
                tokenNo++
                if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
                    referenceTableModel.name = listOfTokens[tokenNo].valuePart
                    tokenNo++
                    if (listOfTokens[tokenNo].classPart == ConstantClass.EXTEND
                        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN
                    ) {
                        if (ext()) {
                            referenceTableModel.reference = classTableCounter
                            if (insertRefTable(referenceTableModel)) {
           //                     classTableList.add(classDataTableModel)
                                referenceTable.add(referenceTableModel)
                                referenceTableModel = ReferenceTableModel()
                            } else {
                                referenceTableModel = ReferenceTableModel()
                                println("ALREADY DECLARED")
                                System.exit(0)
                            }
                            if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                                tokenNo++
                                if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                                    || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                                    || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                                    || listOfTokens[tokenNo].classPart == ConstantClass.CONSTRUCTOR
                                    || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                                    || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                                    || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                                    || listOfTokens[tokenNo].classPart == ConstantClass.OPEN
                                    || listOfTokens[tokenNo].classPart == ConstantClass.OVERRIDE
                                    || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                                ) {
                                    if (normalClassBody()) {
                                        if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                                            result = true
                                            return result
                                        } else {
                                            result = false
                                        }

                                    } else {
                                        result = false
                                    }
                                } else {
                                    result = false
                                }
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun abstractClass(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT) {
        referenceTableModel.type = SemanticConstants.ABSTRACT_CLASS_TYPE
        referenceTableModel.category = SemanticConstants.OPEN_CLASS_CATEGORY
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.CLASS) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
                referenceTableModel.name = listOfTokens[tokenNo].valuePart
                tokenNo++
                if (listOfTokens[tokenNo].classPart == ConstantClass.EXTEND
                    || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN
                ) {
                    if (ext()) {
                        if (insertRefTable(referenceTableModel)) {
                            referenceTable.add(referenceTableModel)
                            referenceTableModel.reference = classTableCounter
                            referenceTableModel = ReferenceTableModel()
                        } else {
                            referenceTableModel = ReferenceTableModel()
                            println("ALREADY DECLARED")
                            System.exit(0)
                        }
                        if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                            tokenNo++
                            if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                            ) {
                                if (abstractBody()) {
                                    if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                                        result = true
                                        return result
                                    } else {
                                        result = false
                                    }
                                } else {
                                    result = false
                                }
                            } else {
                                result = false
                            }

                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            }

        } else {
            result = false
        }
    } else {
        result = false
    }

    return result
}

fun abstractBody(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.DATA_TYPE)
        || (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.IDENTIFIER)
    ) {
        if (allAfterPPP()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
            ) {
                if(insertClassTable(classDataTableModel)){
                    classTableList.add(classDataTableModel)
                    classDataTableModel = ClassDataTableModel()
                    classTableCounter++
                }
                if (abstractBody()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            }
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
        if(insertClassTable(classDataTableModel)){
            classTableList.add(classDataTableModel)
            classDataTableModel = ClassDataTableModel()
            classTableCounter++
        }
        result = true
        return result

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
    ) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
            || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
            || (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.DATA_TYPE)
            || (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.IDENTIFIER)
        ) {

        }
        if (allAfterPPP()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
            ) {
                if(insertClassTable(classDataTableModel)){
                    classTableList.add(classDataTableModel)
                    classDataTableModel = ClassDataTableModel()
                    classTableCounter++
                }
                if (abstractBody()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT) {
        if (abstractFun()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
            )  {
                if(insertClassTable(classDataTableModel)){
                classTableList.add(classDataTableModel)
                classDataTableModel = ClassDataTableModel()
                classTableCounter++
            }
                if (abstractBody()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun o(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.OPEN) {
        referenceTableModel.category = SemanticConstants.OPEN_CLASS_CATEGORY
        tokenNo++
        result = true
        return result
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.CLASS) {
        referenceTableModel.category = SemanticConstants.SEALED_CLASS_CATEGORY
        result = true
        return result
    } else {
        result = false
    }
    return result

}

fun abstractFun(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.FUN) {
            if (funDecInt()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun ext(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.EXTEND) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {

            val tcp: TCP? = lookUpRefTable(listOfTokens[tokenNo].valuePart)
            if (tcp != null) {
                if ((referenceTableModel.type == SemanticConstants.INTERFACE_TYPE) &&
                    (tcp.type == SemanticConstants.INTERFACE_TYPE)
                ) {
                    referenceTableModel.parent = listOfTokens[tokenNo].valuePart
                } else if ((referenceTableModel.type == SemanticConstants.ABSTRACT_CLASS_TYPE) &&
                    ((tcp.type == SemanticConstants.ABSTRACT_CLASS_TYPE) || (tcp.type == SemanticConstants.INTERFACE_TYPE))
                ) {
                    referenceTableModel.parent = listOfTokens[tokenNo].valuePart
                } else if (
                    (referenceTableModel.type == SemanticConstants.MAIN_CLASS_TYPE)
                    &&
                    ((tcp.type == SemanticConstants.ABSTRACT_CLASS_TYPE) || (tcp.type == SemanticConstants.INTERFACE_TYPE))

                ) {
                    referenceTableModel.parent = listOfTokens[tokenNo].valuePart
                } else if ((referenceTableModel.type == SemanticConstants.NORMAL_CLASS_TYPE)
                    &&
                    ((tcp.type == SemanticConstants.ABSTRACT_CLASS_TYPE) || (tcp.type == SemanticConstants.INTERFACE_TYPE))
                ) {
                    referenceTableModel.parent = listOfTokens[tokenNo].valuePart
                } else if ((referenceTableModel.type == SemanticConstants.MAIN_CLASS_TYPE)
                    && ((tcp.type == SemanticConstants.NORMAL_CLASS_TYPE) && (tcp.cat == SemanticConstants.OPEN_CLASS_CATEGORY)
                            )
                ) {
                    referenceTableModel.parent = listOfTokens[tokenNo].valuePart
                } else if ((referenceTableModel.type == SemanticConstants.NORMAL_CLASS_TYPE)
                    && ((tcp.type == SemanticConstants.NORMAL_CLASS_TYPE) && (tcp.cat == SemanticConstants.OPEN_CLASS_CATEGORY))
                ) {
                    referenceTableModel.parent = listOfTokens[tokenNo].valuePart
                } else {
                    println("INVALID INHERITANCE")
                    System.exit(0)
                }
            } else {
                println("ID NOT DECLARED")
                System.exit(0)
            }


            tokenNo++
            result = true
            return result
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
        result = true
        return result
    } else {
        result = false
    }
    return result
}

fun interfaceCfg(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.INTERFACE) {
        referenceTableModel.type = SemanticConstants.INTERFACE_TYPE
        referenceTableModel.category = SemanticConstants.OPEN_CLASS_CATEGORY
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
            referenceTableModel.name = listOfTokens[tokenNo].valuePart
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.EXTEND
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN
            ) {
                if (ext()) {
                    if (insertRefTable(referenceTableModel)) {
                        referenceTable.add(referenceTableModel)
                        referenceTableModel.reference = classTableCounter
                        referenceTableModel = ReferenceTableModel()
                    } else {
                        referenceTableModel = ReferenceTableModel()
                        println("ALREADY DECLARED")
                        System.exit(0)
                    }
                    if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                        tokenNo++
                        if (listOfTokens[tokenNo].classPart == ConstantClass.FUN
                            || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                        ) {
                            if (interfaceBody()) {
                                if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                                    result = true
                                    return result
                                } else {
                                    result = false
                                }
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun interfaceBody(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
        if(insertClassTable(classDataTableModel)){
            classTableList.add(classDataTableModel)
            classDataTableModel = ClassDataTableModel()
            classTableCounter++
            println(classTableCounter)
        }
        result = true
        return result
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.FUN) {
        if (funDecInt()) {
            if(insertClassTable(classDataTableModel)){
                classTableList.add(classDataTableModel)
                classTableCounter++
            }
            if (interfaceBody()) {
                result = true
                return result
            } else {
                result = false
            }

        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun funDecInt(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.FUN) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.DATA_TYPE || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
                tokenNo++

                if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN) {
                    tokenNo++
                    if (listOfTokens[tokenNo].classPart == ConstantClass.DATA_TYPE
                        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                    ) {
                        if (paramsDec()) {
                            if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
                                tokenNo++
                                result = true
                                return result
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }

            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun mainClassBody(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || listOfTokens[tokenNo].classPart == ConstantClass.FUN
        || listOfTokens[tokenNo].classPart == ConstantClass.CONSTRUCTOR
        || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.OVERRIDE
        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
    ) {
        mainClassFlag = true
        if (normalClassBody()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.MAIN_METHOD) {
                if (mainMethodCfg()) {
//                    if(insertClassTable(classDataTableModel)){
//                        classTableList.add(classDataTableModel)
//                        classDataTableModel = ClassDataTableModel()
//                        classTableCounter++
//                    }
                    mainClassFlag = true
                    if (normalClassBody()) {
                        result = true
                        return result
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
        result = true
        return result
    } else {
        result = false
    }
    return result
}

fun normalClassBody(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.DATA_TYPE)
        || (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.IDENTIFIER)
    ) {
        if (allAfterPPP()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.CONSTRUCTOR
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.OVERRIDE
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
            ) {

                if(insertClassTable(classDataTableModel)){
                    classTableList.add(classDataTableModel)
                    classDataTableModel = ClassDataTableModel()
                    classTableCounter++
                }else{
                    println("VARIABLE ALREADY DECLARED ON TOP LEVEL")
                    System.exit(0)
                }
                if (normalClassBody()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }

        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.MAIN_METHOD) {
        result = true
        return result

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.CONSTRUCTOR) {
        if (constructorCFG()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.CONSTRUCTOR
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.OVERRIDE
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
            ) {
                if(insertClassTable(classDataTableModel)){
                    classTableList.add(classDataTableModel)
                    classDataTableModel = ClassDataTableModel()
                    classTableCounter++
                }
                else{
                    println("VARIABLE ALREADY DECLARED ON TOP LEVEL")
                    System.exit(0)
                }
                if (normalClassBody()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
    ) {
        if (combinationClassPPPNormal()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.DATA_TYPE)
                || (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.IDENTIFIER)
            ) {
                if (allAfterPPP()) {
                    if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                        || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                        || listOfTokens[tokenNo].classPart == ConstantClass.CONSTRUCTOR
                        || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                        || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                        || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                        || listOfTokens[tokenNo].classPart == ConstantClass.OPEN
                        || listOfTokens[tokenNo].classPart == ConstantClass.OVERRIDE
                        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                    ) {
                        if(insertClassTable(classDataTableModel)){
                            classTableList.add(classDataTableModel)
                            classDataTableModel = ClassDataTableModel()
                            classTableCounter++
                        }else{
                            println("VARIABLE ALREADY DECLARED ON TOP LEVEL")
                            System.exit(0)
                        }
                        if (normalClassBody()) {
                            result = true
                            return result
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                }
            }
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
        if(mainClassFlag){
//            if(insertClassTable(classDataTableModel)){
//                classTableList.add(classDataTableModel)
//                classDataTableModel = ClassDataTableModel()
//                classTableCounter++
//            }
            mainClassFlag = false

        }else{
            if(insertClassTable(classDataTableModel)){
                classTableList.add(classDataTableModel)
                classDataTableModel = ClassDataTableModel()
                classTableCounter++
            }
        }
        result = true
        return result
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.OVERRIDE
    ) {
        if (ooCombination()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.CONSTRUCTOR
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.OVERRIDE
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
            ) {
                if(insertClassTable(classDataTableModel)){
                    classTableList.add(classDataTableModel)
                    classDataTableModel = ClassDataTableModel()
                    classTableCounter++
                }
                if (normalClassBody()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}


fun allAfterPPP(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC) {
        classDataTableModel.tm = SemanticConstants.STATIC
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
            || (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.DATA_TYPE)
            || (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.IDENTIFIER)
        ) {
            if (commonDec()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.DATA_TYPE)
        || (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.IDENTIFIER)
    ) {
        classDataTableModel.tm= SemanticConstants.NORMAL
        if (commonDec()) {
            result = true
            return result
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun constructorCFG(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.CONSTRUCTOR) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.DATA_TYPE
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
            ) {
                if (paramsDec()) {
                    if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
                        tokenNo++
                        if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                            tokenNo++
                            if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                                || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                            ) {
                                if (body()) {
                                    if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                                        tokenNo++
                                        result = true
                                        return result
                                    } else {
                                        result = false
                                    }
                                } else {
                                    result = false
                                }
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }

                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun combinationClassPPPNormal(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
    ) {
        classDataTableModel.am = listOfTokens[tokenNo].classPart
        tokenNo++
        result = true
        return result
    } else {
        result = false
    }
    return result
}

fun ooCombination(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.OVERRIDE
    ) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
            || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
        ) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.FUN) {
                if (funDecCompulsory()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }

        } else {
            result = false
        }

    } else {
        result = false
    }
    return result
}

fun funDecCompulsory(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.FUN) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.DATA_TYPE
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        ) {
            if(listOfTokens[tokenNo].classPart == ConstantClass.DATA_TYPE) classDataTableModel.type = listOfTokens[tokenNo].classPart
            else {
                val tcp:TCP? = lookUpRefTable(listOfTokens[tokenNo].valuePart)
                if(tcp != null){
                    classDataTableModel.type = listOfTokens[tokenNo].valuePart

                }else{
                    println("UNDEFINED DATA TYPE")
                    System.exit(0)
                }
            }
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
                classDataTableModel.name = listOfTokens[tokenNo].valuePart
                tokenNo++
                if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN) {
                    tokenNo++
                    if (listOfTokens[tokenNo].classPart == ConstantClass.DATA_TYPE
                        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                    ) {
                        if (paramsDec()) {
                            if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
                                tokenNo++

                                if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                                    tokenNo++
                                    if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                                        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                        || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                                        || listOfTokens[tokenNo].classPart == ConstantClass.DO
                                        || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                                        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                        || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                                        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                                        || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                                        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                                        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                                        || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                                        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                                        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                                    ) {
                                        if (body()) {
                                            if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                                                tokenNo++
                                                result = true
                                                return result
                                            } else {
                                                result = false
                                            }
                                        } else {
                                            result = false
                                        }
                                    } else {
                                        result = false
                                    }
                                } else {
                                    result = false
                                }

                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun paramsDec(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.DATA_TYPE || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
        classDataTableModel.type = classDataTableModel.type + "," + listOfTokens[tokenNo].classPart
        if(listOfTokens[tokenNo].classPart == ConstantClass.DATA_TYPE) classDataTableModel.type = classDataTableModel.type + "," + listOfTokens[tokenNo].classPart
        else {
            val tcp:TCP? = lookUpRefTable(listOfTokens[tokenNo].valuePart)
            if(tcp != null){
                classDataTableModel.type = classDataTableModel.type + "," + listOfTokens[tokenNo].classPart

            }else{
                println("UNDEFINED DATA TYPE")
                System.exit(0)
            }
        }
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.COMMA
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
            ) {
                if (moreParamsDec()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
        result = true
        return result
    } else {
        result = false
    }
    return result
}

fun moreParamsDec(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.COMMA) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.DATA_TYPE) {
            /*** yahan par sirf first se gae hain cfg m ***/
            if (paramsDec()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
        result = true
        return result
    } else {
        result = false
    }
    return result
}

fun commonDec(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS) {
        classDataTableModel.isConst = listOfTokens[tokenNo].valuePart == "cons"
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.DATA_TYPE
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        ) {
            if(listOfTokens[tokenNo].classPart == ConstantClass.DATA_TYPE) classDataTableModel.type = listOfTokens[tokenNo].classPart
            else {
                val tcp:TCP? = lookUpRefTable(listOfTokens[tokenNo].valuePart)
                if(tcp != null){
                    classDataTableModel.type = listOfTokens[tokenNo].valuePart

                }else{
                    println("UNDEFINED DATA TYPE")
                    System.exit(0)
                }
            }
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
                classDataTableModel.name = listOfTokens[tokenNo].valuePart
                tokenNo++
                if (listOfTokens[tokenNo].classPart == ConstantClass.EQUALS
                    //   || listOfTokens[tokenNo].classPart == ConstantClass.ASSIGNMENT_OP
                    || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                ) {
                    if (arrVar()) {
                        result = true
                        return result
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if ((listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.DATA_TYPE)
        || (listOfTokens[tokenNo].classPart == ConstantClass.FUN && listOfTokens[tokenNo + 1].classPart == ConstantClass.IDENTIFIER)
    ) {
        if (funDecCompulsory()) {
            result = true
            return result
        }
    } else {
        result = false
    }
    return result
}

fun arrVar(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.EQUALS
        || listOfTokens[tokenNo].classPart == ConstantClass.ASSIGNMENT_OP
    ) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
            || listOfTokens[tokenNo].classPart == ConstantClass.NULL
            || listOfTokens[tokenNo].classPart == ConstantClass.NOT
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        ) {
            if (listCfg()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.EQUALS) {
                tokenNo++
                if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                    tokenNo++
                    if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                    ) {
                        if (value()) {
                            if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                                tokenNo++
                                result = true
                                return result
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.EQUALS && listOfTokens[tokenNo + 1].classPart == ConstantClass.IDENTIFIER) {
        /*** left factoring nh kari aisi hal kardia ***/
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN) {
                tokenNo++
                if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                    || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                    || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                    || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                    || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                    || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                    || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                    || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                    || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN

                    || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                ) {
                    if (params()) {
                        if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
                            tokenNo++
                            result = true
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }

    } else {
        result = false
    }
    return result
}

fun listCfg(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
    ) {
        if (oe()) {
            result = true
            return result
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun value(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
    ) {
        if (oe()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.COMMA
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
            ) {
                if (value2()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun value2(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.COMMA) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
            || listOfTokens[tokenNo].classPart == ConstantClass.NULL
            || listOfTokens[tokenNo].classPart == ConstantClass.NOT
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        ) {
            if (value()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
        result = true
        return result
    }
    return result
}

fun mainMethodCfg(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.FUN) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.MAIN_METHOD) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN) {
                tokenNo++
                if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
                    tokenNo++
                    if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                        tokenNo++
                        if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                            || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                            || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                            || listOfTokens[tokenNo].classPart == ConstantClass.DO
                            || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                            || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                            || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                            || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                            || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                            || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                            || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                            || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                        ) {
                            if (body()) {
                                if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                                    tokenNo++
                                    if (listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                                        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                                        || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                                        || listOfTokens[tokenNo].classPart == ConstantClass.CONSTRUCTOR
                                        || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                                        || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                                        || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                                        || listOfTokens[tokenNo].classPart == ConstantClass.OPEN
                                        || listOfTokens[tokenNo].classPart == ConstantClass.OVERRIDE
                                        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                                    ) {
                                        if (normalClassBody()) {
                                            result = true
                                            return result

                                        } else {
                                            result = false
                                        }
                                    } else {
                                        result = false
                                    }
                                } else {
                                    result = false
                                }
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }

                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun body(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.IF) {
        if (ifElse()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            ) {
                if (body()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.WHEN) {
        if (whenCfg()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            ) {
                if (body()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        }


        //  } else if (listOfTokens[tokenNo].classPart == ConstantClass.LOOP) {

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.DO) {
        if (doWhileCfg()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            ) {
                if (body()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.WHILE) {
        if (whileCfg()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            ) {
                if (body()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.RETURN) {
        if (returnCfg()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            ) {
                if (body()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        }

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER || listOfTokens[tokenNo].classPart == ConstantClass.DATA_TYPE) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
                tokenNo++
                if (listOfTokens[tokenNo].classPart == ConstantClass.EQUALS
                    || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                ) {
                    if (decAndObject()) {
                        if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                            || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                            || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                            || listOfTokens[tokenNo].classPart == ConstantClass.DO
                            || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                            || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                            || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                            || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                            || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                            || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                            || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                            || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                        ) {
                            if (body()) {
                                result = true
                                return result
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }

            } else {
                result = false
            }
        } else {
            result = false
        }

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.DOT

            || listOfTokens[tokenNo].classPart == ConstantClass.MUL_DIV_MOD
            || listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS
            || listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
            || listOfTokens[tokenNo].classPart == ConstantClass.AND
            || listOfTokens[tokenNo].classPart == ConstantClass.OR
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
            || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.NOT
            || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
            || listOfTokens[tokenNo].classPart == ConstantClass.NULL
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
            || listOfTokens[tokenNo].classPart == ConstantClass.IF
            || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
            || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
            || listOfTokens[tokenNo].classPart == ConstantClass.DO
            || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
            || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
            || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
            || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
            || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
            || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
            || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
            || listOfTokens[tokenNo].classPart == ConstantClass.FUN
            || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
            || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
            || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
            || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
            || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
            || listOfTokens[tokenNo].classPart == ConstantClass.COMMA


            || listOfTokens[tokenNo].classPart == ConstantClass.DOT
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.ASSIGNMENT_OP
            || listOfTokens[tokenNo].classPart == ConstantClass.EQUALS

        ) {
            if (decTwoAndCalling()) {
                if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                    || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                    || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                    || listOfTokens[tokenNo].classPart == ConstantClass.DO
                    || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                    || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                    || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                    || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                    || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                    || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                    || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                    || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                    || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                    || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                ) {
                    if (body()) {
                        result = true
                        return result
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }

            } else {
                result = false
            }
        } else {
            result = false
        }

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
    ) {
        result = true
        return result
    } else {

        result = false
    }
    return result
}

fun ifElse(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.IF) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
            ) {
                if (oe()) {
                    if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
                        tokenNo++
                        if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                            tokenNo++
                            if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                                || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                            ) {
                                if (body2()) {
                                    if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                                        tokenNo++
                                        if (listOfTokens[tokenNo].classPart == ConstantClass.ELSE

                                            || listOfTokens[tokenNo].classPart == ConstantClass.IF
                                            || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                            || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                                            || listOfTokens[tokenNo].classPart == ConstantClass.DO
                                            || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                                            || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                            || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                                            || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                                            || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                                            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                                            || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                                            || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                                            || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                                            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                                        ) {
                                            if (elseCfg()) {
                                                result = true
                                                return result
                                            } else {
                                                result = false
                                            }
                                        } else {
                                            result = false
                                        }
                                    } else {
                                        result = false
                                    }
                                } else {
                                    result = false
                                }
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun body2(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.IF
        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
        || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
        || listOfTokens[tokenNo].classPart == ConstantClass.DO
        || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
        || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.DOT
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
    ) {
        if (body()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK

                || listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            ) {
                if (cb()) {
                    if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                        || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                        || listOfTokens[tokenNo].classPart == ConstantClass.DO
                        || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                        || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                        || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                        || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                    ) {
                        if (body()) {
                            result = true
                            return result
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }

        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun cb(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK) {
        tokenNo++
        result = true
        return result
    } else if (
        listOfTokens[tokenNo].classPart == ConstantClass.IF
        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
        || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
        || listOfTokens[tokenNo].classPart == ConstantClass.DO
        || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
        || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.DOT
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
    ) {
        result = true
        return result
    } else {
        result = false
    }
    return result
}

fun elseCfg(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.IF
        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
        || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
        || listOfTokens[tokenNo].classPart == ConstantClass.DO
        || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
        || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.DOT
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
    ) {
        result = true
        return result

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.ELSE && listOfTokens[tokenNo + 1].classPart == ConstantClass.IF) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.IF) {
            if (ifElse()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.ELSE && listOfTokens[tokenNo + 1].classPart == ConstantClass.CURL_BRAC_OPEN) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            ) {
                if (body2()) {
                    if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                        tokenNo++
                        result = true
                        return result
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun whileCfg(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.WHILE) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
            ) {
                if (oe()) {
                    if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
                        tokenNo++
                        if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                            tokenNo++
                            if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                                || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                            ) {
                                if (body2()) {
                                    if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                                        tokenNo++
                                        result = true
                                        return result
                                    } else {
                                        result = false
                                    }
                                } else {
                                    result = false
                                }
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun doWhileCfg(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.DO) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            ) {
                if (body2()) {
                    if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                        tokenNo++
                        if (listOfTokens[tokenNo].classPart == ConstantClass.WHILE) {
                            tokenNo++
                            if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN) {
                                tokenNo++
                                if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                                    || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                                    || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                                    || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                                    || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                                    || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                                    || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                                    || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                                    || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                                ) {
                                    if (oe()) {
                                        if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
                                            tokenNo++
                                            result = true
                                            return result
                                        } else {
                                            result = false
                                        }
                                    } else {
                                        result = false
                                    }
                                } else {
                                    result = false
                                }
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        }
    }
    return result
}

fun returnCfg(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.RETURN) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
            || listOfTokens[tokenNo].classPart == ConstantClass.NULL
            || listOfTokens[tokenNo].classPart == ConstantClass.NOT
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        ) {
            if (oe()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun whenCfg(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.WHEN) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
            ) {
                if (oe()) {
                    if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
                        tokenNo++
                        if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                            tokenNo++
                            if (listOfTokens[tokenNo].classPart == ConstantClass.CASE
                                || listOfTokens[tokenNo].classPart == ConstantClass.DEFAULT
                            ) {
                                if (bodyWhen()) {
                                    if (listOfTokens[tokenNo].classPart == ConstantClass.DEFAULT) {
                                        tokenNo++
                                        if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                                            tokenNo++
                                            if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                                                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                                                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                                                || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                                                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                                                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                                                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                                                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                                                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                                                || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                                                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                                                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                                            ) {
                                                if (body2()) {
                                                    if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                                                        tokenNo++
                                                        if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                                                            tokenNo++
                                                            result = true
                                                            return result
                                                        } else {
                                                            result = false
                                                        }
                                                    } else {
                                                        result = false
                                                    }
                                                } else {
                                                    result = false
                                                }
                                            } else {
                                                result = false
                                            }
                                        } else {
                                            result = false
                                        }
                                    } else {
                                        result = false
                                    }
                                } else {
                                    result = false
                                }
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun bodyWhen(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.DEFAULT) {
        result = true
        return result
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.CASE) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
            ) {
                if (oe()) {
                    if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
                        tokenNo++
                        if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                            tokenNo++
                            if (listOfTokens[tokenNo].classPart == ConstantClass.IF
                                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                                || listOfTokens[tokenNo].classPart == ConstantClass.WHILE
                                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                                || listOfTokens[tokenNo].classPart == ConstantClass.DOT
                                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                            ) {
                                if (body2()) {
                                    if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                                        tokenNo++
                                        if (listOfTokens[tokenNo].classPart == ConstantClass.CASE
                                            || listOfTokens[tokenNo].classPart == ConstantClass.DEFAULT
                                        ) {
                                            if (bodyWhen()) {
                                                result = true
                                                return result
                                            } else {
                                                result = false
                                            }
                                        } else {
                                            result = false
                                        }

                                    } else {
                                        result = false
                                    }
                                } else {
                                    result = false
                                }
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }


    return result
}

fun decAndObject(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.EQUALS) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
            || listOfTokens[tokenNo].classPart == ConstantClass.NULL
            || listOfTokens[tokenNo].classPart == ConstantClass.NOT
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        ) {
            if (listCfg()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE) {
            tokenNo++
            if (listOfTokens[tokenNo].classPart == ConstantClass.EQUALS) {
                tokenNo++
                if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_OPEN) {
                    tokenNo++
                    if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                    ) {
                        if (value()) {
                            if (listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE) {
                                tokenNo++
                                result = true
                                return result
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun decTwoAndCalling(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
        //  || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        //  || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
        //  || listOfTokens[tokenNo].classPart == ConstantClass.DOT

        || listOfTokens[tokenNo].classPart == ConstantClass.MUL_DIV_MOD
        || listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS
        || listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
        || listOfTokens[tokenNo].classPart == ConstantClass.AND
        || listOfTokens[tokenNo].classPart == ConstantClass.OR
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
        //   || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
        || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        //   || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
        || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
        || listOfTokens[tokenNo].classPart == ConstantClass.IF
        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
        || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
        || listOfTokens[tokenNo].classPart == ConstantClass.DO
        || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || listOfTokens[tokenNo].classPart == ConstantClass.FUN
        || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.COMMA

    ) {
        if (id1()) {
            result = true
            return result
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.DOT
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.ASSIGNMENT_OP
        || listOfTokens[tokenNo].classPart == ConstantClass.EQUALS
    ) {
        if (calling3()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.EQUALS
                || listOfTokens[tokenNo].classPart == ConstantClass.ASSIGNMENT_OP
            ) {
                tokenNo++
                if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                    || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                    || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                    || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                    || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                    || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                    || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                    || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                    || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                ) {
                    if (listCfg()) {
                        result = true
                        return result
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun calling3(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.ASSIGNMENT_OP
        || listOfTokens[tokenNo].classPart == ConstantClass.EQUALS
    ) {
        result = true
        return result
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.DOT) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
            if (calling2()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
            || listOfTokens[tokenNo].classPart == ConstantClass.NULL
            || listOfTokens[tokenNo].classPart == ConstantClass.NOT
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN

            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
        ) {
            if (params()) {
                if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
                    tokenNo++
                    if (listOfTokens[tokenNo].classPart == ConstantClass.DOT) {
                        tokenNo++
                        if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
                            if (calling2()) {
                                result = true
                                return result
                            } else {
                                result = false
                            }
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
            || listOfTokens[tokenNo].classPart == ConstantClass.NULL
            || listOfTokens[tokenNo].classPart == ConstantClass.NOT
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        ) {

            if (oe()) {
                if (listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE) {
                    tokenNo++
                    if (listOfTokens[tokenNo].classPart == ConstantClass.DOT
                        || listOfTokens[tokenNo].classPart == ConstantClass.ASSIGNMENT_OP
                        || listOfTokens[tokenNo].classPart == ConstantClass.EQUALS
                    ) {
                        if (choice2()) {
                            result = true
                            return result
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }


        } else {
            result = false
        }

    } else {
        result = false
    }
    return result
}

fun calling2(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.DOT
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.ASSIGNMENT_OP
            || listOfTokens[tokenNo].classPart == ConstantClass.EQUALS
        ) {
            if (calling3()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun choice2(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.DOT) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
            if (calling2()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.ASSIGNMENT_OP
        || listOfTokens[tokenNo].classPart == ConstantClass.EQUALS
    ) {
        result = true
        return result
    }
    return result
}

fun oe(): Boolean {

    if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
    ) {
        if (ae()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.OR
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
                || listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.COMMA
            ) {
                if (oeDash()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

// <AE>
fun ae(): Boolean {
    if (
        listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
    ) {
        if (re()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.AND
                || listOfTokens[tokenNo].classPart == ConstantClass.OR
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
                || listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.COMMA

            ) {
                if (aeDash()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }


        } else {
            result = false
        }
    } else {
        result = false
    }


    return result
}

// <OE'>
fun oeDash(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.OR) {
        tokenNo++
        if (ae()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.OR
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
                || listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.COMMA

            ) {
                oeDash()
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = true
        return result
    }
    return result
}

// <RE>
fun re(): Boolean {
    if (
        listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
    ) {
        if (e()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
                || listOfTokens[tokenNo].classPart == ConstantClass.AND
                || listOfTokens[tokenNo].classPart == ConstantClass.OR
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
                || listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.COMMA


            ) {
                if (reDash()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

// <AE'>
fun aeDash(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.AND) {
        tokenNo++
        if (re()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.AND
                || listOfTokens[tokenNo].classPart == ConstantClass.OR
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
                || listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.COMMA

            ) {
                aeDash()
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = true
        return result
    }
    return result
}

// <E>
fun e(): Boolean {
    if (
        listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
    ) {
        if (t()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS
                || listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
                || listOfTokens[tokenNo].classPart == ConstantClass.AND
                || listOfTokens[tokenNo].classPart == ConstantClass.OR
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
                || listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.COMMA

            ) {
                if (eDash()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }

        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}


// <RE'>
fun reDash(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP) {
        tokenNo++
        if (e()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
                || listOfTokens[tokenNo].classPart == ConstantClass.AND
                || listOfTokens[tokenNo].classPart == ConstantClass.OR
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
                || listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.COMMA


            ) {
                reDash()

            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = true
        return result
    }
    return result
}


// <T>
fun t(): Boolean {
    if (
        listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
    ) {
        if (f()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.MUL_DIV_MOD
                || listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS
                || listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
                || listOfTokens[tokenNo].classPart == ConstantClass.AND
                || listOfTokens[tokenNo].classPart == ConstantClass.OR
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
                || listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.COMMA

            ) {

                if (tDash()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}


// <E'>
fun eDash(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS) {

        tokenNo++
        if (t()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS
                || listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
                || listOfTokens[tokenNo].classPart == ConstantClass.AND
                || listOfTokens[tokenNo].classPart == ConstantClass.OR
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
                || listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.COMMA

            ) {
                eDash()
            } else {
                result = false
            }

        } else {
            result = false
        }
    } else {
        result = true
        return result
    }
    return result
}

// <F>
fun f(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
    ) {
        tokenNo++
        result = true
        return result
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.NOT) {
        tokenNo++
        if (f()) {
            result = true
            return result
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
            || listOfTokens[tokenNo].classPart == ConstantClass.NULL
            || listOfTokens[tokenNo].classPart == ConstantClass.NOT
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        ) {
            if (oe()) {
                if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
                    tokenNo++
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }

        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC) {
        if (incDecT()) {
            result = true
            return result
        } else {
            result = false
        }

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {

        if (calling()) {
            result = true
            return result
        } else {
            result = false
        }

    } else {
        result = false
    }

    return result
}


// <T'>
fun tDash(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.MUL_DIV_MOD) {
        tokenNo++
        if (f()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.MUL_DIV_MOD
                || listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS
                || listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
                || listOfTokens[tokenNo].classPart == ConstantClass.AND
                || listOfTokens[tokenNo].classPart == ConstantClass.OR
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
                || listOfTokens[tokenNo].classPart == ConstantClass.IF
                || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                || listOfTokens[tokenNo].classPart == ConstantClass.DO
                || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                || listOfTokens[tokenNo].classPart == ConstantClass.COMMA

            ) {
                tDash()
            } else {
                result = false
            }

        }
    } else {

        result = true
        return result
    }
    return result

}

fun incDecT(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
            if (incD()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun incD(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.MUL_DIV_MOD
            || listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS
            || listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
            || listOfTokens[tokenNo].classPart == ConstantClass.AND
            || listOfTokens[tokenNo].classPart == ConstantClass.OR
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
            || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.NOT
            || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
            || listOfTokens[tokenNo].classPart == ConstantClass.NULL
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
            || listOfTokens[tokenNo].classPart == ConstantClass.IF
            || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
            || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
            || listOfTokens[tokenNo].classPart == ConstantClass.DO
            || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
            || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
            || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
            || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
            || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
            || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
            || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
            || listOfTokens[tokenNo].classPart == ConstantClass.FUN
            || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
            || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
            || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
            || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
            || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
            || listOfTokens[tokenNo].classPart == ConstantClass.COMMA

        ) {
            if (newChoice()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}

fun newChoice(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN) {
        tokenNo++
        if (oe()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE) {
                tokenNo++
                result = true
                return true
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.MUL_DIV_MOD
        || listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS
        || listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
        || listOfTokens[tokenNo].classPart == ConstantClass.AND
        || listOfTokens[tokenNo].classPart == ConstantClass.OR
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
        || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
        || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
        || listOfTokens[tokenNo].classPart == ConstantClass.IF
        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
        || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
        || listOfTokens[tokenNo].classPart == ConstantClass.DO
        || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || listOfTokens[tokenNo].classPart == ConstantClass.FUN
        || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.COMMA
    ) {

        result = true
        return result
    } else {
        result = false
    }
    return result
}

fun calling(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.DOT

            || listOfTokens[tokenNo].classPart == ConstantClass.MUL_DIV_MOD
            || listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS
            || listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
            || listOfTokens[tokenNo].classPart == ConstantClass.AND
            || listOfTokens[tokenNo].classPart == ConstantClass.OR
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
            || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.NOT
            || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
            || listOfTokens[tokenNo].classPart == ConstantClass.NULL
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
            || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
            || listOfTokens[tokenNo].classPart == ConstantClass.IF
            || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
            || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
            || listOfTokens[tokenNo].classPart == ConstantClass.DO
            || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
            || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
            || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
            || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
            || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
            || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
            || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
            || listOfTokens[tokenNo].classPart == ConstantClass.FUN
            || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
            || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
            || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
            || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
            || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
            || listOfTokens[tokenNo].classPart == ConstantClass.COMMA

        ) {
            if (id1()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else {
        result = false
    }
    return result
}


fun id1(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC) {
        tokenNo++
        result = true
        return result
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
            || listOfTokens[tokenNo].classPart == ConstantClass.NULL
            || listOfTokens[tokenNo].classPart == ConstantClass.NOT
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        ) {
            if (oe()) {
                if (listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE) {
                    tokenNo++
                    if (listOfTokens[tokenNo].classPart == ConstantClass.DOT

                        || listOfTokens[tokenNo].classPart == ConstantClass.MUL_DIV_MOD
                        || listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS
                        || listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
                        || listOfTokens[tokenNo].classPart == ConstantClass.AND
                        || listOfTokens[tokenNo].classPart == ConstantClass.OR
                        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                        || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                        || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
                        || listOfTokens[tokenNo].classPart == ConstantClass.IF
                        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                        || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                        || listOfTokens[tokenNo].classPart == ConstantClass.DO
                        || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                        || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
                        || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                        || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                        || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                        || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                        || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                        || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                        || listOfTokens[tokenNo].classPart == ConstantClass.COMMA
                    ) {
                        if (choice4()) {
                            result = true
                            return result
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }

    } else if (listOfTokens[tokenNo].classPart == ConstantClass.DOT) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
            if (calling()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
            || listOfTokens[tokenNo].classPart == ConstantClass.NULL
            || listOfTokens[tokenNo].classPart == ConstantClass.NOT
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN

            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
        ) {
            if (params()) {
                if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
                    tokenNo++
                    if (listOfTokens[tokenNo].classPart == ConstantClass.DOT

                        || listOfTokens[tokenNo].classPart == ConstantClass.MUL_DIV_MOD
                        || listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS
                        || listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
                        || listOfTokens[tokenNo].classPart == ConstantClass.AND
                        || listOfTokens[tokenNo].classPart == ConstantClass.OR
                        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
                        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
                        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
                        || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
                        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
                        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
                        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
                        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
                        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
                        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
                        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
                        || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
                        || listOfTokens[tokenNo].classPart == ConstantClass.IF
                        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
                        || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
                        || listOfTokens[tokenNo].classPart == ConstantClass.DO
                        || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
                        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                        || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
                        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
                        || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
                        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
                        || listOfTokens[tokenNo].classPart == ConstantClass.FUN
                        || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
                        || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
                        || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
                        || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
                        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
                        || listOfTokens[tokenNo].classPart == ConstantClass.COMMA
                    ) {
                        if (choice4()) {
                            result = true
                            return result
                        } else {
                            result = false
                        }
                    } else {
                        result = false
                    }
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.MUL_DIV_MOD
        || listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS
        || listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
        || listOfTokens[tokenNo].classPart == ConstantClass.AND
        || listOfTokens[tokenNo].classPart == ConstantClass.OR
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
        || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
        || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
        || listOfTokens[tokenNo].classPart == ConstantClass.IF
        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
        || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
        || listOfTokens[tokenNo].classPart == ConstantClass.DO
        || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || listOfTokens[tokenNo].classPart == ConstantClass.FUN
        || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.COMMA
    ) {
        result = true
        return result
    } else {
        result = false
    }
    return result
}

fun params(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
    ) {
        if (oe()) {
            if (listOfTokens[tokenNo].classPart == ConstantClass.COMMA
                || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
            ) {
                if (moreParams()) {
                    result = true
                    return result
                } else {
                    result = false
                }
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
        result = true
        return result
    } else {
        result = false
    }
    return result
}

fun moreParams(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.COMMA) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
            || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
            || listOfTokens[tokenNo].classPart == ConstantClass.NULL
            || listOfTokens[tokenNo].classPart == ConstantClass.NOT
            || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
            || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN

            || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
        ) {
            if (params()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE) {
        result = true
        return result
    } else {
        result = false
    }
    return result
}

fun choice4(): Boolean {
    if (listOfTokens[tokenNo].classPart == ConstantClass.DOT) {
        tokenNo++
        if (listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER) {
            if (calling()) {
                result = true
                return result
            } else {
                result = false
            }
        } else {
            result = false
        }
    } else if (
        listOfTokens[tokenNo].classPart == ConstantClass.MUL_DIV_MOD
        || listOfTokens[tokenNo].classPart == ConstantClass.PLUS_MINUS
        || listOfTokens[tokenNo].classPart == ConstantClass.COMPARISON_OP
        || listOfTokens[tokenNo].classPart == ConstantClass.AND
        || listOfTokens[tokenNo].classPart == ConstantClass.OR
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.NOT
        || listOfTokens[tokenNo].classPart == ConstantClass.INT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.FLOAT_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.STRING_CONSTANT
        || listOfTokens[tokenNo].classPart == ConstantClass.TRUE_FALSE
        || listOfTokens[tokenNo].classPart == ConstantClass.NULL
        || listOfTokens[tokenNo].classPart == ConstantClass.IDENTIFIER
        || listOfTokens[tokenNo].classPart == ConstantClass.ROUND_BRAC_OPEN
        || listOfTokens[tokenNo].classPart == ConstantClass.INC_DEC
        || listOfTokens[tokenNo].classPart == ConstantClass.SEMI_COLON
        || listOfTokens[tokenNo].classPart == ConstantClass.IF
        || listOfTokens[tokenNo].classPart == ConstantClass.WHEN
        || listOfTokens[tokenNo].classPart == ConstantClass.LOOP
        || listOfTokens[tokenNo].classPart == ConstantClass.DO
        || listOfTokens[tokenNo].classPart == ConstantClass.RETURN
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || listOfTokens[tokenNo].classPart == ConstantClass.CONTINUE_BREAK
        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.SQ_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.STATIC
        || listOfTokens[tokenNo].classPart == ConstantClass.VAR_CONS
        || listOfTokens[tokenNo].classPart == ConstantClass.FUN
        || listOfTokens[tokenNo].classPart == ConstantClass.PUBLIC_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PRIVATE_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.PROTECTED_CLASS
        || listOfTokens[tokenNo].classPart == ConstantClass.ABSTRACT
        || listOfTokens[tokenNo].classPart == ConstantClass.CURL_BRAC_CLOSE
        || listOfTokens[tokenNo].classPart == ConstantClass.COMMA
    ) {
        result = true
        return result
    } else {
        result = false
    }
    return result
}

