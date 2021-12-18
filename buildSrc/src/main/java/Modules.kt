object Modules {

    const val app = ":app"
    const val androidtools = ":androidtools"
    const val baselifecycle = ":baselifecycle"
    const val buttons = ":buttons"
    const val core = ":core"
    const val sqlitetools = ":sqlitetools"
    const val textfield = ":textfield"
    const val typography = ":typography"
    const val widgets = ":widgets"

    val modules = listOf(
        app,
        androidtools,
        baselifecycle,
        buttons,
        core,
        sqlitetools,
        textfield,
        typography,
        widgets
    )
}

fun String.clearModule() = this.replace(":", "")