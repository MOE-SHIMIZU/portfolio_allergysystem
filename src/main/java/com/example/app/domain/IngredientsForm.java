package com.example.app.domain;

import java.util.List;

import javax.validation.Valid;

import lombok.Data;

@Data
public class IngredientsForm {

    @Valid
    private List<Ingredient> ingredients;
    
}

