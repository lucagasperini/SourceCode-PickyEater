package com.pickyeaters.logic.model;

public class DishFirst extends Dish{
    public DishFirst(String name, Ingredient[] ingredientList) {
        super(name, ingredientList);
        this.type = TYPE_FIRST;
    }
}