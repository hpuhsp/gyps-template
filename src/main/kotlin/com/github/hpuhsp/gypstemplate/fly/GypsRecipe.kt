package com.github.hpuhsp.gypstemplate.fly


import com.android.tools.idea.wizard.template.Language
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.github.hpuhsp.gypstemplate.fly.config.initEmptyLayout
import com.github.hpuhsp.gypstemplate.fly.config.initLayout
import com.github.hpuhsp.gypstemplate.fly.config.modifyManifest
import com.github.hpuhsp.gypstemplate.fly.temp.gypsActivity
import com.github.hpuhsp.gypstemplate.fly.temp.gypsFragment
import com.github.hpuhsp.gypstemplate.fly.temp.gypsRepository
import com.github.hpuhsp.gypstemplate.fly.temp.gypsViewModel
import com.github.hpuhsp.gypstemplate.fly.temp.gypsViewModelNoRepo
import com.github.hpuhsp.gypstemplate.fly.utils.firstUppercase
import com.github.hpuhsp.gypstemplate.fly.utils.getResourcePrefixFromBuildFile
import com.github.hpuhsp.gypstemplate.fly.utils.getSeparatorPackageName
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toLowerCaseAsciiOnly
import java.io.File

/**
 * @Description: 模板文件的创建和保存
 * @Author:   Hsp
 * @Email:    1101121039@qq.com
 * @CreateTime:     2023/8/19 16:32
 * @UpdateRemark:   更新说明：
 */
fun RecipeExecutor.gypsActivityRecipe(
    provider: GypsPluginGeneratorProvider,
    data: ModuleTemplateData
) {
    // 模块包名，例如com.github.user , 对应AndroidManifest.xml中配置，获取方式为：val modulePackageName = projectData.applicationPackage
    val (projectData, srcOut, resOut, manifestDir) = data
    val modulePath = data.rootDir.absolutePath // Module 的文件目录的绝对路径
    val moduleName = data.rootDir.name.toLowerCaseAsciiOnly() // Module 名称
    val modulePackageName = projectData.applicationPackage ?: "" // Module 包名
    
    /**
     * 资源前缀
     */
    var resourcePrefix = ""
    
    /**
     * 类文件等命名前缀
     */
    var namePrefix = ""
    
    if (provider.needResourcePrefix.value) {
        resourcePrefix =
            getResourcePrefixFromBuildFile("${modulePath.replace("\\", "/")}/build.gradle") ?: ""
        namePrefix = resourcePrefix.replace("_", "").replaceFirstChar { it.uppercase() }
    }
    
    /**
     * 语言选择 Java 或者 Kotlin
     */
    val languageSuffix = if (data.projectTemplateData.language == Language.Java) "java" else "kt"
    val isKt = data.projectTemplateData.language == Language.Kotlin
    // 注意生成文件的顺序，建议考虑引用关系
    val activityName = "$namePrefix${firstUppercase(provider.activityName.value)}"
    
    if (provider.needActivity.value) {
        mergeXml(
            modifyManifest(provider, modulePackageName, activityName),
            File(data.manifestDir, "AndroidManifest.xml")
        )
    }
    
    if (provider.needActivity.value && provider.generateActivityLayout.value) {
        save(
            initLayout(provider),
            File(
                data.resDir,
                "layout/${resourcePrefix}${provider.activityLayoutName.value}.xml"
            )
        )
    }
    
    if (provider.needRepository.value) {
        val repositoryFile = File(
            data.rootDir,
            "${getSeparatorPackageName(provider.targetPackageName.value)}/${activityName}Repository.$languageSuffix"
        )
        save(gypsRepository(provider, activityName), repositoryFile)
        open(repositoryFile)
    }
    
    if (provider.needViewModel.value) {
        val viewModelFile = File(
            data.rootDir,
            "${getSeparatorPackageName(provider.targetPackageName.value)}/viewmodel/${activityName}ViewModel.$languageSuffix"
        )
        if (provider.needRepository.value) {
            save(gypsViewModel(provider, activityName), viewModelFile)
        } else {
            save(gypsViewModelNoRepo(provider, activityName), viewModelFile)
        }
        open(viewModelFile)
    }
    
    if (provider.needActivity.value) {
        val activityFile = File(
            data.rootDir,
            "${getSeparatorPackageName(provider.activityGeneratedLocation.value)}/${activityName}Activity.$languageSuffix"
        )
        save(gypsActivity(provider, modulePackageName, activityName, namePrefix), activityFile)
        open(activityFile)
    }
    
}

/**
 * Gyps一键生成Fragment模板文件
 */
fun RecipeExecutor.gypsFragmentRecipe(
    provider: GypsPluginGeneratorProvider,
    data: ModuleTemplateData
) {
    // 模块包名，例如com.github.user , 对应AndroidManifest.xml中配置，获取方式为：val modulePackageName = projectData.applicationPackage
    val (projectData, srcOut, resOut, manifestDir) = data
    val modulePath = data.rootDir.absolutePath // Module 的文件目录的绝对路径
    val moduleName = data.rootDir.name.toLowerCaseAsciiOnly() // Module 名称
    val modulePackageName = projectData.applicationPackage ?: "" // Module 包名
    
    /**
     * 资源前缀
     */
    var resourcePrefix = ""
    
    /**
     * 类文件等命名前缀
     */
    var namePrefix = ""
    
    if (provider.needResourcePrefix.value) {
        resourcePrefix =
            getResourcePrefixFromBuildFile("${modulePath.replace("\\", "/")}/build.gradle") ?: ""
        namePrefix = resourcePrefix.replace("_", "").replaceFirstChar { it.uppercase() }
    }
    
    /**
     * 语言选择 Java 或者 Kotlin
     */
    val languageSuffix = if (data.projectTemplateData.language == Language.Java) "java" else "kt"
    val isKt = data.projectTemplateData.language == Language.Kotlin
    val fragmentName = "$namePrefix${firstUppercase(provider.fragmentName.value)}"
    
    if (provider.needFragment.value && provider.generateFragmentLayout.value) {
        save(
            initEmptyLayout(provider),
            File(
                data.resDir,
                "layout/${resourcePrefix}${provider.fragmentLayoutName.value}.xml"
            )
        )
    }
    
    if (provider.needRepository.value) {
        val repositoryFile = File(
            data.rootDir,
            "${getSeparatorPackageName(provider.targetPackageName.value)}/${fragmentName}Repository.$languageSuffix"
        )
        save(gypsRepository(provider, fragmentName), repositoryFile)
        open(repositoryFile)
    }
    
    if (provider.needViewModel.value) {
        val viewModelFile = File(
            data.rootDir,
            "${getSeparatorPackageName(provider.targetPackageName.value)}/viewmodel/${fragmentName}ViewModel.$languageSuffix"
        )
        if (provider.needRepository.value) {
            save(gypsViewModel(provider, fragmentName), viewModelFile)
        } else {
            save(gypsViewModelNoRepo(provider, fragmentName), viewModelFile)
        }
        open(viewModelFile)
    }
    
    if (provider.needFragment.value) {
        val fragmentFile = File(
            data.rootDir,
            "${getSeparatorPackageName(provider.fragmentGeneratedLocation.value)}/${fragmentName}Fragment.$languageSuffix"
        )
        save(gypsFragment(provider, modulePackageName, fragmentName, namePrefix), fragmentFile)
        open(fragmentFile)
    }
}

