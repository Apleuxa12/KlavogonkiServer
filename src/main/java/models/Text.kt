package models

data class Text(val theme: String, var value: String){

    fun countProgress(text: String): Double{
        var progress = 0.0
        value.forEachIndexed cycle@{ i, letter ->
            if(letter == text[i])
                progress++
            else
                return@cycle
        }
        return progress / value.length
    }

}