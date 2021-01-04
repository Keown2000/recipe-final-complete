package keown.cantrell.recipe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_recipe.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import keown.cantrell.recipe.R
import keown.cantrell.recipe.helpers.readImage
import keown.cantrell.recipe.helpers.readImageFromPath
import keown.cantrell.recipe.helpers.showImagePicker
import keown.cantrell.recipe.main.MainApp
import keown.cantrell.recipe.models.Location
import keown.cantrell.recipe.models.RecipeModel

class RecipeActivity : AppCompatActivity(), AnkoLogger {

  var recipe = RecipeModel()
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2
  var edit = false;
  var location = Location(52.245696, -7.139102, 15f)


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_recipe)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Recipe Activity started..")

    app = application as MainApp

    if (intent.hasExtra("recipe_edit")) {
      edit = true
      recipe = intent.extras?.getParcelable<RecipeModel>("recipe_edit")!!
      recipeTitle.setText(recipe.title)
      description.setText(recipe.description)
      recipeImage.setImageBitmap(readImageFromPath(this, recipe.image))
      if (recipe.image != null) {
        chooseImage.setText(R.string.change_recipe_image)
      }
      btnAdd.setText(R.string.save_recipe)
    }

    btnAdd.setOnClickListener() {
      recipe.title = recipeTitle.text.toString()
      recipe.description = description.text.toString()
      if (recipe.title.isEmpty()) {
        toast(R.string.enter_recipe_title)
      } else {
        if (edit) {
          app.recipes.update(recipe.copy())
        } else {

          app.recipes.create(recipe.copy())
        }
      }
      info("add Button Pressed: $recipeTitle")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }

    recipeLocation.setOnClickListener {
      val location = Location(52.245696, -7.139102, 15f)
      if (recipe.zoom != 0f) {
        location.lat =  recipe.lat
        location.lng = recipe.lng
        location.zoom = recipe.zoom
      }
      startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_recipe, menu)
    if (edit && menu != null) menu.getItem(0).setVisible(true)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_delete -> {
        app.recipes.delete(recipe)
        finish()
      }
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          recipe.image = data.getData().toString()
          recipeImage.setImageBitmap(readImage(this, resultCode, data))
          chooseImage.setText(R.string.change_recipe_image)
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras?.getParcelable<Location>("location")!!
          recipe.lat = location.lat
          recipe.lng = location.lng
          recipe.zoom = location.zoom
        }
      }
    }
  }

}

