package com.diplom.gimch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class activity_main_card : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CardAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_card)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val cards = listOf(
            CardData("Эл Лоулайт", 25, R.drawable.l),
           CardData("Йор Форджер", 27, R.drawable.yor),
           CardData("Маюри Сиина", 16, R.drawable.mayuri),
          CardData("Эрен Йегер", 19, R.drawable.eren),
            CardData("Куклин Данила", 50, R.drawable.dan),
            CardData("Федоров Владимир", 70, R.drawable.vov),

        )

        adapter = CardAdapter(cards)
        recyclerView.adapter = adapter
    }
}
