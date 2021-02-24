package com.example.android2.model

data class MovieDTO(
        val results : List<ResultsDTO>
)

data class ResultsDTO(
        //val original_language: String,
        //val poster_path: String,
        //val vote_average: Double,
        val overview: String,
        //val release_date: String,
        val title: String,
        override val size: Int
) : List<ResultsDTO> {
        override fun contains(element: ResultsDTO): Boolean {
                TODO("Not yet implemented")
        }

        override fun containsAll(elements: Collection<ResultsDTO>): Boolean {
                TODO("Not yet implemented")
        }

        override fun get(index: Int): ResultsDTO {
                TODO("Not yet implemented")
        }

        override fun indexOf(element: ResultsDTO): Int {
                TODO("Not yet implemented")
        }

        override fun isEmpty(): Boolean {
                TODO("Not yet implemented")
        }

        override fun iterator(): Iterator<ResultsDTO> {
                TODO("Not yet implemented")
        }

        override fun lastIndexOf(element: ResultsDTO): Int {
                TODO("Not yet implemented")
        }

        override fun listIterator(): ListIterator<ResultsDTO> {
                TODO("Not yet implemented")
        }

        override fun listIterator(index: Int): ListIterator<ResultsDTO> {
                TODO("Not yet implemented")
        }

        override fun subList(fromIndex: Int, toIndex: Int): List<ResultsDTO> {
                TODO("Not yet implemented")
        }
}