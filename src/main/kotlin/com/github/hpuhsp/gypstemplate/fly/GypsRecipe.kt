package com.github.hpuhsp.gypstemplate.fly


import com.android.tools.idea.wizard.template.Language
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.github.hpuhsp.gypstemplate.fly.config.initEmptyLayout
import com.github.hpuhsp.gypstemplate.fly.config.initLayout
import com.github.hpuhsp.gypstemplate.fly.config.modifyManifest
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toLowerCaseAsciiOnly
import java.io.File

/**
 * @Description:
 * @Author:   Hsp
 * @Email:    1101121039@qq.com
 * @CreateTime:     2023/8/19 16:32
 * @UpdateRemark:   更新说明：
 */
fun RecipeExecutor.gypsRecipe(
    provider: GypsPluginGeneratorProvider,
    data: ModuleTemplateData,
    createAct: Boolean
) {
    
    val (projectData, srcOut, resOut) = data
    val moduleName = data.rootDir.name.toLowerCaseAsciiOnly()
    val modulePackageName = projectData.applicationPackage ?: "" // Module 包名
    
    println("--------moduleName------------------->${moduleName}")
    println("--------modulePackageName------------------->${modulePackageName}")
    
    
    if (provider.needActivity.value) {
        mergeXml(
            modifyManifest(provider, modulePackageName),
            File(data.manifestDir, "AndroidManifest.xml")
        )
    }
    
    if (provider.needActivity.value && provider.generateActivityLayout.value) {
        save(
            initLayout(provider),
            File(data.resDir, "layout/${provider.activityLayoutName.value}.xml")
        )
    }
    
    if (provider.needFragment.value && provider.generateFragmentLayout.value) {
        save(
            initEmptyLayout(provider),
            File(data.resDir, "layout/${provider.fragmentLayoutName.value}.xml")
        )
    }
    /**
     * 语言选择 Java 或者 Kotlin
     */
    val languageSuffix = if (data.projectTemplateData.language == Language.Java) "java" else "kt"
    val isKt = data.projectTemplateData.language == Language.Kotlin
    if (provider.needActivity.value) {
        val activityFile = File(
            data.rootDir,
            "${getSeparatorPackageName(provider.activityGeneratedLocation.value)}/${provider.activityName.value}Activity.$languageSuffix"
        )
        save(armsActivity(isKt, provider), activityFile)
        open(activityFile)
    }
    if (provider.needFragment.value) {
        val fragmentFile = File(
            data.rootDir,
            "${getSeparatorPackageName(provider.fragmentGeneratedLocation.value)}/${provider.fragmentName.value}Fragment.$languageSuffix"
        )
        save(armsFragment(isKt, provider), fragmentFile)
        open(fragmentFile)
    }
    
    
}

fun getSeparatorPackageName(oVar: String): String {
    return "src/main/java/${oVar.replace('.', '/')}"
}