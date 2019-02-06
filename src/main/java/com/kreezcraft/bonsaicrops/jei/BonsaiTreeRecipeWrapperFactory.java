package com.kreezcraft.bonsaicrops.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import com.kreezcraft.bonsaicrops.api.IBonsaiTreeType;

public class BonsaiTreeRecipeWrapperFactory implements IRecipeWrapperFactory<IBonsaiTreeType>{
    @Override
    public IRecipeWrapper getRecipeWrapper(IBonsaiTreeType recipe) {
        return new BonsaiTreeRecipeWrapper(recipe);
    }
}
