package com.pickyeaters.logic.view.gui;

import com.pickyeaters.logic.controller.application.MainController;
import javafx.scene.layout.BorderPane;

public class FindRestaurantView extends VirtualPaneView {
    public FindRestaurantView(MainController controller, VirtualPaneView parent) {
        super(controller, "/form/pickie/findARestaurantView.fxml", parent);
    }

    @Override
    protected void setup() {

    }
}
