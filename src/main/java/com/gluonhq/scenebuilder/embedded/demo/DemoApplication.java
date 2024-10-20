/*
 * Copyright (c) 2024, Gluon and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Gluon nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.gluonhq.scenebuilder.embedded.demo;

import com.gluonhq.scenebuilder.embedded.DependenciesScanner;
import com.gluonhq.scenebuilder.embedded.SceneBuilderPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

public class DemoApplication extends Application {

    @Override
    public void start(Stage stage) {
        SceneBuilderPane sceneBuilderPane = new SceneBuilderPane();

        Button newButton = new Button("New FXML");
        newButton.setOnAction(e -> sceneBuilderPane.newFXML());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open FXML File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FXML Files", "*.fxml"));
        Button fileButton = new Button("Open FXML File");
        fileButton.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    sceneBuilderPane.openFXML(file.toURI().toURL());
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        Button saveButton = new Button("Save FXML");
        saveButton.setOnAction(e -> sceneBuilderPane.saveFXML(stage));

        HBox top = new HBox(50, newButton, fileButton, saveButton);
        top.setPadding(new Insets(20));
        top.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane(sceneBuilderPane);
        root.setTop(top);
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Hello Scene Builder Embedded!");
        stage.setScene(scene);
        stage.show();

        // custom library from module path and class path
        List<Path> scan = DependenciesScanner.scan();
        sceneBuilderPane.createCustomLibrary(scan);
    }

    public static void main(String[] args) {
        launch();
    }

}