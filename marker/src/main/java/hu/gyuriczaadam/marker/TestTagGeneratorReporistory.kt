package hu.gyuriczaadam.marker

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec

interface TestTagGeneratorReporistory {

    fun toStringMetodGenerator(): FunSpec

    fun testTagListGenerator(rawTagList: List<String>): List<TypeSpec.Builder>

    fun testTagFileBuilder(
        packageName:String,
        fileName:String,
        className: TypeSpec.Builder,
        rawTagList: List<String>,
        kotlinGeneratedDir:String
    )

    fun testTagItemGeneratorInitailizer(
        element: String,
        rootTagList: MutableList<String>,
        childTagList: MutableList<String>,
        innerTagList: MutableList<String>,
        testTagList: MutableList<TypeSpec.Builder>,
        rawTagList: List<String>
    )

    fun testTagItemGenerator(
        tempRootTag: String,
        rawTagList: List<String>,
        childTagList: MutableList<String>,
        innerTagList: MutableList<String>
    ): TypeSpec.Builder

    fun testTagObjectGenerator(
        rawTagElement: String,
        tempRootTag: String,
        childTagList: MutableList<String>,
        typeSpec: TypeSpec.Builder,
        rawTagList: List<String>,
        innerTagList: MutableList<String>
    )

    fun singleSubTagObjectPropertyGenerator(
        typeSpec: TypeSpec.Builder,
        rawTagElement: String
    )

    fun generateTestTagsObjects(
        tempSubTag: String,
        childTagList: MutableList<String>,
        rawTagElement: String,
        typeSpec: TypeSpec.Builder,
        rawTagList: List<String>,
        tempRootTag: String,
        innerTagList: MutableList<String>
    )

    fun multipleSubObjectTestTagGenerator(
        typeSpec: TypeSpec.Builder,
        rawTagElement: String,
        rawTagList: List<String>,
        tempRootTag: String,
        tempSubTag: String,
        innerTagList: MutableList<String>
    )

    fun splitRawTagToSubTagList(rawTag: String): List<String>
}