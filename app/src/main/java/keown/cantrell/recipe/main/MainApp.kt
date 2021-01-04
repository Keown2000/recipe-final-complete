
package keown.cantrell.recipe.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import keown.cantrell.recipe.models.RecipeJSONStore
import keown.cantrell.recipe.models.RecipeMemStore
import keown.cantrell.recipe.models.RecipeStore

class MainApp : Application(), AnkoLogger {

  lateinit var recipes: RecipeStore

  override fun onCreate() {
    super.onCreate()
    recipes = RecipeJSONStore(applicationContext)
    info("Recipe started")
  }
}

