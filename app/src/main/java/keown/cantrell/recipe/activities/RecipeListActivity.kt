package keown.cantrell.recipe.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recipe_list.*
import kotlinx.android.synthetic.main.card_recipe.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import keown.cantrell.recipe.R
import keown.cantrell.recipe.main.MainApp
import keown.cantrell.recipe.models.RecipeModel

class RecipeListActivity : AppCompatActivity(), RecipeListener {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_recipe_list)
    app = application as MainApp
    toolbar.title = title
    setSupportActionBar(toolbar)

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = RecipeAdapter(app.recipes.findAll(), this)
    loadRecipes()
  }

  private fun loadRecipes() {
    showRecipes( app.recipes.findAll())
  }

  fun showRecipes (recipes: List<RecipeModel>) {
    recyclerView.adapter = RecipeAdapter(recipes, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_add -> startActivityForResult<RecipeActivity>(200)
      R.id.item_map -> startActivity<RecipeMapsActivity>()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onRecipeClick(recipe: RecipeModel) {
    startActivityForResult(intentFor<RecipeActivity>().putExtra("recipe_edit", recipe), 0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadRecipes()
    super.onActivityResult(requestCode, resultCode, data)
  }
}

