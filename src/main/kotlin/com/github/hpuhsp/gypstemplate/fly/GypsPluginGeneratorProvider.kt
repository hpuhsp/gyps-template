package com.github.hpuhsp.gypstemplate.fly

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.activities.common.MIN_API
import java.io.File

/**
 * @Description: 模板配置
 * @Author:   Hsp
 * @Email:    1101121039@qq.com
 * @CreateTime:     2023/8/19 10:58
 * @UpdateRemark:   更新说明：
 */
class GypsPluginGeneratorProvider : WizardTemplateProvider() {
    override fun getTemplates(): List<Template> {
        return listOf(gypsActivityTemplate, gypsFragmentTemplate)
    }
    
    /**
     * 配置Activity自定义模板
     */
    private val gypsActivityTemplate: Template
        get() = template {
            name = "Gyps一键生成Activity"
            description =
                "快速创建基于Gyps框架的页面文件，包括Activity、Fragment、ViewModel、ViewBinding、 Repository、Service等类，以及相关资源文件（可选）"
            minApi = MIN_API
            category = Category.Activity
            formFactor = FormFactor.Mobile
            screens = listOf(
                WizardUiContext.ActivityGallery,
                WizardUiContext.MenuEntry,
                WizardUiContext.NewProject,
                WizardUiContext.NewModule
            )
            thumb { File("template_blank_activity.png") }
            
            widgets(
                PackageNameWidget(targetPackageName),
                TextFieldWidget(activityName),
                CheckBoxWidget(needActivity),
                TextFieldWidget(activityLayoutName),
                CheckBoxWidget(generateActivityLayout),
                TextFieldWidget(activityGeneratedLocation),
                CheckBoxWidget(needViewModel),
                CheckBoxWidget(needRepository),
                CheckBoxWidget(needResourcePrefix),
                LanguageWidget()
            )
            
            // 模板代码文件创建
            recipe = { it ->
                gypsActivityRecipe(this@GypsPluginGeneratorProvider, (it as ModuleTemplateData))
            }
        }
    
    /**
     * 配置Activity自定义模板
     */
    private val gypsFragmentTemplate: Template
        get() = template {
            name = "Gyps一键生成Fragment"
            description =
                "快速创建基于Gyps框架的页面文件，包括Fragment、ViewModel、ViewBinding、 Repository、Service等类，以及相关资源文件（可选）"
            minApi = MIN_API
            category = Category.Fragment
            formFactor = FormFactor.Mobile
            screens = listOf(
                WizardUiContext.ActivityGallery,
                WizardUiContext.MenuEntry,
                WizardUiContext.NewProject,
                WizardUiContext.NewModule
            )
            thumb { File("template_blank_activity.png") }
            
            widgets(
                PackageNameWidget(targetPackageName),
                TextFieldWidget(fragmentName),
                CheckBoxWidget(needFragment),
                CheckBoxWidget(createLazyFragment),
                TextFieldWidget(fragmentLayoutName),
                CheckBoxWidget(generateFragmentLayout),
                TextFieldWidget(fragmentGeneratedLocation),
                CheckBoxWidget(needViewModel),
                CheckBoxWidget(needRepository),
                CheckBoxWidget(needResourcePrefix),
                LanguageWidget()
            )
            
            // 模板代码文件创建
            recipe = { it ->
                gypsFragmentRecipe(this@GypsPluginGeneratorProvider, (it as ModuleTemplateData))
            }
        }
    
    /**
     * 类文件生成位置的文件目录
     */
    val targetPackageName = stringParameter {
        name = "Root Package Name"
        constraints = listOf(Constraint.PACKAGE)
        default = "com.gyps.app"
        suggest = { packageName }
        help = "类文件生成位置的文件目录"
    }
    
    /**
     * 定义 Activity 名称
     */
    val activityName = stringParameter {
        name = "Activity Name"
        constraints = listOf(Constraint.UNIQUE, Constraint.NONEMPTY, Constraint.STRING)
        default = "Main"
        help = "定义 Activity 名称"
    }
    
    /**
     * 是否需要创建 Activity
     */
    val needActivity = booleanParameter {
        name = "Generate Activity"
        default = true
        help = "是否需要创建Activity? 默认创建"
    }
    
