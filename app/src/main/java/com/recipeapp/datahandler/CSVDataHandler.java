package com.recipeapp.datahandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

public class CSVDataHandler implements DataHandler {
    public String filepath;

    public CSVDataHandler() {
        this.filepath = "app/src/main/resources/recipes.csv";
    }

    public CSVDataHandler(String filepath) {
        this.filepath = filepath;
    }

    public String getMode() {
        return "CSV";
    }

    public ArrayList<Recipe> readData() throws IOException {
        ArrayList<String> recipes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            // レシピ名と具材
            String line;
            // レシピ名
            String recipeName;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                // レシピ名
                recipeName = parts[0];
                // 具材
                String[] ingredients = parts[1].split(",");
                ArrayList<Ingredient> recipeIngredients = new ArrayList<>();

                // 具材をひとつづつIngredientに変換してrecipeIngredientsに追加する
                for (String ingredient : ingredients) {
                    recipeIngredients.add(new Ingredient(ingredient));
                }
                recipes.add(new Recipe(recipeName, recipeIngredients));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        // リストで返す
        return recipes;
    }

    public void writeData(Recipe recipe) throws IOException{
        //ファイルにアクセス
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filepath, true)))) {
            //レシピ名と具材の結合結果
            String ingredientResult = recipe.getName();

            //受け取ったレシピをカンマでつなぐ
            ArrayList<Ingredient> recipeIngredients = recipe.getIngredients();
            for(int i = 0; i < recipeIngredients.size(); i++) {
                ingredientResult += "," + recipeIngredients.get(i).getName();
            }    
            
            //ファイルに書き込む
            //print.println(ingredientResult);
            System.out.println("Recipe added successfully.");
        } catch (IOException e) {
            System.out.println("Failed to add new recipe: " + e.getMessage());
        }

    }

    public ArrayList<Recipe> searchData(String keyword) throws IOException {
        return null;
    }
}
//解答