package utils

fun <T, I> List<T>.chunked(chunkIndicator: (T) -> I): List<List<T>> {
    val underlyingSequence = this
    return buildList {
        val buffer = mutableListOf<T>()
        var lastPredicate: I? = null

        for (current in underlyingSequence) {
            val curPredicate = chunkIndicator(current)
            if(lastPredicate != curPredicate) {
                add(buffer.toList())
                buffer.clear()
            }
            buffer.add(current)
            lastPredicate = curPredicate
        }
        if (buffer.isNotEmpty()) {
            add(buffer)
        }
    }
}