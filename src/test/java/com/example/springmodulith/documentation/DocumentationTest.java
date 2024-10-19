package com.example.springmodulith.documentation;

import com.example.springmodulith.SpringModulithApplication;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class DocumentationTest {

    ApplicationModules modules = ApplicationModules.of(SpringModulithApplication.class);

    @Test
    void writeDocumentationSnippets() {
//        new Documenter(modules)
//                .writeModulesAsPlantUml()
//                .writeIndividualModulesAsPlantUml()
//                .writeModuleCanvases();

        new Documenter(modules)
                .writeModulesAsPlantUml(
                        Documenter.DiagramOptions.defaults()
                                .withStyle(Documenter.DiagramOptions.DiagramStyle.UML)
                )
                .writeIndividualModulesAsPlantUml(
                        Documenter.DiagramOptions.defaults()
                                .withStyle(Documenter.DiagramOptions.DiagramStyle.UML)
                )
                .writeModuleCanvases();

//        Documenter.DiagramOptions.defaults()
//                .withStyle(Documenter.DiagramOptions.DiagramStyle.UML);
    }
}
