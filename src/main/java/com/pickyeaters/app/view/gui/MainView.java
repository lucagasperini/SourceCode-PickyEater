package com.pickyeaters.app.view.gui;

import com.pickyeaters.app.view.VirtualView;
import com.pickyeaters.logic.controller.application.MainController;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainView extends VirtualView {
    public static final String backgroundView = "/backgroundTemplate.fxml";
    private Stage stage;
    private Parent root;
    private URL fxml = null;
    public MainView(Stage primaryStage) {
        super(new MainController());
        this.fxml = getClass().getResource(backgroundView);
        this.stage = primaryStage;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.fxml);
            loader.setController(this);
            this.root = loader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            //TODO:
            System.exit(-1);
        }
    }
    private final String APP_NAME = "Picky Eater";
    private final int WINDOW_HEIGTH = 720;
    private final int WINDOW_WIDTH = 1280;

    @FXML
    protected BorderPane mainLayout;

    @Override
    public void show() {
        controller.start();
        stage.setTitle(APP_NAME);

        try {
            controller.getInitController().loadFromFile();
        } catch (SettingsControllerException | DatabaseControllerException ex) {
            InitView initView = new InitView(controller);
            initView.show();
        }

        LoginView loginView = new LoginView(controller);
        loginView.show();

        MainPickieView mainPickieView = new MainPickieView(controller, mainLayout);
        mainPickieView.show();

        stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGTH));
        stage.show();
    }
}
