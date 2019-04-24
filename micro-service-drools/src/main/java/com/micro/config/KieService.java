package com.micro.config;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Service
public class KieService {

    private StatelessKieSession kieSession;

//    @Autowired
//    private BlackListService blackListService;
//
//    @Autowired
//    private DimensionService dimensionService;

    private InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

    private static Logger logger = LoggerFactory.getLogger(KieService.class);


    /**
     * drools全局服务变量
     */
   private void setGlobal() {
//        kieSession.setGlobal("blackListService", blackListService);
//        kieSession.setGlobal("dimensionService", dimensionService);
   }


    /**
     * 规则集上线
     *
     * @param packageName
     */
    public void addPackage(String packageName) {
        try {
            URL url=getClass().getClassLoader().getResource(packageName);
            String path=url.getPath();
            logger.info("path:============="+path);
//            List<String>fileNames=new ArrayList<>();
//            if(path.contains("jar")){
//                JarFile jarFile=((JarURLConnection)url.openConnection()).getJarFile();
//                for(Enumeration<JarEntry> enumeration =  jarFile.entries(); enumeration.hasMoreElements(); ) {
//                    JarEntry jarEntry = enumeration.nextElement();
//                    System.out.println("==========="+jarEntry.getName());
//                    if (jarEntry.getName().contains(packageName)&&jarEntry.getName().endsWith(".drl")){
//                        fileNames.add(jarEntry.getName().replace(packageName+"/",""));
//                    }
//                }
//                CodeSource codeSource = getClass().getProtectionDomain().getCodeSource();
//                File jFile = new File(codeSource.getLocation().toURI().getPath()+"/"+packageName);
//                logger.info("========================jFile"+jFile.getName());
//            }else {
//                File file = new File(path);
//                if (file.isDirectory()) {
//                    for (File f : file.listFiles()) {
//                        if (f.getName().endsWith(".drl")) {
//                            fileNames.add(f.getName());
//                        }
//                    }
//                }
//            }
            File file = null;
            if(path.contains("/target/classes/")){
                file = new File(path);
            }else {
                String jarPath=new File("").getCanonicalPath();
                file=new File(jarPath+"/"+packageName);
            }
            logger.info("drl============:"+file.getAbsolutePath());
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
            for (File name :file.listFiles()) {
                if (name.getName().endsWith(".drl")) {
                    kbuilder.add(ResourceFactory.newFileResource(name), ResourceType.DRL);
                    if (kbuilder.hasErrors()) {
                        logger.error("Unable to compile drl. " + packageName + name);
                        return;
                    } else {
                        String ruleName = name.getName().replace(".drl", "");
                        if (kbase.getRule(packageName, ruleName) != null) {
                            logger.info("update rule: " + packageName + "." + ruleName);
                        } else {
                            logger.info("add rule: " + packageName + "." + ruleName);
                        }
                    }
                }
            }

            kbase.addPackages(kbuilder.getKnowledgePackages());
            kieSession = kbase.newStatelessKieSession();
            setGlobal();
            printRules();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 单一规则上线
     *
     * @param packageName
     * @param ruleName
     */
    public void addRule(String packageName, String ruleName) {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        String jarPath= null;
        try {
            jarPath = new File("").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file=new File(jarPath+"/"+packageName + "/" + ruleName + ".drl");
        kbuilder.add(ResourceFactory.newFileResource(file), ResourceType.DRL);
        if (kbuilder.hasErrors()) {
            logger.error("Unable to compile drl. " + packageName + ruleName + ".drl");
            return;
        } else {
            if (kbase.getRule(packageName, ruleName) != null) {
                logger.info("update rule: " + packageName + "." + ruleName);
            } else {
                logger.info("add rule: " + packageName + "." + ruleName);
            }
        }

        kbase.addPackages(kbuilder.getKnowledgePackages());
        kieSession = kbase.newStatelessKieSession();
        setGlobal();
        printRules();
    }

    /**
     * 单一规则下线
     *
     * @param packageName
     * @param ruleName
     */
    public void removeRule(String packageName, String ruleName) {
        if (kbase.getRule(packageName, ruleName) != null) {
            kbase.removeRule(packageName, ruleName);
            logger.info("remove rule: " + packageName + "." + ruleName);
            kieSession = kbase.newStatelessKieSession();
            setGlobal();
            printRules();
        } else {
            logger.error("no rule: " + packageName + ruleName);
        }
    }

    /**
     * 规则集下线
     *
     * @param packageName
     */
    public void removePackage(String packageName) {
        if (kbase.getPackage(packageName) != null) {
            kbase.removeKiePackage(packageName);
            logger.info("remove package: " + packageName);
            kieSession = kbase.newStatelessKieSession();
            setGlobal();
            printRules();
        } else {
            logger.error("no package: " + packageName);
        }
    }

    /**
     * 打印规则
     */
    public void printRules() {
        logger.info("print rule: -----------------------");
        kbase.getKiePackages().forEach(knowledgePackage ->
                knowledgePackage.getRules().forEach(rule ->
                        logger.info("print rule: " + knowledgePackage.getName() + "." + rule.getName())));
        logger.info("print rule: -----------------------");
    }

    @PostConstruct
    public void init() {
        addPackage("stateless");
    }


    /**
     * 规则引擎执行
     *
     * @param object
     */
    public void execute(Object object) {
        this.kieSession.execute(object);
    }



}
