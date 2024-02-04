package com.pickyeaters.logic.controller.application.restaurateur;

import com.pickyeaters.logic.controller.application.DatabaseController;
import com.pickyeaters.logic.controller.application.LoginController;
import com.pickyeaters.logic.controller.application.SettingsController;
import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.ControllerException;
import com.pickyeaters.logic.controller.exception.DatabaseControllerException;
import com.pickyeaters.logic.controller.exception.SettingsControllerException;
import com.pickyeaters.logic.view.bean.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuDetailsControllerTest {
    UserBean user;
    @BeforeEach
    void setUp() throws ControllerException, BeanException {
        SettingsController.getInstance().init();
        DatabaseController.getInstance().init();

        LoginController loginController = new LoginController();
        user = loginController.auth(new LoginBean("lucar", "luca"));

        AddDishController addDishController = new AddDishController();

        EditDishBean dish1 = new EditDishBean("First", "", "DRINK");
        dish1.getIngredientList().add(new DishIngredientBean("Pollo"));

        EditDishBean dish2 = new EditDishBean("Second", "", "DRINK");
        dish1.getIngredientList().add(new DishIngredientBean("Pollo"));

        EditDishBean dish3 = new EditDishBean("Third", "", "DRINK");
        dish1.getIngredientList().add(new DishIngredientBean("Pollo"));

        EditDishBean dishRemove = new EditDishBean("To remove", "", "DRINK");
        dish1.getIngredientList().add(new DishIngredientBean("Pollo"));

        EditDishBean dishToggle = new EditDishBean("To toggle", "", "DRINK");
        dish1.getIngredientList().add(new DishIngredientBean("Pollo"));

        addDishController.add(dish1, user.getRestaurant().getID());
        addDishController.add(dish2, user.getRestaurant().getID());
        addDishController.add(dish3, user.getRestaurant().getID());
        addDishController.add(dishRemove, user.getRestaurant().getID());
        addDishController.add(dishToggle, user.getRestaurant().getID());
    }

    @Test
    void getMenu() throws ControllerException, BeanException {
        MenuDetailsController menuDetailsController = new MenuDetailsController();
        List<ShowDishBean> menu = menuDetailsController.getMenu(user.getRestaurant().getID());
        List<String> names = new ArrayList<>(List.of(new String[]{"First", "Second", "Third"}));
        for(ShowDishBean i : menu) {
            assertTrue(names.contains(i.getName()));
        }
    }

    @Test
    void deleteDish() throws ControllerException {
        MenuDetailsController menuDetailsController = new MenuDetailsController();
        menuDetailsController.deleteDish("To remove", user.getRestaurant().getID());
    }

    @Test
    void toggleDish() throws ControllerException, BeanException {
        MenuDetailsController menuDetailsController = new MenuDetailsController();
        menuDetailsController.toggleDish("To toggle", user.getRestaurant().getID());

        List<ShowDishBean> menu = menuDetailsController.getMenu(user.getRestaurant().getID());
        for(ShowDishBean i : menu) {
            if(i.getName().equals("To toggle")) {
                assertFalse(i.isActive());
            }
        }
    }
}