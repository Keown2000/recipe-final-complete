package keown.cantrell.recipe.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import keown.cantrell.recipe.helpers.*
import java.util.*

val JSON_FILE = "recipes.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<RecipeModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class RecipeJSONStore : RecipeStore, AnkoLogger {

  val context: Context
  var recipes = mutableListOf<RecipeModel>()

  constructor (context: Context) {
    this.context = context
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<RecipeModel> {
    return recipes
  }

  override fun create(recipe: RecipeModel) {
    recipe.id = generateRandomId()
    recipes.add(recipe)
    serialize()
  }


  override fun update(recipe: RecipeModel) {
    // todo
  }

  override fun delete(placemark: RecipeModel) {
    recipes.remove(placemark)
    serialize()
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(recipes, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    recipes = Gson().fromJson(jsonString, listType)
  }
}

