package hu.gyuriczaadam.marker

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File

class TestTagGeneratorRepositoryImpl : TestTagGeneratorReporistory {

    override fun toStringMetodGenerator(): FunSpec {
        return FunSpec
            .builder("toString")
            .returns(String::class)
            .addModifiers(KModifier.OVERRIDE)
            .addStatement("return this::class.simpleName!!")
            .build()
    }

    override fun testTagListGenerator(rawTagList: List<String>): List<TypeSpec.Builder> {
        val rootTagList = mutableListOf<String>()
        val childTagList = mutableListOf<String>()
        val innerTagList = mutableListOf<String>()
        val testTagList = mutableListOf<TypeSpec.Builder>()

        rawTagList.forEach { element ->
            testTagItemGeneratorInitailizer(
                element, rootTagList, childTagList, innerTagList, testTagList, rawTagList
            )
        }
        return testTagList
    }

    override fun testTagFileBuilder(
        packageName: String,
        fileName: String,
        className: TypeSpec.Builder,
        rawTestTagList: List<String>,
        kotlinGeneratedDir: String
    ) {
        val testTagFile =
            FileSpec
                .builder(
                    "hu.gyuriczaadam.annotationprocessplaygorund.tags",
                    "TestTags"
                )
                .apply {
                    addType(
                        className.apply {
                            addFunction(toStringMetodGenerator())
                            testTagListGenerator(rawTestTagList).forEach {
                                addType(
                                    it.build()
                                )
                            }
                        }.build()
                    )
                }.build()
        testTagFile.writeTo(File(kotlinGeneratedDir))
    }

    override fun testTagItemGeneratorInitailizer(
        element: String,
        rootTagList: MutableList<String>,
        childTagList: MutableList<String>,
        innerTagList: MutableList<String>,
        testTagList: MutableList<TypeSpec.Builder>,
        rawTagList: List<String>
    ) {
        val tempRootTag = splitRawTagToSubTagList(element)[0]

        if (splitRawTagToSubTagList(element)[0].equals(tempRootTag) && !rootTagList.contains(
                tempRootTag
            )
        ) {
            rootTagList.add(tempRootTag)
            childTagList.clear()
            innerTagList.clear()
            testTagList.add(
                testTagItemGenerator(
                    tempRootTag, rawTagList, childTagList, innerTagList
                )
            )
        }
    }

    override fun testTagItemGenerator(
        tempRootTag: String,
        rawTagList: List<String>,
        childTagList: MutableList<String>,
        innerTagList: MutableList<String>
    ): TypeSpec.Builder {
        return TypeSpec.objectBuilder(tempRootTag).apply {
            superclass(ClassName("", "TestTags"))
            rawTagList.forEach { rawTagElement ->
                testTagObjectGenerator(
                    rawTagElement, tempRootTag, childTagList, this, rawTagList, innerTagList
                )
            }
        }
    }

    override fun testTagObjectGenerator(
        rawTagElement: String,
        tempRootTag: String,
        childTagList: MutableList<String>,
        typeSpec: TypeSpec.Builder,
        rawTagList: List<String>,
        innerTagList: MutableList<String>
    ) {
        if (splitRawTagToSubTagList(rawTagElement).size > 1) {
            val tempSubTag = splitRawTagToSubTagList(rawTagElement)[1]
            if (splitRawTagToSubTagList(rawTagElement)[0].equals(tempRootTag) && !childTagList.contains(
                    tempSubTag
                )
            ) {
                generateTestTagsObjects(
                    tempSubTag,
                    childTagList,
                    rawTagElement,
                    typeSpec,
                    rawTagList,
                    tempRootTag,
                    innerTagList
                )
            }
        }
    }

    override fun singleSubTagObjectPropertyGenerator(
        typeSpec: TypeSpec.Builder, rawTagElement: String
    ) {
        typeSpec.addProperty(
            PropertySpec.builder(
                splitRawTagToSubTagList(rawTagElement)[1], String::class
            ).initializer(
                "%S", "${splitRawTagToSubTagList(rawTagElement)[0]}." + splitRawTagToSubTagList(
                    rawTagElement
                )[1]
            ).build()
        )
    }

    override fun generateTestTagsObjects(
        tempSubTag: String,
        childTagList: MutableList<String>,
        rawTagElement: String,
        typeSpec: TypeSpec.Builder,
        rawTagList: List<String>,
        tempRootTag: String,
        innerTagList: MutableList<String>
    ) {
        childTagList.add(tempSubTag)
        if (splitRawTagToSubTagList(rawTagElement).size > 2) {
            multipleSubObjectTestTagGenerator(
                typeSpec, rawTagElement, rawTagList, tempRootTag, tempSubTag, innerTagList
            )
        } else {
            singleSubTagObjectPropertyGenerator(typeSpec, rawTagElement)
        }
    }

    override fun multipleSubObjectTestTagGenerator(
        typeSpec: TypeSpec.Builder,
        rawTagElement: String,
        rawTagList: List<String>,
        tempRootTag: String,
        tempSubTag: String,
        innerTagList: MutableList<String>
    ) {
        typeSpec.addType(
            TypeSpec.objectBuilder(
                splitRawTagToSubTagList(rawTagElement)[1]
            ).apply {
                superclass(ClassName("", "TestTags"))
                rawTagList.forEach { rawTagElement ->
                    if (splitRawTagToSubTagList(rawTagElement).size > 2) {
                        var tempRootTag3 = splitRawTagToSubTagList(rawTagElement)[2]
                        if (splitRawTagToSubTagList(rawTagElement)[0].equals(tempRootTag)
                            && splitRawTagToSubTagList(rawTagElement)[1]
                                .equals(tempSubTag)
                            &&
                            !innerTagList.contains(tempRootTag3)
                        ) {
                            innerTagList.add(tempRootTag3)
                            addProperty(
                                PropertySpec.builder(
                                    splitRawTagToSubTagList(
                                        rawTagElement
                                    )[2], String::class
                                ).initializer(
                                    "%S", "${
                                        splitRawTagToSubTagList(
                                            rawTagElement
                                        )[0]
                                    }.${
                                        splitRawTagToSubTagList(
                                            rawTagElement
                                        )[1]
                                    }.${
                                        splitRawTagToSubTagList(
                                            rawTagElement
                                        )[2]
                                    }"
                                ).build()
                            )
                        }
                    }
                }
            }.build()
        ).build()
    }

    override fun splitRawTagToSubTagList(rawTag: String): List<String> {
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