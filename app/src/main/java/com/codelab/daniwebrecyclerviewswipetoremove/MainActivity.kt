package com.codelab.daniwebrecyclerviewswipetoremove

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Creates a list of 10 elements
        val dataset = MutableList(10){
            Item("I am Item #$it")
        }

        //finds the RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        //Assigns an adapter to RecyclerView
        recyclerView.adapter = ItemAdapter(dataset)

        //Implementing the callback.
        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, //bitwise OR
            ItemTouchHelper.START or ItemTouchHelper.END //bitwise OR
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                //modifying the dataset as well is optional for this tutorial
//                val movedItem = dataset.removeAt(fromPosition)
//                dataset.add(toPosition, movedItem)

                //push specific event
                recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                //Actually removes the item from the dataset
                dataset.removeAt(position)

                //push specific event
                recyclerView.adapter?.notifyItemRemoved(position)
            }

        }

        //Creates touch helper with callback
        val touchHelper = ItemTouchHelper(callback)

        //attaches the helper to the recyclerView
        touchHelper.attachToRecyclerView(recyclerView)
    }
}