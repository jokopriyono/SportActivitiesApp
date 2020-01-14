package com.rizzkijo.sportactivitiesapp

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry

object DummyData {
    fun lineEntriesData(): List<Entry> {
        val dataset = mutableListOf<Entry>()
        dataset.add(Entry(0f, 0f))
        dataset.add(Entry(1f, 70f))
        dataset.add(Entry(2f, 60f))
        dataset.add(Entry(3f, 65f))
        dataset.add(Entry(4f, 50f))
        dataset.add(Entry(5f, 75f))
        dataset.add(Entry(6f, 45f))
        return dataset
    }

    fun pieEntriesData(): List<PieEntry> {
        val dataset = mutableListOf<PieEntry>()
        dataset.add(PieEntry(55f, "Cycling 55%"))
        dataset.add(PieEntry(23f, "Running 23%"))
        dataset.add(PieEntry(13f, "Soccer 13%"))
        dataset.add(PieEntry(9f, "Weightlifting 9%"))
        return dataset
    }

}