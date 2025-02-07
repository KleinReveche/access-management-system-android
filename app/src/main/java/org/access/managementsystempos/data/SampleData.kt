package org.access.managementsystempos.data

import org.access.managementsystempos.R
import org.access.managementsystempos.domain.models.Product
import org.access.managementsystempos.domain.models.ProductCategory

object SampleData {
    private val all = ProductCategory("All", null)
    private val streetFood = ProductCategory("Street Food", all)
    private val drinks = ProductCategory("Drinks", all)
    private val riceMeal = ProductCategory("Rice Meal", all)

    val categories = arrayOf(
        all, streetFood, drinks, riceMeal
    )

    private val kwekKwek = Product("Kwek-Kwek", 25.0f, streetFood, R.drawable.kwek_kwek)
    private val frenchFries = Product("French Fries", 20.0f, streetFood, R.drawable.french_fries)
    private val cheeseStick = Product("Cheese Sticks", 20.0f, streetFood, R.drawable.cheese_sticks)
    private val fishBalls = Product("Fish Balls", 20.0f, streetFood, R.drawable.fishballs)
    private val kikiam = Product("Kikiam", 20.0f, streetFood, R.drawable.kikiam)
    private val icedTea = Product("Iced Tea", 20.0f, drinks, R.drawable.iced_tea)
    private val icedTeaWFries =
        Product("Iced Tea w/Fries", 40.0f, drinks, R.drawable.iced_tea_fries)
    private val liempoWRice =
        Product("Liempo w/Rice", 100.0f, riceMeal, R.drawable.liempo_rice_meal)

    val food = arrayOf(
        kwekKwek, frenchFries, cheeseStick, fishBalls, kikiam, icedTea, icedTeaWFries, liempoWRice
    )
}