    /**
     * 定义 Activity 布局文件名称
     */
    val activityLayoutName = stringParameter {
        name = "Activity Layout Name"
        constraints = listOf(Constraint.LAYOUT, Constraint.NONEMPTY)
        suggest = { activityToLayout(activityName.value) }
        default = "activity_main"
        visible = { needActivity.value }
        help = "如果已创建XML布局文件，则填写对应名称。否则请勾选下方框创建新的布局文件"
    }
    
    /**
     * 是否需要创建新的 Activity 布局文件
     */
    val generateActivityLayout = booleanParameter {
        name = "Generate Activity Layout"
        default = true
        visible = { needActivity.value }
        help = "勾选后使用上方定义的名称为Activity创建布局文件"
    }
    
    /**
     * Activity 生成位置，可重新进行编辑
     */
    val activityGeneratedLocation = stringParameter {
        name = "Activity Package Name"
        constraints = listOf(Constraint.PACKAGE, Constraint.STRING, Constraint.NONEMPTY)
        suggest = { "${targetPackageName.value}.ui.activity" }
        visible = { needActivity.value }
        default = "${targetPackageName.value}.ui.activity"
        help = "Activity 类生成目录，可根据需要进行修改调整"
    }
    
    
    /**
     * 定义 Fragment 名称
     */
    val fragmentName = stringParameter {
        name = "Fragment Name"
        constraints = listOf(Constraint.UNIQUE, Constraint.NONEMPTY, Constraint.STRING)
        default = "Main"
        help = "定义 Fragment 名称"
    }
    
    /**
     * 是否需要创建 Fragment
     */
    val needFragment = booleanParameter {
        name = "Generate Fragment"
        default = true
        help = "是否需要生成Fragment ? 默认不生成"
    }
    
    /**
     * 是否继承BaseLazyFragment ? 默认继承BaseFragment
     */
    val createLazyFragment = booleanParameter {
        name = "Lazy Fragment"
        default = false
        visible = { needFragment.value }
        help = "是否继承BaseLazyFragment ? 默认继承BaseFragment"
    }
    
    /**
     *  定义Fragment 布局文件名称
     */
    val fragmentLayoutName = stringParameter {
        name = "Fragment Layout Name"
        constraints = listOf(Constraint.LAYOUT, Constraint.NONEMPTY)
        suggest = { fragmentToLayout(fragmentName.value) }
        default = "fragment_main"
        visible = { needFragment.value }
        help = "如果已创建XML布局文件，则填写对应名称。否则请勾选下方框创建新的布局文件"
    }
    
    /**
     * 是否需要创建新的 Fragment 布局文件
     */
    val generateFragmentLayout = booleanParameter {
        name = "Generate Fragment Layout"
        default = true
        visible = { needFragment.value }
        help = "勾选后使用上方定义的名称为Fragment创建布局文件"
    }
    
    /**
     * Fragment 生成位置，可重新进行编辑
     */
    val fragmentGeneratedLocation = stringParameter {
        name = "Fragment Class Location"
        constraints = listOf(Constraint.PACKAGE, Constraint.STRING, Constraint.NONEMPTY)
        suggest = { "${targetPackageName.value}.ui.fragment" }
        default = "${targetPackageName.value}.ui.fragment"
        visible = { needFragment.value }
        help = "Fragment 类生成目录，可根据需要进行修改调整"
    }
    
    /**
     * 是否需要创建 ViewModel
     */
    val needViewModel = booleanParameter {
        name = "Generate ViewModel"
        default = true
        help = "是否需要生成 ViewModel ? 默认不生成"
    }
    
    /**
     * 是否需要创建 Repository
     */
    val needRepository = booleanParameter {
        name = "Generate Repository"
        default = true
        help = "是否需要生成 Repository ? 默认不生成"
    }
    
    /**
     * 是否定义资源前缀（根据需要，不定义则不添加至文件名称中，默认自动获取）
     */
    val needResourcePrefix = booleanParameter {
        name = "Add ResourcePrefix"
        default = true
        help = "是否添加资源前缀，默认自动获取Module定义的resourcePrefix值,重定义类和资源文件名称"
    }
}