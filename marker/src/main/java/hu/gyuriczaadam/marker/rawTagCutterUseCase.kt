package hu.gyuriczaadam.marker

class rawTagCutterUseCase(rawTag: String) {
    operator fun invoke(rawTag:String): List<String>{
        val splitChar = "."
        val tagList = mutableListOf<String>()
        if (!rawTag.contains(".")) {
            tagList.add(rawTag)
        } else {
            rawTag.split(splitChar).forEach {
                tagList.add(it)
            }
        }
        return tagList
    }
}