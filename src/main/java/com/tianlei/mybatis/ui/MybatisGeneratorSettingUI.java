package com.tianlei.mybatis.ui;

import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.psi.PsiPackage;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBTextField;
import com.tianlei.mybatis.model.Config;
import com.tianlei.mybatis.setting.PersistentConfig;
import com.tianlei.mybatis.util.JTextFieldHintListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class MybatisGeneratorSettingUI extends JDialog {
    public JPanel contentPanel = new JBPanel<>(new GridLayout(1, 1));

    private JBTextField modelPackageField = new JBTextField(30);

    private JBTextField daoPackageField = new JBTextField(35);

    private JBTextField xmlPackageField = new JBTextField(35);

    private JTextField daoPostfixField = new JTextField(35);

    private JTextField modelPostfixField = new JTextField(3);

    private TextFieldWithBrowseButton projectFolderBtn = new TextFieldWithBrowseButton();

    private JTextField modelMvnField = new JBTextField(15);

    private JTextField daoMvnField = new JBTextField(15);

    private JTextField xmlMvnField = new JBTextField(15);

    //private JButton setProjectBtn = new JButton("Set-Project-Path");

    //    private JCheckBox commentBox = new JCheckBox("Comment(实体注释)");
    //
    //    private JCheckBox overrideXMLBox = new JCheckBox("Overwrite-Xml");
    //
    //    private JCheckBox overrideJavaBox = new JCheckBox("Overwrite-Java");
    //
    //    private JCheckBox needToStringHashcodeEqualsBox = new JCheckBox("toString/hashCode/equals");
    //
    //    private JCheckBox useSchemaPrefixBox = new JCheckBox("Use-Schema(使用Schema前缀)");
    //
    //    private JCheckBox annotationDAOBox = new JCheckBox("Repository-Annotation(Repository注解)");
    //
    //    private JCheckBox useDAOExtendStyleBox = new JCheckBox("Parent-Interface(Dao公共父接口)");
    //
    //    private JCheckBox jsr310SupportBox = new JCheckBox("JSR310: Date and Time API");
    //
    //    private JCheckBox annotationBox = new JCheckBox("JPA-Annotation(JPA注解)");
    //
    //    private JCheckBox useActualColumnNamesBox = new JCheckBox("Actual-Column(实际的列名)");
    //
    //    private JCheckBox useTableNameAliasBox = new JCheckBox("Use-Alias(启用别名查询)");
    //
    //    private JCheckBox useExampleBox = new JCheckBox("Use-Example");
    //
    //    private JCheckBox offsetLimitBox = new JCheckBox("Page(分页，需开启Use-Example)");
    //
    //    private JCheckBox needForUpdateBox = new JCheckBox("Add-ForUpdate(需开启Use-Example)");
    //
    //    private JCheckBox useLombokBox = new JCheckBox("Use-Lombok");

    private PersistentConfig config;

    public MybatisGeneratorSettingUI() {
        setContentPane(contentPanel);
    }

    public void createUI(Project project) {
        String projectFolder = project.getBasePath();
        contentPanel.setPreferredSize(new Dimension(0, 0));

        /**
         * project folder
         */
        JPanel projectFolderPanel = new JPanel();
        projectFolderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel projectLabel = new JLabel("project folder:");
        projectFolderPanel.add(projectLabel);
        projectFolderBtn.setTextFieldPreferredWidth(55);
        projectFolderBtn.setText(projectFolder);
        projectFolderBtn.addBrowseFolderListener(
                new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor()) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        super.actionPerformed(e);
                        projectFolderBtn.setText(projectFolderBtn.getText().replaceAll("\\\\", "/"));
                    }
                });
        projectFolderPanel.add(projectFolderBtn);
        //projectFolderPanel.add(setProjectBtn);

        /**
         * mode panel
         */
        JPanel modelPanel = new JPanel();
        modelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        modelPanel.setBorder(BorderFactory.createTitledBorder("Model Setting"));
        modelPanel.add(new JLabel("Model postfix:"));
        modelPostfixField.setText("PO");
        modelPanel.add(modelPostfixField);
        JBLabel labelLeft4 = new JBLabel("package:");
        modelPanel.add(labelLeft4);
        modelPanel.add(modelPackageField);
        JButton packageBtn1 = new JButton("...");
        packageBtn1.addActionListener(actionEvent -> {
            final PackageChooserDialog chooser = new PackageChooserDialog("chooser model package", project);
            chooser.selectPackage(modelPackageField.getText());
            chooser.show();
            final PsiPackage psiPackage = chooser.getSelectedPackage();
            String packageName = psiPackage == null ? null : psiPackage.getQualifiedName();
            modelPackageField.setText(packageName);
        });
        modelPanel.add(packageBtn1);
        //        modelPanel.add(new JLabel("path:"));
        //        modelMvnField.setText("src/main/java");
        //        modelPanel.add(modelMvnField);

        /**
         * dao panel
         */
        JPanel daoPanel = new JPanel();
        daoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        daoPanel.setBorder(BorderFactory.createTitledBorder("Mapper Setting"));

        //        daoPanel.add(new JLabel("Mapper postfix:"));
        //        daoPostfixField.setText("Mapper");
        //        daoPanel.add(daoPostfixField);

        JLabel labelLeft5 = new JLabel("package:");
        daoPanel.add(labelLeft5);
        daoPanel.add(daoPackageField);

        JButton packageBtn2 = new JButton("...");
        packageBtn2.addActionListener(actionEvent -> {
            final PackageChooserDialog chooser = new PackageChooserDialog("choose mapper package", project);
            chooser.selectPackage(daoPackageField.getText());
            chooser.show();
            final PsiPackage psiPackage = chooser.getSelectedPackage();
            String packageName = psiPackage == null ? null : psiPackage.getQualifiedName();
            daoPackageField.setText(packageName);
        });
        daoPanel.add(packageBtn2);
        //        daoPanel.add(new JLabel("path:"));
        //        daoMvnField.setText("src/main/java");
        //        daoPanel.add(daoMvnField);

        /**
         * xml mapper panel
         */
        JPanel xmlMapperPanel = new JPanel();
        xmlMapperPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        xmlMapperPanel.setBorder(BorderFactory.createTitledBorder("XML Mapper Setting"));
        JLabel labelLeft6 = new JLabel("package:");
        xmlMapperPanel.add(labelLeft6);
        xmlMapperPanel.add(xmlPackageField);
        //        xmlMapperPanel.add(new JLabel("path:"));
        //        xmlMvnField.setText("src/main/resources");
        //        xmlMapperPanel.add(xmlMvnField);

        /**
         * options panel
         */
        //        JBPanel optionsPanel = new JBPanel(new GridLayout(5, 5, 5, 5));
        //        optionsPanel.setBorder(BorderFactory.createTitledBorder("options panel"));

        /**
         * Default selected
         **/
        //        useLombokBox.setSelected(true);
        //        commentBox.setSelected(true);
        //        overrideJavaBox.setSelected(true);
        //        overrideXMLBox.setSelected(true);
        //        useSchemaPrefixBox.setSelected(true);
        //        annotationDAOBox.setSelected(true);

        //        optionsPanel.add(useLombokBox);
        //        optionsPanel.add(commentBox);
        //        optionsPanel.add(useSchemaPrefixBox);
        //        optionsPanel.add(overrideJavaBox);
        //        optionsPanel.add(overrideXMLBox);
        //        optionsPanel.add(annotationDAOBox);
        //        optionsPanel.add(needToStringHashcodeEqualsBox);
        //        optionsPanel.add(useDAOExtendStyleBox);
        //        optionsPanel.add(jsr310SupportBox);
        //        optionsPanel.add(annotationBox);
        //        optionsPanel.add(useActualColumnNamesBox);
        //        optionsPanel.add(useTableNameAliasBox);
        //        optionsPanel.add(useExampleBox);
        //        optionsPanel.add(offsetLimitBox);
        //        optionsPanel.add(needForUpdateBox);

        /**
         * 设置面板内容
         */
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainPanel.add(projectFolderPanel);
        mainPanel.add(modelPanel);
        mainPanel.add(daoPanel);
        mainPanel.add(xmlMapperPanel);
        //mainPanel.add(optionsPanel);
        contentPanel.add(mainPanel);

        config = PersistentConfig.getInstance(project);
        Map<String, Config> initConfig = config.getInitConfig();
        if (initConfig != null) {
            Config config = initConfig.get("initConfig");
            daoPostfixField.setText(config.getDaoPostfix());
            modelPackageField.setText(config.getModelPackage());
            daoPackageField.setText(config.getDaoPackage());
            xmlPackageField.setText(config.getXmlPackage());
            modelPostfixField.setText(config.getModelPostFix());
            projectFolderBtn.setText(config.getProjectFolder());
            //            offsetLimitBox.setSelected(config.isOffsetLimit());
            //            commentBox.setSelected(config.isComment());
            //            overrideXMLBox.setSelected(config.isOverrideXML());
            //            overrideJavaBox.setSelected(config.isOverrideJava());
            //            needToStringHashcodeEqualsBox.setSelected(config.isNeedToStringHashcodeEquals());
            //            useSchemaPrefixBox.setSelected(config.isUseSchemaPrefix());
            //            needForUpdateBox.setSelected(config.isNeedForUpdate());
            //            annotationDAOBox.setSelected(config.isAnnotationDAO());
            //            useDAOExtendStyleBox.setSelected(config.isUseDAOExtendStyle());
            //            jsr310SupportBox.setSelected(config.isJsr310Support());
            //            annotationBox.setSelected(config.isAnnotation());
            //            useActualColumnNamesBox.setSelected(config.isUseActualColumnNames());
            //            useTableNameAliasBox.setSelected(config.isUseTableNameAlias());
            //            useExampleBox.setSelected(config.isUseExample());
            //            useLombokBox.setSelected(config.isUseLombokPlugin());
        } else {
            modelPackageField.addFocusListener(new JTextFieldHintListener(modelPackageField, "generator"));
            daoPackageField.addFocusListener(new JTextFieldHintListener(daoPackageField, "generator"));
            xmlPackageField.addFocusListener(new JTextFieldHintListener(xmlPackageField, "mapper"));
        }
    }

    public boolean isModified() {
        boolean modified = true;
        //        modified |= !this.id.getText().equals(config.getId());
        //        modified |= !this.entity.getText().equals(config.getEntity());
        //        modified |= !this.project_directory.getText().equals(config.getProject_directory());
        //        modified |= !this.dao_name.getText().equals(config.getDao_name());
        //
        //        modified |= !this.entity_package.getText().equals(config.getEntity_package());
        //        modified |= !this.entity_directory.getText().equals(config.getEntity_directory());
        //        modified |= !this.mapper_package.getText().equals(config.getMapper_package());
        //        modified |= !this.mapper_directory.getText().equals(config.getMapper_directory());
        //        modified |= !this.xml_package.getText().equals(config.getXml_package());
        //        modified |= !this.xml_directory.getText().equals(config.getXml_directory());
        //        modified |= !this.password.getPassword().equals(config.getPassword());
        //        modified |= !this.username.getText().equals(config.getUsername());
        return modified;
    }

    public void apply() {
        HashMap<String, Config> initConfig = new HashMap<>();
        Config config = new Config();
        config.setName("initConfig");
        config.setDaoPostfix(daoPostfixField.getText());
        config.setModelPackage(modelPackageField.getText());
        config.setDaoPackage(daoPackageField.getText());
        config.setXmlPackage(xmlPackageField.getText());
        config.setProjectFolder(projectFolderBtn.getText());
        config.setModelPostFix(modelPostfixField.getText());
        //config.setOffsetLimit(offsetLimitBox.getSelectedObjects() != null);
        config.setComment(true);
        config.setOverrideXML(true);
        config.setOverrideJava(true);
        //config.setNeedToStringHashcodeEquals(needToStringHashcodeEqualsBox.getSelectedObjects() != null);
        //config.setUseSchemaPrefix(useSchemaPrefixBox.getSelectedObjects() != null);
        //config.setNeedForUpdate(needForUpdateBox.getSelectedObjects() != null);
        config.setAnnotationDAO(true);
        //config.setUseDAOExtendStyle(useDAOExtendStyleBox.getSelectedObjects() != null);
        config.setJsr310Support(true);
        config.setAnnotation(true);
        //config.setUseActualColumnNames(useActualColumnNamesBox.getSelectedObjects() != null);
        //config.setUseTableNameAlias(useTableNameAliasBox.getSelectedObjects() != null);
        //config.setUseExample(useExampleBox.getSelectedObjects() != null);
        config.setUseLombokPlugin(true);
        initConfig.put(config.getName(), config);
        this.config.setInitConfig(initConfig);
    }

    public void reset() {
    }

    @Override
    public JPanel getContentPane() {
        return contentPanel;
    }

}
