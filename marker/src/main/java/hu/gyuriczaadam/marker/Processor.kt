package hu.gyuriczaadam.marker

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

@SupportedSourceVersion(SourceVersion.RELEASE_8)
class Processor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes() = mutableSetOf(marker::class.java.canonicalName)
    val tags = mutableListOf(
        "Homework.Button.Close",
        "Homework.Button1.Close2",
        "Homework.Button2.Close3",
        "Homework.Button.Close4",
        "Homework.Button.Close5",
        "Book.Button.Open",
        "Lesson.Button",
        "Lesson.Button.Open",
        "Homework.Window",
        "Work",
        "Homeork.Button.Close",
        "Hoework.Button1.Close2",
        "omework.Button2.Close3",
        "Homewok.Button.Close4",
        "MarkOne.ForCsabika.EveryDay",
        "Homework.Button.Close",
        "Hofgmework.Buttosdfn1.Close2",
        "Homefghgwork.Buttddfon2.Close3",
        "Homsdfework.Buttodssn.Close4",
        "Homesdfwork.Buttoeen.Close5",
        "Homyvework.Button1.Close2",
        "Homework.Button2.Close3",
        "Homewyxcork.Button.Close4",
        "Homework.Buttyxcon.Close5",
        "Homewghdork.Buttrron1.Close2",
        "Homework.Button2.Close3",
        "Hoffgmework.Buttohghn.Close4",
        "Homewcxvork.Butybsdfghton.Close5",
    )
    override fun process(
        annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment
    ): Boolean {
        val kaptKotlinGeneratedDir =
            processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: return false
        val testTags = tags
        var argumentName =""
        roundEnv.getElementsAnnotatedWith(marker::class.java).forEach {
           argumentName = it.getAnnotation(marker::class.java).packageName
        }
        val myClass = ClassName(argumentName, "TestTags")
        val classBuilder = TypeSpec.classBuilder(myClass).addModifiers(KModifier.SEALED)
        val tagBuilderRepostiory:TestTagGeneratorReporistory = TestTagGeneratorRepositoryImpl()
        tagBuilderRepostiory.testTagFileBuilder(
            argumentName,
            "TestTags",
            classBuilder,
            testTags,
            kaptKotlinGeneratedDir
        )
        return true
    }
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
    private fun generateProvidesFactories(roundEnv: RoundEnvironment) {





    }

}