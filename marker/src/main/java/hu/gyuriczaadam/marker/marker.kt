package hu.gyuriczaadam.marker


@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.FILE,
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION
)
annotation class marker(
    val packageName:String
)
