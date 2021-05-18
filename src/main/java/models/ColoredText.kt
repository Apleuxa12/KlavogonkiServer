package models

data class ColoredText(val text: Text?, val colors: ArrayList<Color>, val shouldClear: Boolean) {

    val isFinished: Boolean
        get(){
            if(colors.isEmpty())
                return false

            return colors.all { color -> color == Color.RIGHT || color == Color.SPACE }
        }
}