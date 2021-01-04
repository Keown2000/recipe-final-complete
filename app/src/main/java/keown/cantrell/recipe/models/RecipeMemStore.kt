package keown.cantrell.recipe.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class RecipeMemStore : RecipeStore, AnkoLogger {

  val recipes = ArrayList<RecipeModel>()

  override fun findAll(): List<RecipeModel> {
    return recipes
  }

  override fun create(recipe: RecipeModel) {
    recipe.id = getId()
    recipes.add(recipe)
    logAll()
  }

  override fun update(placemark: RecipeModel) {
    var foundPlacemark: RecipeModel? = recipes.find { p -> p.id == placemark.id }
    if (foundPlacemark != null) {
      foundPlacemark.title = placemark.title
      foundPlacemark.description = placemark.description
      foundPlacemark.image = placemark.image
      foundPlacemark.lat = placemark.lat
      foundPlacemark.lng = placemark.lng
      foundPlacemark.zoom = placemark.zoom
      logAll();
    }
  }

  override fun delete(recipe: RecipeModel) {
    recipes.remove(recipe)
  }

  fun logAll() {
    recipes.forEach { info("${it}") }
  }
}

