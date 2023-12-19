package day05Utils

class Pipeline(private val mappers: List<Mapper>) {
    fun process(seeds: List<LongRange>): List<LongRange> {
        var interseeds = seeds
        for (mapper in mappers) {
            interseeds = mapper.map(interseeds)
        }

        return interseeds
    }
}