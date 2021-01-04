package keown.cantrell.recipe.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_recipe.view.*
import keown.cantrell.recipe.R
import keown.cantrell.recipe.helpers.readImageFromPath
import keown.cantrell.recipe.models.RecipeModel

interface RecipeListener {
  fun onRecipeClick(recipe: RecipeModel)
}

class RecipeAdapter constructor(private var recipes: List<RecipeModel>,
                                private val listener: RecipeListener) : RecyclerView.Adapter<RecipeAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_recipe, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val recipe = recipes[holder.adapterPosition]
    holder.bind(recipe, listener)
  }

  override fun getItemCount(): Int = recipes.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(recipe: RecipeModel, listener: RecipeListener) {
      itemView.recipeTitle.text = recipe.title
      itemView.description.text = recipe.description
      itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, recipe.image))
      itemView.setOnClickListener { listener.onRecipeClick(recipe) }
    }
  }
}

