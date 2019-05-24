package semanticPackage

import classTableList
import referenceTable

/** semantic part functions **/
fun lookUpRefTable(name: String): TCP? {
    for (element in referenceTable) {
        if (element.name == name) {
            return TCP(element.type, element.category, element.parent)
        }
    }
    return null

}

fun insertRefTable(referenceTableModel: ReferenceTableModel): Boolean {
    var count = 0
    for (element in referenceTable) {
        if (element.name == referenceTableModel.name) {
            count++
        }
    }

    if (count == 1) {
        return false
    }
    return true
}

fun insertClassTable(classDataTableModel: ClassDataTableModel): Boolean {
    var count = 0
    for (element in classTableList) {
        if (element.name == classDataTableModel.name) {
            count++
        }
    }

    if (count == 1) {
        return false
    }
    return true
}