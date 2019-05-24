@file:Suppress("NAME_SHADOWING")

import lexicalPackage.*


fun main() {

    /*** lexical methods ***/
    breakWords()
    writeInFile()

    /*** syntax method ***/
    enter()

    /*** semantic method to print out tables ***/
    for(tables in referenceTable){
        println("${tables.name} , ${tables.category}, ${tables.parent}, ${tables.type}, ${tables.reference}")
    }

    println()
    println()
    println()

    for(tables in classTableList){
        println("${tables.name} , ${tables.am}, ${tables.isConst}, ${tables.tm}, ${tables.type}")
    }
}

